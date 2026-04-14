// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

/**
 * @title SessionKeyManager
 * @author Tranzo Team
 * @notice Abstract module providing scoped session keys. Inherited by TranzoAccount.
 *
 * A session key is a temporary signing key that may only:
 *   - Call a specific set of contracts
 *   - Call a specific set of function selectors on those contracts
 *   - Spend up to a cumulative `spendLimit` in native value
 *   - Be used before `validUntil`
 */
abstract contract SessionKeyManager {
    // ─── Types ───────────────────────────────────────────────────

    struct SessionKeyData {
        address[] allowedContracts;
        bytes4[] allowedFunctions;
        uint256 spendLimit;
        uint256 spent;
        uint256 validUntil;
        bool active;
    }

    // ─── Storage ─────────────────────────────────────────────────

    mapping(address => SessionKeyData) private _sessionKeys;

    // ─── Events ──────────────────────────────────────────────────

    event SessionKeyAdded(
        address indexed key,
        uint256 validUntil,
        uint256 spendLimit
    );
    event SessionKeyRevoked(address indexed key);

    // ─── Errors ──────────────────────────────────────────────────

    error SessionKeyAlreadyExists(address key);
    error SessionKeyNotFound(address key);
    error SessionKeyExpiredError(address key);
    error SessionKeyRevokedError(address key);
    error ContractNotAllowed(address key, address target);
    error FunctionNotAllowed(address key, bytes4 selector);
    error SessionSpendLimitExceeded(address key, uint256 amount, uint256 remaining);
    error SessionKeyZeroAddress();

    // ─── Management (access-controlled by inheritor) ─────────────

    /**
     * @notice Register a new session key.
     * @param key               The temporary signing address.
     * @param allowedContracts  Contracts the key may call (empty = any).
     * @param allowedFunctions  Function selectors the key may call (empty = any).
     * @param spendLimit        Maximum cumulative native value the key may spend (0 = no limit).
     * @param validUntil        Expiry timestamp (block.timestamp-based).
     */
    function addSessionKey(
        address key,
        address[] calldata allowedContracts,
        bytes4[] calldata allowedFunctions,
        uint256 spendLimit,
        uint256 validUntil
    ) external {
        _requireOwner();
        if (key == address(0)) revert SessionKeyZeroAddress();
        if (_sessionKeys[key].active) revert SessionKeyAlreadyExists(key);

        _sessionKeys[key] = SessionKeyData({
            allowedContracts: allowedContracts,
            allowedFunctions: allowedFunctions,
            spendLimit: spendLimit,
            spent: 0,
            validUntil: validUntil,
            active: true
        });

        emit SessionKeyAdded(key, validUntil, spendLimit);
    }

    /**
     * @notice Revoke a session key immediately.
     */
    function revokeSessionKey(address key) external {
        _requireOwner();
        if (!_sessionKeys[key].active) revert SessionKeyNotFound(key);
        _sessionKeys[key].active = false;
        emit SessionKeyRevoked(key);
    }

    /**
     * @notice Returns whether `key` is an active (non-expired) session key.
     */
    function isActiveSessionKey(address key) public view returns (bool) {
        SessionKeyData storage sk = _sessionKeys[key];
        return sk.active && block.timestamp <= sk.validUntil;
    }

    /**
     * @notice Returns the session key metadata for off-chain inspection.
     */
    function getSessionKey(address key) external view returns (SessionKeyData memory) {
        return _sessionKeys[key];
    }

    // ─── Validation (called during validateUserOp) ───────────────

    /**
     * @notice Validate a call against session-key restrictions and update spend tracking.
     *         Reverts if any constraint is violated.
     * @param key      The session key signer extracted from the UserOp signature.
     * @param target   Destination contract of the call.
     * @param value    Native-token value of the call.
     * @param data     Calldata of the call.
     */
    function _validateSessionKey(
        address key,
        address target,
        uint256 value,
        bytes memory data
    ) internal {
        SessionKeyData storage sk = _sessionKeys[key];

        if (!sk.active) revert SessionKeyRevokedError(key);
        if (block.timestamp > sk.validUntil) revert SessionKeyExpiredError(key);

        // Contract allowlist (empty = allow all)
        if (sk.allowedContracts.length > 0) {
            bool contractAllowed = false;
            for (uint256 i = 0; i < sk.allowedContracts.length; i++) {
                if (sk.allowedContracts[i] == target) {
                    contractAllowed = true;
                    break;
                }
            }
            if (!contractAllowed) revert ContractNotAllowed(key, target);
        }

        // Function selector allowlist (empty = allow all, only checked if data ≥ 4 bytes)
        if (sk.allowedFunctions.length > 0 && data.length >= 4) {
            bytes4 sel = bytes4(abi.encodePacked(data[0], data[1], data[2], data[3]));
            bool funcAllowed = false;
            for (uint256 i = 0; i < sk.allowedFunctions.length; i++) {
                if (sk.allowedFunctions[i] == sel) {
                    funcAllowed = true;
                    break;
                }
            }
            if (!funcAllowed) revert FunctionNotAllowed(key, sel);
        }

        // Spend limit (0 = no limit)
        if (sk.spendLimit != 0 && value > 0) {
            uint256 newSpent = sk.spent + value;
            if (newSpent > sk.spendLimit) {
                uint256 remaining = sk.spendLimit > sk.spent ? sk.spendLimit - sk.spent : 0;
                revert SessionSpendLimitExceeded(key, value, remaining);
            }
            sk.spent = newSpent;
        }
    }

    // ─── Hook ────────────────────────────────────────────────────

    /// @dev Reverts if msg.sender is not the account owner.
    function _requireOwner() internal view virtual;
}
