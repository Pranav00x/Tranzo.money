package com.tranzo.app.data.api

import com.tranzo.app.data.model.*
import retrofit2.http.*

/**
 * Tranzo Backend API interface.
 */
interface TranzoApi {

    // ─── Auth ────────────────────────────────────────────────────

    @POST("auth/email")
    suspend fun sendOtp(@Body request: SendOtpRequest): MessageResponse

    @POST("auth/verify-otp")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): AuthResponse

    @POST("auth/google")
    suspend fun loginWithGoogle(@Body request: GoogleLoginRequest): AuthResponse

    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): TokenResponse

    @GET("auth/me")
    suspend fun getMe(): UserResponse

    @POST("auth/logout")
    suspend fun logout(): MessageResponse

    // ─── Balances ────────────────────────────────────────────────

    @GET("balances")
    suspend fun getBalances(@Query("chainId") chainId: Int? = null): BalancesResponse

    @GET("balances/tokens")
    suspend fun getTokens(): Map<String, List<TokenInfo>>

    // ─── Transfers ───────────────────────────────────────────────

    @POST("transfers/send")
    suspend fun sendToken(@Body request: SendTokenRequest): TransferResponse

    @GET("transfers/history")
    suspend fun getTransactionHistory(
        @Query("limit") limit: Int = 20
    ): TransactionHistoryResponse

    @GET("transfers/status/{intentId}")
    suspend fun getTransactionStatus(
        @Path("intentId") intentId: String
    ): TransactionStatusResponse

    // ─── Dripper ─────────────────────────────────────────────────

    @POST("dripper")
    suspend fun createStream(@Body request: CreateStreamRequest): StreamResponse

    @GET("dripper")
    suspend fun listStreams(@Query("role") role: String): StreamsResponse

    @GET("dripper/{id}")
    suspend fun getStream(@Path("id") streamId: String): StreamDetail

    @POST("dripper/{id}/withdraw")
    suspend fun withdrawFromStream(@Path("id") streamId: String): IntentResponse

    @POST("dripper/{id}/cancel")
    suspend fun cancelStream(@Path("id") streamId: String): IntentResponse

    // ─── Settings ────────────────────────────────────────────────

    @GET("user/profile")
    suspend fun getProfile(): UserResponse

    @PUT("user/profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): UserResponse

    @GET("user/account")
    suspend fun getAccountInfo(): AccountInfoResponse
}
