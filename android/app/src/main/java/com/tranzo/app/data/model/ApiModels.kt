package com.tranzo.app.data.model

/**
 * All request/response DTOs for the Tranzo API.
 */

// ─── Auth Requests ───────────────────────────────────────────

data class SendOtpRequest(val email: String)
data class VerifyOtpRequest(val email: String, val otp: String)
data class GoogleLoginRequest(val idToken: String)
data class RefreshTokenRequest(val refreshToken: String)

// ─── Auth Responses ──────────────────────────────────────────

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val isNewUser: Boolean,
)

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)

data class MessageResponse(val message: String)

// ─── User ────────────────────────────────────────────────────

data class UserResponse(
    val id: String,
    val email: String?,
    val phone: String?,
    val displayName: String?,
    val firstName: String?,
    val lastName: String?,
    val avatarUrl: String?,
    val smartAccount: String,
    val kycStatus: String,
    val emailVerified: Boolean,
    val createdAt: String,
)

data class UpdateProfileRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val displayName: String? = null,
    val avatarUrl: String? = null,
)

data class AccountInfoResponse(
    val smartAccount: String,
    val openfortPlayer: String?,
    val kycStatus: String,
    val createdAt: String,
)

// ─── Balances ────────────────────────────────────────────────

data class BalancesResponse(val balances: List<TokenBalance>)

data class TokenBalance(
    val symbol: String,
    val address: String,
    val decimals: Int,
    val balance: String,
    val formatted: String,
)

data class TokenInfo(
    val symbol: String,
    val address: String,
    val decimals: Int,
)

// ─── Transfers ───────────────────────────────────────────────

data class SendTokenRequest(
    val to: String,
    val tokenAddress: String,
    val amount: String,
    val chainId: Int? = null,
)

data class TransferResponse(
    val intentId: String,
    val txHash: String?,
    val status: String?,
)

data class TransactionHistoryResponse(
    val transactions: List<TransactionItem>,
)

data class TransactionItem(
    val id: String,
    val type: String?,
    val status: String?,
    val transactionHash: String?,
    val createdAt: Long,
)

data class TransactionStatusResponse(
    val id: String,
    val status: String,
    val transactionHash: String?,
)

// ─── Swap ─────────────────────────────────────────────────────

data class SwapQuoteRequest(
    /** On-chain token address of the source token. */
    val fromToken: String,
    /** On-chain token address of the destination token. */
    val toToken: String,
    /** Human-readable amount, e.g. "100.00". */
    val amount: String,
    /** Optional slippage in basis points (default 50 = 0.5%). */
    val slippageBps: Int = 50,
)

data class SwapQuoteResponse(
    val fromToken: String,
    val toToken: String,
    val fromAmount: String,
    val toAmount: String,
    /** Minimum amount out after slippage is applied. */
    val minAmountOut: String,
    /** Human-readable exchange rate, e.g. "1 USDC = 2.45 POL". */
    val rate: String,
    val slippageBps: Int,
    /** Price impact as a percentage string, e.g. "0.12". */
    val priceImpact: String,
    /** Expiry timestamp (epoch seconds) for this quote. */
    val expiresAt: Long,
)

data class SwapExecuteRequest(
    val fromToken: String,
    val toToken: String,
    val amount: String,
    val minAmountOut: String,
    val slippageBps: Int = 50,
)

data class SwapExecuteResponse(
    val intentId: String,
    val txHash: String?,
    val status: String?,
)

// ─── Dripper ─────────────────────────────────────────────────

data class CreateStreamRequest(
    val employeeAddress: String,
    val tokenAddress: String,
    val amountPerSecond: String,
    val startTime: String,
    val endTime: String,
)

data class StreamResponse(
    val stream: StreamDetail,
    val intent: IntentResponse,
)

data class StreamsResponse(val streams: List<StreamDetail>)

data class StreamDetail(
    val id: String,
    val onChainStreamId: Int?,
    val employerId: String,
    val employeeAddress: String,
    val tokenAddress: String,
    val amountPerSecond: String,
    val startTime: String,
    val endTime: String,
    val totalWithdrawn: String,
    val status: String,
    val txHash: String?,
)

data class IntentResponse(
    val id: String,
    val status: String?,
    val transactionHash: String?,
)

// ─── Card ─────────────────────────────────────────────────────

/**
 * Full card details returned by GET /card.
 * Sensitive fields (full PAN, CVV) are only returned when explicitly unlocked.
 */
data class CardResponse(
    val id: String,
    /** "virtual" | "physical" */
    val type: String,
    /** "active" | "frozen" | "pending" | "terminated" */
    val status: String,
    /** Masked card number, e.g. "**** **** **** 4242" */
    val maskedPan: String,
    val cardholderName: String,
    /** MM/YY expiry */
    val expiry: String,
    val network: String,
    /** Spending limit in USD cents. Null = unlimited. */
    val spendLimitCents: Long?,
    val createdAt: String,
)

data class OrderCardRequest(
    /** "virtual" | "physical" */
    val type: String,
    val cardholderName: String,
    /** Required for physical card. */
    val shippingAddress: ShippingAddress? = null,
)

data class ShippingAddress(
    val line1: String,
    val line2: String? = null,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String,
)

data class CardOrderResponse(
    val orderId: String,
    val status: String,
    val estimatedDelivery: String?,
)

data class CardFreezeRequest(
    /** true = freeze, false = unfreeze */
    val frozen: Boolean,
)

data class CardTransactionsResponse(
    val transactions: List<CardTransaction>,
    val total: Int,
    val offset: Int,
    val limit: Int,
)

data class CardTransaction(
    val id: String,
    val merchantName: String,
    val merchantCategory: String?,
    /** Amount in USD cents. */
    val amountCents: Long,
    val currency: String,
    /** "approved" | "declined" | "pending" | "reversed" */
    val status: String,
    val createdAt: String,
)

// ─── Notifications ────────────────────────────────────────────

data class RegisterNotificationTokenRequest(
    /** FCM token from Firebase. */
    val token: String,
    /** "android" | "ios" */
    val platform: String = "android",
    /** App version for debugging. */
    val appVersion: String? = null,
)
