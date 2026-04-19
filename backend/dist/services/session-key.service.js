import { createKernelAccount, createKernelAccountClient, createZeroDevPaymasterClient } from "@zerodev/sdk";
import { signerToEcdsaValidator } from "@zerodev/ecdsa-validator";
import { getEntryPoint, KERNEL_V3_1 } from "@zerodev/sdk/constants";
import { toPermissionValidator } from "@zerodev/permissions";
import { toCallPolicy, CallPolicyVersion } from "@zerodev/permissions/policies";
import { toECDSASigner } from "@zerodev/permissions/signers";
import { createPublicClient, http, parseEther, zeroAddress } from "viem";
import { baseSepolia } from "viem/chains";
import { privateKeyToAccount, generatePrivateKey } from "viem/accounts";
import { ENV } from "../config/env.js";
export class SessionKeyService {
    static ENTRY_POINT = getEntryPoint("0.7");
    static KERNEL_VERSION = KERNEL_V3_1;
    static getPublicClient() {
        return createPublicClient({
            chain: baseSepolia,
            transport: http("https://sepolia.base.org"),
        });
    }
    /**
     * CTO LOGIC: Create a fresh session key and INSTALL it on-chain using the master signer.
     * This matches 'activateCard' in the test repo.
     */
    static async createAndInstallSessionKey(masterSignerPrivateKey, spendLimitEth = "0.1") {
        const publicClient = this.getPublicClient();
        const masterSigner = privateKeyToAccount(masterSignerPrivateKey);
        // 1. Master validator
        const sudoValidator = await signerToEcdsaValidator(publicClient, {
            signer: masterSigner,
            entryPoint: this.ENTRY_POINT,
            kernelVersion: this.KERNEL_VERSION,
        });
        // 2. Generate a fresh session key (The Card Key)
        const sessionKeyPK = generatePrivateKey();
        const sessionKeySigner = privateKeyToAccount(sessionKeyPK);
        // 3. Setup Call Policy
        const callPolicy = toCallPolicy({
            policyVersion: CallPolicyVersion.V0_0_3,
            permissions: [
                { target: zeroAddress, valueLimit: parseEther(spendLimitEth) },
            ],
        });
        // 4. Session validator
        const sessionValidator = await toPermissionValidator(publicClient, {
            entryPoint: this.ENTRY_POINT,
            kernelVersion: this.KERNEL_VERSION,
            signer: await toECDSASigner({ signer: sessionKeySigner }),
            policies: [callPolicy],
        });
        // 5. Account with BOTH validators
        const account = await createKernelAccount(publicClient, {
            plugins: { sudo: sudoValidator, regular: sessionValidator },
            entryPoint: this.ENTRY_POINT,
            kernelVersion: this.KERNEL_VERSION,
        });
        // 6. Setup Master Client with Paymaster
        const paymasterClient = createZeroDevPaymasterClient({
            chain: baseSepolia,
            transport: http(ENV.ZERODEV_RPC_URL),
        });
        const masterKernelClient = createKernelAccountClient({
            account,
            chain: baseSepolia,
            bundlerTransport: http(ENV.ZERODEV_RPC_URL),
            paymaster: {
                getPaymasterData: async (userOperation) => paymasterClient.sponsorUserOperation({ userOperation }),
            },
        });
        // 7. Send the "Plugin Install" transaction (Setup UserOp)
        const setupHash = await masterKernelClient.sendUserOperation({
            userOperation: {
                callData: await account.encodeCalls([
                    { to: zeroAddress, value: BigInt(0), data: "0x" },
                ]),
            }
        });
        // Wait for it so we know it's active
        await masterKernelClient.waitForUserOperationReceipt({ hash: setupHash });
        return { setupHash, sessionKeyPK };
    }
    /**
     * CTO LOGIC: Execute a payment using the stored session key.
     * This matches 'sendCardPayment' in the test repo.
     */
    static async executeWithSessionKey(smartAccountAddress, sessionKeyPK, to, value, spendLimitEth) {
        const publicClient = this.getPublicClient();
        const sessionKeySigner = privateKeyToAccount(sessionKeyPK);
        // 1. Recreate the same policy used during activation
        const callPolicy = toCallPolicy({
            policyVersion: CallPolicyVersion.V0_0_3,
            permissions: [
                { target: zeroAddress, valueLimit: parseEther(spendLimitEth) },
            ],
        });
        // 2. Recreate the session validator
        const sessionValidator = await toPermissionValidator(publicClient, {
            entryPoint: this.ENTRY_POINT,
            kernelVersion: this.KERNEL_VERSION,
            signer: await toECDSASigner({ signer: sessionKeySigner }),
            policies: [callPolicy],
        });
        // 3. Use a dummy master signer (required by SDK for shape, but NOT used for signing)
        const dummyMasterSigner = privateKeyToAccount(generatePrivateKey());
        const dummySudoValidator = await signerToEcdsaValidator(publicClient, {
            signer: dummyMasterSigner,
            entryPoint: this.ENTRY_POINT,
            kernelVersion: this.KERNEL_VERSION,
        });
        // 4. Create the session-bound account
        const account = await createKernelAccount(publicClient, {
            address: smartAccountAddress,
            plugins: { sudo: dummySudoValidator, regular: sessionValidator },
            entryPoint: this.ENTRY_POINT,
            kernelVersion: this.KERNEL_VERSION,
        });
        // 5. Card Client (Signatureless!)
        const paymasterClient = createZeroDevPaymasterClient({
            chain: baseSepolia,
            transport: http(ENV.ZERODEV_RPC_URL),
        });
        const cardClient = createKernelAccountClient({
            account,
            chain: baseSepolia,
            bundlerTransport: http(ENV.ZERODEV_RPC_URL),
            paymaster: {
                getPaymasterData: async (userOperation) => paymasterClient.sponsorUserOperation({ userOperation }),
            },
        });
        // 6. Execute UserOp
        const userOpHash = await cardClient.sendUserOperation({
            userOperation: {
                callData: await account.encodeCalls([
                    { to, value, data: "0x" },
                ]),
            }
        });
        return userOpHash;
    }
}
//# sourceMappingURL=session-key.service.js.map