import { SmartAccountService } from "./services/smart-account.service.js";
import { ENV } from "./config/env.js";
async function testZeroDev() {
    console.log("\n========== TESTING ZERODEV ==========\n");
    // Check env vars
    console.log("1. Checking environment variables...");
    console.log(`   ZERODEV_PROJECT_ID: ${ENV.ZERODEV_PROJECT_ID ? "✅ Set" : "❌ Missing"}`);
    console.log(`   ZERODEV_RPC_URL: ${ENV.ZERODEV_RPC_URL ? "✅ Set" : "❌ Missing"}`);
    if (!ENV.ZERODEV_PROJECT_ID || !ENV.ZERODEV_RPC_URL) {
        console.error("\n❌ MISSING ENVIRONMENT VARIABLES!");
        console.error("Add to Railway:");
        console.error("  ZERODEV_PROJECT_ID=your_id");
        console.error("  ZERODEV_RPC_URL=https://rpc.zerodev.app/...");
        process.exit(1);
    }
    try {
        console.log("\n2. Creating smart account...");
        const { address, privateKey } = await SmartAccountService.createAccount();
        console.log("\n✅ SUCCESS!\n");
        console.log("Smart Account Details:");
        console.log(`  Address: ${address}`);
        console.log(`  Private Key: ${privateKey.substring(0, 20)}...`);
        console.log("\nThis is a test - you can delete this account.\n");
        process.exit(0);
    }
    catch (error) {
        console.error("\n❌ ERROR!");
        console.error(`\nMessage: ${error.message}`);
        console.error(`\nStack: ${error.stack}`);
        process.exit(1);
    }
}
testZeroDev();
//# sourceMappingURL=test-zerodev.js.map