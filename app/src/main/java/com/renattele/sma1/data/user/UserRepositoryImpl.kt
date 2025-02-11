package com.renattele.sma1.data.user

import android.content.SharedPreferences
import androidx.core.content.edit
import com.renattele.sma1.data.HashUtil
import com.renattele.sma1.data.hashMatches
import com.renattele.sma1.domain.user.AddUserRequest
import com.renattele.sma1.domain.user.AddUserResponse
import com.renattele.sma1.domain.user.AuthenticateRequest
import com.renattele.sma1.domain.user.AuthenticateResponse
import com.renattele.sma1.domain.user.UserEntity
import com.renattele.sma1.domain.user.UserRepository

private const val CURRENT_USER_KEY = "user"

class UserRepositoryImpl(
    private val dao: UserDao,
    private val prefs: SharedPreferences,
) : UserRepository {
    override suspend fun registerUser(addUserRequest: AddUserRequest): AddUserResponse {
        val previous = dao.findByUsername(addUserRequest.username)
        if (previous != null) return AddUserResponse.AlreadyRegistered
        dao.add(
            DbUserEntity(
                username = addUserRequest.username,
                passwordHash = HashUtil.hash(addUserRequest.password)
            )
        )
        val user = dao.findByUsername(addUserRequest.username)
            ?: return AddUserResponse.FailedToRegister
        return AddUserResponse.Success(user.toEntity())
    }

    override suspend fun authenticate(request: AuthenticateRequest): AuthenticateResponse {
        val user =
            dao.findByUsername(request.username) ?: return AuthenticateResponse.WrongCredentials
        val passwordCorrect = request.password hashMatches user.passwordHash
        if (!passwordCorrect) return AuthenticateResponse.WrongCredentials
        prefs.edit {
            putString(CURRENT_USER_KEY, user.username)
        }
        return AuthenticateResponse.Authenticated
    }

    override suspend fun getCurrentAuthenticatedUser(): UserEntity? {
        val username = prefs.getString(CURRENT_USER_KEY, null) ?: return null
        val user = dao.findByUsername(username) ?: return null
        return user.toEntity()
    }

    override suspend fun logout() {
        prefs.edit {
            remove(CURRENT_USER_KEY)
        }
    }
}