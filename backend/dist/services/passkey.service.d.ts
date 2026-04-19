export declare class PasskeyService {
    private static RP_NAME;
    private static RP_ID;
    private static ORIGIN;
    /**
     * Step 1: Generate registration options for the client
     */
    static getRegistrationOptions(userId: string, email: string): Promise<import("@simplewebauthn/types").PublicKeyCredentialCreationOptionsJSON>;
    /**
     * Step 2: Verify registration and save the passkey
     */
    static verifyRegistration(userId: string, body: any, expectedChallenge: string): Promise<{
        success: boolean;
    }>;
    /**
     * Step 3: Generate authentication options
     */
    static getAuthenticationOptions(userId: string): Promise<import("@simplewebauthn/types").PublicKeyCredentialRequestOptionsJSON>;
    /**
     * Step 4: Verify authentication
     */
    static verifyAuthentication(userId: string, body: any, expectedChallenge: string): Promise<{
        success: boolean;
    }>;
}
