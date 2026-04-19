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

    @POST("auth/twitter")
    suspend fun loginWithTwitter(@Body request: TwitterLoginRequest): AuthResponse

    @POST("auth/passkey/register/options")
    suspend fun getPasskeyRegisterOptions(): Map<String, Any>

    @POST("auth/passkey/register/verify")
    suspend fun verifyPasskeyRegister(@Body request: Map<String, Any>): MessageResponse

    @POST("auth/passkey/login/options")
    suspend fun getPasskeyLoginOptions(@Body request: Map<String, String>): Map<String, Any>

    @POST("auth/passkey/login/verify")
    suspend fun verifyPasskeyLogin(@Body request: Map<String, Any>): AuthResponse

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
        @Query("limit") limit: Int = 20,
    ): TransactionHistoryResponse

    @GET("transfers/status/{intentId}")
    suspend fun getTransactionStatus(
        @Path("intentId") intentId: String,
    ): TransactionStatusResponse

    // ─── Swap ────────────────────────────────────────────────────

    @POST("swap/quote")
    suspend fun getSwapQuote(@Body request: SwapQuoteRequest): SwapQuoteResponse

    @POST("swap/execute")
    suspend fun executeSwap(@Body request: SwapExecuteRequest): SwapExecuteResponse

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

    // ─── Card ─────────────────────────────────────────────────────

    /** Get the user's virtual/physical card details. */
    @GET("card")
    suspend fun getCard(): CardResponse

    /** Order a new virtual or physical card. */
    @POST("card/order")
    suspend fun orderCard(@Body request: OrderCardRequest): CardOrderResponse

    /** Freeze or unfreeze the card. */
    @POST("card/freeze")
    suspend fun setCardFrozen(@Body request: CardFreezeRequest): CardResponse

    /** Paginated card transaction history. */
    @POST("card/{cardId}/activate")
    suspend fun activateCard(@Path("cardId") cardId: String, @Body body: Map<String, String>): SuccessResponse

    @GET("card/transactions")
    suspend fun getCardTransactions(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): CardTransactionsResponse

    // ─── Notifications ────────────────────────────────────────────

    /** Register (or refresh) a push notification token. */
    @POST("notifications/register")
    suspend fun registerNotificationToken(
        @Body request: RegisterNotificationTokenRequest,
    ): MessageResponse

    // ─── Settings ────────────────────────────────────────────────

    @GET("user/profile")
    suspend fun getProfile(): UserResponse

    @PUT("user/profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): UserResponse

    @GET("user/account")
    suspend fun getAccountInfo(): AccountInfoResponse
}
