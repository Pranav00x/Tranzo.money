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
    val avatarUrl: String?,
    val smartAccount: String,
    val kycStatus: String,
    val emailVerified: Boolean,
    val createdAt: String,
)

data class UpdateProfileRequest(
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
