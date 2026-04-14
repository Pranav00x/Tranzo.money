// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import {ECDSA} from "@openzeppelin/contracts/utils/cryptography/ECDSA.sol";

/**
 * @title SocialRecovery
 * @author Tranzo Team
 * @notice Abstract module providing M-of-N guardian-based social recovery with a
 *         48-hour timelock. Inherited by TranzoAccount.
 *
 * Recovery flow:
 *   1. Any guardian calls initiateRecovery(newOwner, signatures[])
 *      — requires threshold guardian signatures over keccak256(newOwner, nonce, address(this))
 *   2. After RECOVERY_TIMELOCK seconds, executeRecovery() finalises the ownership change.
 *   3. The current owner can call cancelRecovery() to abort during the timelock.
 */
abstract contract SocialRecovery {
    using ECDSA for bytes32;

    // ─── Constants ───────────────────────────────────────────────

    uint256 public constant RECOVERY_TIMELOCK = 48 hours;
    uint256 public constant MIN_GUARDIANS = 1;
    uint256 public constant MAX_GUARDIANS = 5;

    // ─── Storage ─────────────────────────────────────────────────

    mapping(address => bool) public isGuardian;
    address[] public guardians;

    /// @dev M of N threshold required to initiate recovery.
    uint256 public threshold;

    struct RecoveryRequest {
        address newOwner;
        uint256 executeAfter; // block.timestamp + RECOVERY_TIMELOCK
        bool active;
    }

    RecoveryRequest public recoveryRequest;

    /// @dev Nonce increments after each recovery to prevent replay.
    uint256 public recoveryNonce;

    // ─── Events ──────────────────────────────────────────────────

    event GuardianAdded(address indexed guardian);
    event GuardianRemoved(address indexed guardian);
    event RecoveryInitiated(address indexed newOwner, uint256 executeAfter);
    event RecoveryCancelled();
    event RecoveryExecuted(address indexed oldOwner, address indexed newOwner);

    // ─── Errors ──────────────────────────────────────────────────

    error AlreadyGuardian(address guardian);
    error NotGuardian(address caller);
    error TooManyGuardians();
    error TooFewGuardians();
    error InvalidThreshold();
    error RecoveryAlreadyActive();
    error RecoveryNotActive();
    error TimelockNotExpired(uint256 executeAfter, uint256 currentTime);
    error InsufficientGuardianSignatures(uint256 provided, uint256 required);
    error DuplicateOrInvalidSignature();
    error ZeroAddress();

    // ─── Initializer ─────────────────────────────────────────────

    function _initSocialRecovery(address[] memory _guardians, uint256 _threshold) internal {
        if (_guardians.length < MIN_GUARDIANS || _guardians.length > MAX_GUARDIANS) {
            revert TooFewGuardians();
        }
        if (_threshold == 0 || _threshold > _guardians.length) revert InvalidThreshold();

        for (uint256 i = 0; i < _guardians.length; i++) {
            if (_guardians[i] == address(0)) revert ZeroAddress();
            if (isGuardian[_guardians[i]]) revert AlreadyGuardian(_guardians[i]);
            isGuardian[_guardians[i]] = true;
            guardians.push(_guardians[i]);
            emit GuardianAdded(_guardians[i]);
        }
        threshold = _threshold;
    }

    // ─── Guardian management (owner-only, enforced by inheritor) ─

    function _addGuardian(address guardian) internal {
        if (guardian == address(0)) revert ZeroAddress();
        if (isGuardian[guardian]) revert AlreadyGuardian(guardian);
        if (guardians.length >= MAX_GUARDIANS) revert TooManyGuardians();
        isGuardian[guardian] = true;
        guardians.push(guardian);
        emit GuardianAdded(guardian);
    }

    function _removeGuardian(address guardian) internal {
        if (!isGuardian[guardian]) revert NotGuardian(guardian);
        isGuardian[guardian] = false;
        // Swap-and-pop removal
        uint256 len = guardians.length;
        for (uint256 i = 0; i < len; i++) {
            if (guardians[i] == guardian) {
                guardians[i] = guardians[len - 1];
                guardians.pop();
                break;
            }
        }
        // Ensure threshold is still satisfiable
        if (threshold > guardians.length && guardians.length > 0) {
            threshold = guardians.length;
        }
        emit GuardianRemoved(guardian);
    }

    function _setThreshold(uint256 _threshold) internal {
        if (_threshold == 0 || _threshold > guardians.length) revert InvalidThreshold();
        threshold = _threshold;
    }

    // ─── Recovery flow ───────────────────────────────────────────

    /**
     * @notice Initiate a recovery. Requires `threshold` valid guardian signatures.
     * @param newOwner      The address to transfer ownership to.
     * @param signatures    Array of guardian signatures over the recovery hash.
     */
    function initiateRecovery(address newOwner, bytes[] calldata signatures) external {
        if (newOwner == address(0)) revert ZeroAddress();
        if (recoveryRequest.active) revert RecoveryAlreadyActive();
        if (signatures.length < threshold) {
            revert InsufficientGuardianSignatures(signatures.length, threshold);
        }

        bytes32 digest = _recoveryHash(newOwner, recoveryNonce);
        _verifyGuardianSignatures(digest, signatures);

        uint256 executeAfter = block.timestamp + RECOVERY_TIMELOCK;
        recoveryRequest = RecoveryRequest({
            newOwner: newOwner,
            executeAfter: executeAfter,
            active: true
        });

        emit RecoveryInitiated(newOwner, executeAfter);
    }

    /**
     * @notice Cancel an in-progress recovery. Must be called by the current owner.
     *         Enforced via `_requireOwner()` which the inheriting contract provides.
     */
    function cancelRecovery() external {
        _requireOwner();
        if (!recoveryRequest.active) revert RecoveryNotActive();
        recoveryRequest.active = false;
        emit RecoveryCancelled();
    }

    /**
     * @notice Execute recovery after the timelock has elapsed.
     *         Anyone can call this once the timelock expires.
     */
    function executeRecovery() external {
        RecoveryRequest memory req = recoveryRequest;
        if (!req.active) revert RecoveryNotActive();
        if (block.timestamp < req.executeAfter) {
            revert TimelockNotExpired(req.executeAfter, block.timestamp);
        }

        recoveryRequest.active = false;
        recoveryNonce++;

        address previousOwner = _getOwner();
        _transferOwnershipInternal(req.newOwner);

        emit RecoveryExecuted(previousOwner, req.newOwner);
    }

    // ─── Internal helpers ─────────────────────────────────────────

    function _recoveryHash(address newOwner, uint256 nonce) internal view returns (bytes32) {
        return keccak256(abi.encodePacked(
            "\x19\x01",
            keccak256(abi.encode(address(this))),
            keccak256(abi.encode(newOwner, nonce, address(this)))
        ));
    }

    function _verifyGuardianSignatures(bytes32 digest, bytes[] calldata signatures) internal view {
        uint256 validCount = 0;
        address lastSigner = address(0);

        for (uint256 i = 0; i < signatures.length; i++) {
            address signer = digest.recover(signatures[i]);
            // Must be a guardian, and signers must be strictly ordered to prevent duplicates
            if (!isGuardian[signer] || signer <= lastSigner) {
                revert DuplicateOrInvalidSignature();
            }
            lastSigner = signer;
            validCount++;
        }

        if (validCount < threshold) {
            revert InsufficientGuardianSignatures(validCount, threshold);
        }
    }

    // ─── Hooks to be implemented by the inheriting contract ──────

    /// @dev Reverts if msg.sender is not the account owner.
    function _requireOwner() internal view virtual;

    /// @dev Returns the current owner address.
    function _getOwner() internal view virtual returns (address);

    /// @dev Transfers ownership to `newOwner` (internal, no access check).
    function _transferOwnershipInternal(address newOwner) internal virtual;
}
