import { ENV } from "../config/env.js";
import { createPublicClient, http, formatUnits } from "viem";
import { polygon, base, baseSepolia } from "viem/chains";

// Supported tokens per chain
const TOKENS: Record<
  number,
  Array<{ symbol: string; address: string; decimals: number }>
> = {
  137: [
    { symbol: "USDC", address: "0x3c499c542cEF5E3811e1192ce70d8cC03d5c3359", decimals: 6 },
    { symbol: "USDT", address: "0xc2132D05D31c914a87C6611C10748AEb04B58e8F", decimals: 6 },
    { symbol: "WETH", address: "0x7ceB23fD6bC0adD59E62ac25578270cFf1b9f619", decimals: 18 },
    { symbol: "WBTC", address: "0x1BFD67037B42Cf73acF2047067bd4F2C47D9BfD6", decimals: 8 },
    { symbol: "POL",  address: "native", decimals: 18 },
  ],
  8453: [
    { symbol: "USDC", address: "0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913", decimals: 6 },
    { symbol: "WETH", address: "0x4200000000000000000000000000000000000006", decimals: 18 },
    { symbol: "ETH",  address: "native", decimals: 18 },
  ],
  84532: [
    { symbol: "USDC", address: "0x036CbD53842c5426634e7929541eC2318f3dCF7e", decimals: 6 },
    { symbol: "ETH",  address: "native", decimals: 18 },
  ],
};

// ERC-20 balanceOf ABI
const erc20Abi = [
  {
    inputs: [{ name: "account", type: "address" }],
    name: "balanceOf",
    outputs: [{ name: "", type: "uint256" }],
    stateMutability: "view",
    type: "function",
  },
] as const;

function getClient(chainId: number) {
  const chain = chainId === 137 ? polygon : (chainId === 8453 ? base : baseSepolia);
  
  let rpcUrl = ENV.ZERODEV_RPC_URL;
  if (!rpcUrl) {
    rpcUrl = chainId === 137 ? ENV.POLYGON_RPC_URL : (chainId === 8453 ? ENV.BASE_RPC_URL : undefined);
  }

  return createPublicClient({
    chain,
    transport: http(rpcUrl),
  });
}

export class BalanceService {
  /**
   * Get all token balances for a smart account address.
   */
  static async getBalances(
    address: string,
    chainId: number = ENV.DEFAULT_CHAIN_ID
  ) {
    const client = getClient(chainId);
    const tokens = TOKENS[chainId] ?? [];
    const addr = address as `0x${string}`;

    const results = await Promise.all(
      tokens.map(async (token) => {
        try {
          let balance: bigint;

          if (token.address === "native") {
            balance = await client.getBalance({ address: addr });
          } else {
            balance = (await (client as any).readContract({
              address: token.address as `0x${string}`,
              abi: erc20Abi,
              functionName: "balanceOf",
              args: [addr],
            })) as bigint;
          }

          return {
            symbol: token.symbol,
            address: token.address,
            decimals: token.decimals,
            balance: balance.toString(),
            formatted: formatUnits(balance, token.decimals),
          };
        } catch {
          return {
            symbol: token.symbol,
            address: token.address,
            decimals: token.decimals,
            balance: "0",
            formatted: "0",
          };
        }
      })
    );

    return results;
  }

  /**
   * Get supported tokens for a chain.
   */
  static getTokens(chainId: number = ENV.DEFAULT_CHAIN_ID) {
    return TOKENS[chainId] ?? [];
  }
}
