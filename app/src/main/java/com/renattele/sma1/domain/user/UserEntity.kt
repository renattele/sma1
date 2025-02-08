package com.renattele.sma1.domain.user

import java.util.UUID

data class UserEntity(
    val id: UUID,
    val username: String,
    val passwordHash: String
)