// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import {IERC20} from "@openzeppelin/contracts/token/ERC20/IERC20.sol";
import {SafeERC20} from "@openzeppelin/contracts/token/ERC20/utils/SafeERC20.sol";

/**
 * @title TranzoDripper
 * @author Tranzo Team
 * @notice Salary streaming contract. Employers deposit tokens and employees
 *         withdraw accrued amounts in real-time. The only custom contract
 *         in Tranzo's stack — everything else runs through Openfort.
 */
contract TranzoDripper {
    using SafeERC20 for IERC20;

    struct Stream {
        address sender;
        address recipient;
        address token;
        uint256 totalAmount;
        uint256 withdrawn;
        uint256 startTime;
        uint256 endTime;
        bool cancelled;
    }

    uint256 public nextStreamId = 1;
    mapping(uint256 => Stream) public streams;

    // ─── Events ──────────────────────────────────────────────────

    event StreamCreated(
        uint256 indexed streamId,
        address indexed sender,
        address indexed recipient,
        address token,
        uint256 totalAmount,
        uint256 startTime,
        uint256 endTime
    );

    event Withdrawn(
        uint256 indexed streamId,
        address indexed recipient,
        uint256 amount
    );

    event StreamCancelled(
        uint256 indexed streamId,
        address indexed sender,
        uint256 recipientAmount,
        uint256 senderRefund
    );

    // ─── Errors ──────────────────────────────────────────────────

    error InvalidTimeRange();
    error ZeroAmount();
    error ZeroAddress();
    error Unauthorized();
    error StreamEndedOrCancelled();
    error NothingToWithdraw();

    // ─── Stream Management ───────────────────────────────────────

    /**
     * @notice Create a linear vesting salary stream.
     * @param recipient  Employee's smart account address.
     * @param token      ERC-20 token to stream (USDC/USDT).
     * @param totalAmount Total tokens to stream over the duration.
     * @param startTime  Unix timestamp when streaming begins.
     * @param endTime    Unix timestamp when streaming ends.
     */
    function createStream(
        address recipient,
        address token,
        uint256 totalAmount,
        uint256 startTime,
        uint256 endTime
    ) external returns (uint256) {
        if (recipient == address(0)) revert ZeroAddress();
        if (totalAmount == 0) revert ZeroAmount();
        if (startTime >= endTime) revert InvalidTimeRange();

        // Pull tokens from sender (employer must approve first)
        IERC20(token).safeTransferFrom(msg.sender, address(this), totalAmount);

        uint256 streamId = nextStreamId++;
        streams[streamId] = Stream({
            sender: msg.sender,
            recipient: recipient,
            token: token,
            totalAmount: totalAmount,
            withdrawn: 0,
            startTime: startTime,
            endTime: endTime,
            cancelled: false
        });

        emit StreamCreated(
            streamId, msg.sender, recipient, token,
            totalAmount, startTime, endTime
        );
        return streamId;
    }

    /**
     * @notice Calculate the currently withdrawable amount for a stream.
     */
    function balanceOf(uint256 streamId) public view returns (uint256) {
        Stream memory s = streams[streamId];
        if (s.cancelled || block.timestamp <= s.startTime) return 0;

        uint256 accrued;
        if (block.timestamp >= s.endTime) {
            accrued = s.totalAmount;
        } else {
            accrued = (s.totalAmount * (block.timestamp - s.startTime))
                / (s.endTime - s.startTime);
        }

        return accrued > s.withdrawn ? accrued - s.withdrawn : 0;
    }

    /**
     * @notice Withdraw accrued tokens from a stream.
     *         Anyone can call this (gasless via smart account).
     */
    function withdraw(uint256 streamId) external {
        Stream storage s = streams[streamId];
        if (s.cancelled) revert StreamEndedOrCancelled();

        uint256 amount = balanceOf(streamId);
        if (amount == 0) revert NothingToWithdraw();

        s.withdrawn += amount;
        IERC20(s.token).safeTransfer(s.recipient, amount);

        emit Withdrawn(streamId, s.recipient, amount);
    }

    /**
     * @notice Cancel a stream. Accrued amount goes to recipient,
     *         remaining goes back to sender.
     */
    function cancel(uint256 streamId) external {
        Stream storage s = streams[streamId];
        if (msg.sender != s.sender) revert Unauthorized();
        if (s.cancelled) revert StreamEndedOrCancelled();

        uint256 recipientAmount = balanceOf(streamId);
        s.cancelled = true;

        if (recipientAmount > 0) {
            s.withdrawn += recipientAmount;
            IERC20(s.token).safeTransfer(s.recipient, recipientAmount);
        }

        uint256 senderRefund = s.totalAmount - s.withdrawn;
        if (senderRefund > 0) {
            IERC20(s.token).safeTransfer(s.sender, senderRefund);
        }

        emit StreamCancelled(streamId, msg.sender, recipientAmount, senderRefund);
    }
}
