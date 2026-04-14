// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

/**
 * @title SpendingLimits
 * @author Tranzo Team
 * @notice Abstract module providing daily and per-transaction spending limits.
 *         Inherited by TranzoAccount. Limits are denominated in wei (or token units
 *         when the account executes ERC-20 transfers — enforcement at that level).
 */
abstract contract SpendingLimits {
    // ─── Storage ─────────────────────────────────────────────────

    /// @dev Amount that may be spent in a single transaction (0 = no limit).
    uint256 public perTxLimit;

    /// @dev Maximum amount that may be spent within a single UTC day (0 = no limit).
    uint256 public dailyLimit;

    /// @dev Accumulated spending in the current UTC day.
    uint256 public dailySpent;

    /// @dev The UTC midnight timestamp that starts the current window.
    uint256 public currentDayStart;

    // ─── Events ──────────────────────────────────────────────────

    event LimitSet(uint256 dailyLimit, uint256 perTxLimit);
    event LimitExceeded(uint256 amount, uint256 limit, string limitType);

    // ─── Errors ──────────────────────────────────────────────────

    error PerTxLimitExceeded(uint256 amount, uint256 limit);
    error DailyLimitExceeded(uint256 amount, uint256 remaining);

    // ─── Internal helpers ─────────────────────────────────────────

    /**
     * @notice Must be called by the inheriting contract's initializer.
     */
    function _initSpendingLimits(uint256 _dailyLimit, uint256 _perTxLimit) internal {
        dailyLimit = _dailyLimit;
        perTxLimit = _perTxLimit;
        currentDayStart = _midnightOf(block.timestamp);
    }

    /**
     * @notice Set new spending limits. Must be access-controlled by the inheriting contract.
     */
    function _setSpendingLimits(uint256 _dailyLimit, uint256 _perTxLimit) internal {
        dailyLimit = _dailyLimit;
        perTxLimit = _perTxLimit;
        emit LimitSet(_dailyLimit, _perTxLimit);
    }

    /**
     * @notice Check that `amount` does not violate per-tx or daily limits.
     *         Reverts if exceeded. Updates the daily counter if within limits.
     * @param amount Value (in wei or token units) being spent.
     */
    function _checkAndRecordSpend(uint256 amount) internal {
        // Per-transaction check
        if (perTxLimit != 0 && amount > perTxLimit) {
            emit LimitExceeded(amount, perTxLimit, "perTx");
            revert PerTxLimitExceeded(amount, perTxLimit);
        }

        // Roll over daily counter if we are in a new UTC day
        uint256 todayMidnight = _midnightOf(block.timestamp);
        if (todayMidnight > currentDayStart) {
            currentDayStart = todayMidnight;
            dailySpent = 0;
        }

        // Daily limit check
        if (dailyLimit != 0) {
            uint256 newSpent = dailySpent + amount;
            if (newSpent > dailyLimit) {
                uint256 remaining = dailyLimit > dailySpent ? dailyLimit - dailySpent : 0;
                emit LimitExceeded(amount, dailyLimit, "daily");
                revert DailyLimitExceeded(amount, remaining);
            }
            dailySpent = newSpent;
        }
    }

    /**
     * @dev Returns the Unix timestamp of the most recent UTC midnight on or before `ts`.
     */
    function _midnightOf(uint256 ts) private pure returns (uint256) {
        return ts - (ts % 1 days);
    }
}
