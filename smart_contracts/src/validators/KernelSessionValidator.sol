// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import {PackedUserOperation} from "account-abstraction/interfaces/PackedUserOperation.sol";
import {ECDSA} from "@openzeppelin/contracts/utils/cryptography/ECDSA.sol";
import {MessageHashUtils} from "@openzeppelin/contracts/utils/cryptography/MessageHashUtils.sol";

contract KernelSessionValidator {
    using ECDSA for bytes32;
    using MessageHashUtils for bytes32;

    mapping(address account => mapping(address sessionKey => SessionKeyData)) public sessionKeys;

    struct SessionKeyData {
        bytes32 permissionsHash;
        uint256 expiryTime;
        bool revoked;
    }

    event SessionKeyCreated(address indexed account, address indexed sessionKey, uint256 expiryTime);
    event SessionKeyRevokedEvent(address indexed account, address indexed sessionKey);

    error SessionKeyExpired();
    error SessionKeyRevokedError();
    error UnauthorizedCall();

    function registerSessionKey(
        address sessionKey,
        bytes32 permissionsHash,
        uint256 expiryTime
    ) external {
        require(expiryTime > block.timestamp, "Expiry must be in the future");
        sessionKeys[msg.sender][sessionKey] = SessionKeyData({
            permissionsHash: permissionsHash,
            expiryTime: expiryTime,
            revoked: false
        });
        emit SessionKeyCreated(msg.sender, sessionKey, expiryTime);
    }

    function revokeSessionKey(address sessionKey) external {
        sessionKeys[msg.sender][sessionKey].revoked = true;
        emit SessionKeyRevokedEvent(msg.sender, sessionKey);
    }

    function validateUserOp(
        PackedUserOperation calldata userOp,
        bytes32 userOpHash,
        bytes calldata signature
    ) external returns (uint256) {
        (address sessionKey,) = abi.decode(signature, (address, bytes));
        SessionKeyData storage keyData = sessionKeys[userOp.sender][sessionKey];

        if (keyData.expiryTime == 0) revert UnauthorizedCall();
        if (block.timestamp > keyData.expiryTime) revert SessionKeyExpired();
        if (keyData.revoked) revert SessionKeyRevokedError();

        bytes32 digest = userOpHash.toEthSignedMessageHash();
        address recovered = digest.recover(signature);
        require(recovered == sessionKey, "Invalid signature");

        return 0;
    }
}
