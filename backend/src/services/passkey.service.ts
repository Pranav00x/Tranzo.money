import { 
  generateRegistrationOptions, 
  verifyRegistrationResponse,
  generateAuthenticationOptions,
  verifyAuthenticationResponse,
  VerifyRegistrationResponseOpts,
  VerifyAuthenticationResponseOpts
} from "@simplewebauthn/server";
import { ENV } from "../config/env.js";
import prisma from "./prisma.service.js";

export class PasskeyService {
  private static RP_NAME = "Tranzo";
  private static RP_ID = process.env.RP_ID || "tranzo.app";
  private static ORIGIN = process.env.ORIGIN || "https://tranzo.app";

  /**
   * Step 1: Generate registration options for the client
   */
  static async getRegistrationOptions(userId: string, email: string) {
    const userPasskeys = await prisma.passkey.findMany({
      where: { userId }
    });

    const options = await generateRegistrationOptions({
      rpName: this.RP_NAME,
      rpID: this.RP_ID,
      userID: userId,
      userName: email,
      attestationType: "none",
      excludeCredentials: userPasskeys.map(p => ({
        id: p.credentialId as any,
        type: "public-key",
      })) as any,
      authenticatorSelection: {
        residentKey: "required",
        userVerification: "preferred",
      },
    });

    return options;
  }

  /**
   * Step 2: Verify registration and save the passkey
   */
  static async verifyRegistration(userId: string, body: any, expectedChallenge: string) {
    const verification = await verifyRegistrationResponse({
      response: body,
      expectedChallenge,
      expectedOrigin: this.ORIGIN,
      expectedRPID: this.RP_ID,
    });

    if (verification.verified && verification.registrationInfo) {
      const { credentialPublicKey, credentialID, counter } = verification.registrationInfo;

      await prisma.passkey.create({
        data: {
          userId,
          credentialId: Buffer.from(credentialID) as any,
          publicKey: Buffer.from(credentialPublicKey) as any,
          counter: BigInt(counter),
          credentialName: body.credentialName || "New Device",
        }
      });

      return { success: true };
    }

    throw new Error("Registration verification failed");
  }

  /**
   * Step 3: Generate authentication options
   */
  static async getAuthenticationOptions(userId: string) {
    const userPasskeys = await prisma.passkey.findMany({
      where: { userId }
    });

    const options = await generateAuthenticationOptions({
      rpID: this.RP_ID,
      allowCredentials: userPasskeys.map(p => ({
        id: p.credentialId as any,
        type: "public-key",
      })) as any,
      userVerification: "preferred",
    });

    return options;
  }

  /**
   * Step 4: Verify authentication
   */
  static async verifyAuthentication(userId: string, body: any, expectedChallenge: string) {
    const passkey = await prisma.passkey.findUnique({
      where: { credentialId: Buffer.from(body.id, "base64url") as any }
    });

    if (!passkey) throw new Error("Passkey not found");

    const verification = await verifyAuthenticationResponse({
      response: body,
      expectedChallenge,
      expectedOrigin: this.ORIGIN,
      expectedRPID: this.RP_ID,
      authenticator: {
        credentialID: passkey.credentialId as any,
        credentialPublicKey: passkey.publicKey as any,
        counter: Number(passkey.counter),
      },
    });

    if (verification.verified) {
      // Update counter
      await prisma.passkey.update({
        where: { id: passkey.id },
        data: { counter: BigInt(verification.authenticationInfo.newCounter) }
      });

      return { success: true };
    }

    throw new Error("Authentication verification failed");
  }
}
