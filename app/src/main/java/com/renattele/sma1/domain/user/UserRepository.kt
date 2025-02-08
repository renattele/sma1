package com.renattele.sma1.domain.user

interface UserRepository {
    suspend fun registerUser(addUserRequest: AddUserRequest): AddUserResponse
    suspend fun authenticate(request: AuthenticateRequest): AuthenticateResponse
    suspend fun getCurrentAuthenticatedUser(): UserEntity?
    suspend fun logout()
}

data class AddUserRequest(
    val username: String,
    val password: String
)

sealed class AddUserResponse {
    data class Success(val user: UserEntity): AddUserResponse()
    data object AlreadyRegistered: AddUserResponse()
    data object FailedToRegister: AddUserResponse()
}

data class AuthenticateRequest(
    val username: String,
    val password: String
)

sealed class AuthenticateResponse {
    data object Authenticated: AuthenticateResponse()
    data object WrongCredentials: AuthenticateResponse()
}