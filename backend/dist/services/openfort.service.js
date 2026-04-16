import { openfort } from "../config/openfort.js";
import { ENV } from "../config/env.js";
/**
 * Wrapper around the Openfort SDK.
 * Handles player creation, smart account management, and transaction intents.
 */
export class OpenfortService {
    // ─── Player / Account Management ──────────────────────────────
    /**
     * Create a new Openfort player and their smart account.
     * Returns the player ID and counterfactual smart account address.
     */
    static async createPlayer(email) {
        const player = await openfort.players.create({
            name: email,
        });
        // Create an account for this player on the default chain
        const account = await openfort.accounts.create({
            player: player.id,
            chainId: ENV.DEFAULT_CHAIN_ID,
        });
        return {
            playerId: player.id,
            smartAccountAddress: account.address,
        };
    }
    /**
     * Get a player's smart account address for a specific chain.
     */
    static async getAccount(playerId, chainId = ENV.DEFAULT_CHAIN_ID) {
        const accounts = await openfort.accounts.list({
            player: playerId,
        });
        const account = accounts.data.find((a) => a.chainId === chainId);
        if (!account) {
            // Create account on this chain if it doesn't exist
            return await openfort.accounts.create({
                player: playerId,
                chainId,
            });
        }
        return account;
    }
    // ─── Transaction Intents ──────────────────────────────────────
    /**
     * Submit a token transfer via Openfort.
     * Openfort handles UserOp construction, bundler submission, and gas sponsorship.
     */
    static async sendToken(params) {
        const intent = await openfort.transactionIntents.create({
            player: params.playerId,
            chainId: params.chainId ?? ENV.DEFAULT_CHAIN_ID,
            policy: ENV.OPENFORT_POLICY_ID,
            interactions: [
                {
                    contract: params.tokenAddress,
                    functionName: "transfer",
                    functionArgs: [params.to, params.amount],
                },
            ],
        });
        return intent;
    }
    /**
     * Execute an arbitrary contract interaction.
     */
    static async executeInteraction(params) {
        const intent = await openfort.transactionIntents.create({
            player: params.playerId,
            chainId: params.chainId ?? ENV.DEFAULT_CHAIN_ID,
            policy: ENV.OPENFORT_POLICY_ID,
            interactions: [
                {
                    contract: params.contract,
                    functionName: params.functionName,
                    functionArgs: params.functionArgs,
                    value: params.value,
                },
            ],
        });
        return intent;
    }
    /**
     * Check the status of a transaction intent.
     */
    static async getTransactionStatus(intentId) {
        return await openfort.transactionIntents.get({ id: intentId });
    }
    /**
     * List recent transaction intents for a player.
     */
    static async getTransactionHistory(playerId, limit = 20) {
        return await openfort.transactionIntents.list({
            player: playerId,
            limit,
        });
    }
    // ─── Signature Requests ───────────────────────────────────────
    /**
     * Create a signature request for the user to sign on-device.
     * Used for operations that require the user's key (e.g., high-value transfers).
     */
    static async createSignatureRequest(params) {
        // Openfort handles the signing flow — the mobile app
        // uses the Openfort SDK to sign and return the signature
        return await openfort.transactionIntents.createSignatureRequest({
            player: params.playerId,
            chainId: params.chainId ?? ENV.DEFAULT_CHAIN_ID,
            request: {
                message: params.message,
            },
        });
    }
}
//# sourceMappingURL=openfort.service.js.map