// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import "forge-std/Test.sol";
import {TranzoDripper} from "../src/TranzoDripper.sol";
import {IERC20} from "@openzeppelin/contracts/token/ERC20/IERC20.sol";
import {ERC20} from "@openzeppelin/contracts/token/ERC20/ERC20.sol";

/// @dev Mock ERC-20 for testing
contract MockUSDC is ERC20 {
    constructor() ERC20("USD Coin", "USDC") {}

    function mint(address to, uint256 amount) external {
        _mint(to, amount);
    }

    function decimals() public pure override returns (uint8) {
        return 6;
    }
}

contract TranzoDripperTest is Test {
    TranzoDripper public dripper;
    MockUSDC public usdc;

    address public employer = address(0xA);
    address public employee = address(0xB);

    uint256 constant TOTAL_AMOUNT = 12_000e6; // 12,000 USDC
    uint256 constant DURATION = 60 days;

    function setUp() public {
        dripper = new TranzoDripper();
        usdc = new MockUSDC();

        // Fund employer
        usdc.mint(employer, 100_000e6);

        // Approve dripper
        vm.prank(employer);
        usdc.approve(address(dripper), type(uint256).max);
    }

    function test_createStream() public {
        uint256 startTime = block.timestamp;
        uint256 endTime = startTime + DURATION;

        vm.prank(employer);
        uint256 streamId = dripper.createStream(
            employee,
            address(usdc),
            TOTAL_AMOUNT,
            startTime,
            endTime
        );

        assertEq(streamId, 1);
        assertEq(usdc.balanceOf(address(dripper)), TOTAL_AMOUNT);
    }

    function test_balanceOf_accrues() public {
        uint256 startTime = block.timestamp;
        uint256 endTime = startTime + DURATION;

        vm.prank(employer);
        uint256 streamId = dripper.createStream(
            employee, address(usdc), TOTAL_AMOUNT, startTime, endTime
        );

        // At start, nothing accrued
        assertEq(dripper.balanceOf(streamId), 0);

        // Warp 30 days (half duration)
        vm.warp(startTime + 30 days);
        uint256 halfBalance = dripper.balanceOf(streamId);
        assertApproxEqAbs(halfBalance, TOTAL_AMOUNT / 2, 1e6); // ~6000 USDC ±1

        // Warp to end
        vm.warp(endTime);
        assertEq(dripper.balanceOf(streamId), TOTAL_AMOUNT);
    }

    function test_withdraw() public {
        uint256 startTime = block.timestamp;
        uint256 endTime = startTime + DURATION;

        vm.prank(employer);
        uint256 streamId = dripper.createStream(
            employee, address(usdc), TOTAL_AMOUNT, startTime, endTime
        );

        // Warp 15 days
        vm.warp(startTime + 15 days);
        uint256 expectedBalance = dripper.balanceOf(streamId);

        vm.prank(employee);
        dripper.withdraw(streamId);

        assertEq(usdc.balanceOf(employee), expectedBalance);
        assertEq(dripper.balanceOf(streamId), 0); // All withdrawn
    }

    function test_cancel_refunds_employer() public {
        uint256 startTime = block.timestamp;
        uint256 endTime = startTime + DURATION;

        vm.prank(employer);
        uint256 streamId = dripper.createStream(
            employee, address(usdc), TOTAL_AMOUNT, startTime, endTime
        );

        uint256 employerBefore = usdc.balanceOf(employer);

        // Warp 20 days
        vm.warp(startTime + 20 days);
        uint256 accruedForEmployee = dripper.balanceOf(streamId);

        vm.prank(employer);
        dripper.cancel(streamId);

        // Employee gets accrued
        assertEq(usdc.balanceOf(employee), accruedForEmployee);

        // Employer gets refund
        uint256 expectedRefund = TOTAL_AMOUNT - accruedForEmployee;
        assertEq(usdc.balanceOf(employer) - employerBefore, expectedRefund);
    }

    function test_revert_cancel_unauthorized() public {
        uint256 startTime = block.timestamp;
        uint256 endTime = startTime + DURATION;

        vm.prank(employer);
        uint256 streamId = dripper.createStream(
            employee, address(usdc), TOTAL_AMOUNT, startTime, endTime
        );

        vm.prank(employee);
        vm.expectRevert(TranzoDripper.Unauthorized.selector);
        dripper.cancel(streamId);
    }

    function test_revert_invalid_time_range() public {
        vm.prank(employer);
        vm.expectRevert(TranzoDripper.InvalidTimeRange.selector);
        dripper.createStream(
            employee,
            address(usdc),
            TOTAL_AMOUNT,
            block.timestamp + 100,
            block.timestamp // end before start
        );
    }

    function test_revert_zero_amount() public {
        vm.prank(employer);
        vm.expectRevert(TranzoDripper.ZeroAmount.selector);
        dripper.createStream(
            employee,
            address(usdc),
            0,
            block.timestamp,
            block.timestamp + DURATION
        );
    }

    function test_revert_withdraw_nothing() public {
        uint256 startTime = block.timestamp + 1 hours;
        uint256 endTime = startTime + DURATION;

        vm.prank(employer);
        uint256 streamId = dripper.createStream(
            employee, address(usdc), TOTAL_AMOUNT, startTime, endTime
        );

        // Stream hasn't started yet
        vm.expectRevert(TranzoDripper.NothingToWithdraw.selector);
        dripper.withdraw(streamId);
    }

    function test_multiple_withdrawals() public {
        uint256 startTime = block.timestamp;
        uint256 endTime = startTime + DURATION;

        vm.prank(employer);
        uint256 streamId = dripper.createStream(
            employee, address(usdc), TOTAL_AMOUNT, startTime, endTime
        );

        // First withdrawal at day 10
        vm.warp(startTime + 10 days);
        uint256 firstBalance = dripper.balanceOf(streamId);
        vm.prank(employee);
        dripper.withdraw(streamId);
        assertEq(usdc.balanceOf(employee), firstBalance);

        // Second withdrawal at day 30
        vm.warp(startTime + 30 days);
        uint256 secondBalance = dripper.balanceOf(streamId);
        assertTrue(secondBalance > 0);
        vm.prank(employee);
        dripper.withdraw(streamId);

        assertEq(usdc.balanceOf(employee), firstBalance + secondBalance);
    }
}
