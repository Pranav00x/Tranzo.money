# Tranzo Smart Contracts Deployment Guide

## Overview

This guide covers deploying Tranzo smart contracts to **Base Sepolia** testnet with **ZeroDev Kernel** integration.

## Contracts

1. **TranzoAccountFactory** - Creates and manages TranzoAccount instances (ERC-4337 smart accounts)
2. **TranzoAccount** - User's smart wallet (implements IAccount, supports session keys, spending limits, social recovery)
3. **TranzoPaymaster** - Handles gas sponsorship and USDC-based gas payments
4. **TranzoDripper** - Salary streaming contract (linear vesting)
5. **KernelSessionValidator** - On-chain validator for session-key-signed UserOps

## Prerequisites

```bash
# Install Foundry
curl -L https://foundry.paradigm.xyz | bash
foundryup

# Clone and setup
git clone https://github.com/Pranav00x/Tranzo.money.git
cd Tranzo.money/smart_contracts
forge install
```

## Environment Setup

Create a `.env` file in `smart_contracts/`:

```bash
# Deployer account (must have ETH for gas)
DEPLOYER_PRIVATE_KEY=0x...

# Base Sepolia RPC
BASE_SEPOLIA_RPC_URL=https://sepolia.base.org

# ERC-4337 EntryPoint v0.7 address on Base Sepolia
ENTRY_POINT_ADDRESS=0x0000000071727De22E8e0f8050385f8db87Df640

# Backend signer (signs paymaster authorizations)
BACKEND_SIGNER_ADDRESS=0x...

# USDC on Base Sepolia
USDC_ADDRESS=0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913

# Optional: Initial ETH deposit for paymaster (in wei)
PAYMASTER_DEPOSIT_ETH=1000000000000000000

# Etherscan API key for verification
BASESCAN_API_KEY=...
```

## Deployment Steps

### 1. Build Contracts

```bash
cd smart_contracts
forge build
```

### 2. Test Contracts

```bash
# Run unit tests
forge test

# Run integration tests
forge test --match-test "Integration"

# With detailed output
forge test -vv
```

### 3. Deploy to Base Sepolia

```bash
source .env

forge script script/Deploy.s.sol:Deploy \
  --rpc-url base_sepolia \
  --broadcast \
  --verify \
  --etherscan-api-key $BASESCAN_API_KEY
```

### 4. Verify Contracts on Etherscan

```bash
forge verify-contract \
  <CONTRACT_ADDRESS> \
  src/TranzoAccount.sol:TranzoAccount \
  --chain base_sepolia \
  --etherscan-api-key $BASESCAN_API_KEY
```

## Network Configuration

### Base Sepolia

| Parameter | Value |
|-----------|-------|
| Chain ID | 84532 |
| RPC URL | https://sepolia.base.org |
| EntryPoint v0.7 | 0x0000000071727De22E8e0f8050385f8db87Df640 |
| USDC | 0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913 |

### Polygon Amoy (Mumbai)

| Parameter | Value |
|-----------|-------|
| Chain ID | 80002 |
| RPC URL | https://rpc-amoy.polygon.technology |
| EntryPoint v0.7 | 0x0000000071727De22E8e0f8050385f8db87Df640 |
| USDC | 0x41E94Cbed3193957FA8ADDC8f9bA1fa0b47Afcc6 |

## Deployment Checklist

- [ ] `.env` file created with all required variables
- [ ] Deployer account has sufficient ETH (>0.5 ETH for gas)
- [ ] Contracts build successfully: `forge build`
- [ ] Tests pass: `forge test`
- [ ] Verify RPC connection: `cast chain-id --rpc-url base_sepolia`
- [ ] Run deployment: `forge script script/Deploy.s.sol:Deploy --broadcast`
- [ ] Verify contracts on Etherscan

## Post-Deployment

### 1. Update Backend Configuration

In `backend/.env`:

```bash
# Deployed contract addresses
DRIPPER_CONTRACT_ADDRESS=0x...
ACCOUNT_FACTORY_ADDRESS=0x...
PAYMASTER_ADDRESS=0x...
BACKEND_SIGNER_ADDRESS=0x...
```

### 2. Configure ZeroDev Kernel

Update `backend/.env`:

```bash
ZERODEV_PROJECT_ID=<your-project-id>
ZERODEV_RPC_URL=https://rpc.zerodev.app/api/v3/<project-id>/chain/84532
BUNDLER_RPC_URL=https://rpc.zerodev.app/api/v3/<project-id>/bundler
```

### 3. Test Android Integration

In `android/app/src/main/java/com/tranzo/app/data/api/TranzoApi.kt`:

```kotlin
private val BASE_URL = "https://tranzo-backend.example.com"
```

Start backend and test flow:

```bash
cd backend
npm install
npm run dev
```

Test in Android app:
1. Create account (OTP flow)
2. Send token transfer
3. Verify UserOp hash in response
4. Check transaction status

## Troubleshooting

### "Insufficient gas"
- Ensure deployer has >0.5 ETH
- Reduce contract size or optimize code

### "EntryPoint not found"
- Verify ENTRY_POINT_ADDRESS is correct for the network
- Check RPC connection

### "Signature verification failed"
- Ensure BACKEND_SIGNER_ADDRESS matches backend signer
- Check signature encoding in paymaster

### Contracts won't verify
- Use `--compiler-version` flag if version mismatch
- Ensure source code matches compiled bytecode

## Gas Estimates

Approximate gas usage (Base Sepolia):

| Operation | Gas |
|-----------|-----|
| Create account | ~150,000 |
| Simple transfer | ~50,000 |
| Create stream | ~100,000 |
| Withdraw from stream | ~80,000 |
| Cancel stream | ~60,000 |

## Security Notes

- Backend signer private key must be kept secure
- Paymaster owner can drain deposits
- Session key expiry should be set appropriately
- Test thoroughly on testnet before mainnet

## Support

For issues or questions:
1. Check contract code comments
2. Review integration tests
3. Check deployment logs for error messages
4. Verify environment variables are set correctly
