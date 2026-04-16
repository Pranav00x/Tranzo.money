import prisma from "./prisma.service.js";
import { OpenfortService } from "./openfort.service.js";
export class StreamService {
    /**
     * Create a new salary stream via the TranzoDripper contract.
     */
    static async createStream(params) {
        const employer = await prisma.user.findUniqueOrThrow({
            where: { id: params.employerId },
        });
        // Check if employee is a registered user
        const employee = await prisma.user.findFirst({
            where: { smartAccount: params.employeeAddress.toLowerCase() },
        });
        // Execute createStream on TranzoDripper via Openfort
        const totalDurationSec = Math.floor((params.endTime.getTime() - params.startTime.getTime()) / 1000);
        const totalAmount = (BigInt(params.amountPerSecond) * BigInt(totalDurationSec)).toString();
        const intent = await OpenfortService.executeInteraction({
            playerId: employer.openfortPlayer,
            contract: "DRIPPER_CONTRACT_ADDRESS", // Will be configured
            functionName: "createStream",
            functionArgs: [
                params.employeeAddress,
                params.tokenAddress,
                totalAmount,
                Math.floor(params.startTime.getTime() / 1000),
                Math.floor(params.endTime.getTime() / 1000),
            ],
        });
        // Save stream record
        const stream = await prisma.stream.create({
            data: {
                employerId: params.employerId,
                employeeId: employee?.id,
                employeeAddress: params.employeeAddress.toLowerCase(),
                tokenAddress: params.tokenAddress,
                amountPerSecond: params.amountPerSecond,
                startTime: params.startTime,
                endTime: params.endTime,
                txHash: intent.response?.transactionHash,
            },
        });
        return { stream, intent };
    }
    /**
     * Withdraw accrued amount from a stream.
     */
    static async withdrawFromStream(streamId, userId) {
        const stream = await prisma.stream.findUniqueOrThrow({
            where: { id: streamId },
        });
        // Verify the caller is the employee
        const user = await prisma.user.findUniqueOrThrow({
            where: { id: userId },
        });
        if (stream.employeeAddress.toLowerCase() !==
            user.smartAccount.toLowerCase()) {
            throw new Error("Only the stream recipient can withdraw");
        }
        if (!stream.onChainStreamId) {
            throw new Error("Stream not yet confirmed on-chain");
        }
        const intent = await OpenfortService.executeInteraction({
            playerId: user.openfortPlayer,
            contract: "DRIPPER_CONTRACT_ADDRESS",
            functionName: "withdrawFromStream",
            functionArgs: [stream.onChainStreamId],
        });
        return { intent };
    }
    /**
     * Cancel a stream (employer only).
     */
    static async cancelStream(streamId, userId) {
        const stream = await prisma.stream.findUniqueOrThrow({
            where: { id: streamId },
        });
        if (stream.employerId !== userId) {
            throw new Error("Only the stream creator can cancel");
        }
        if (!stream.onChainStreamId) {
            throw new Error("Stream not yet confirmed on-chain");
        }
        const user = await prisma.user.findUniqueOrThrow({
            where: { id: userId },
        });
        const intent = await OpenfortService.executeInteraction({
            playerId: user.openfortPlayer,
            contract: "DRIPPER_CONTRACT_ADDRESS",
            functionName: "cancelStream",
            functionArgs: [stream.onChainStreamId],
        });
        await prisma.stream.update({
            where: { id: streamId },
            data: { status: "CANCELLED" },
        });
        return { intent };
    }
    /**
     * List streams for a user (as employer or employee).
     */
    static async listStreams(userId, role) {
        const user = await prisma.user.findUniqueOrThrow({
            where: { id: userId },
        });
        if (role === "employer") {
            return prisma.stream.findMany({
                where: { employerId: userId },
                orderBy: { createdAt: "desc" },
            });
        }
        return prisma.stream.findMany({
            where: {
                OR: [
                    { employeeId: userId },
                    { employeeAddress: user.smartAccount.toLowerCase() },
                ],
            },
            orderBy: { createdAt: "desc" },
        });
    }
    /**
     * Get a single stream by ID.
     */
    static async getStream(streamId) {
        return prisma.stream.findUniqueOrThrow({
            where: { id: streamId },
        });
    }
}
//# sourceMappingURL=stream.service.js.map