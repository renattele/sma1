package com.renattele.sma1.domain

import java.util.UUID

sealed interface MultiEntity {
    val id: String
}

data class ProfileEntity(
    val name: String,
    val pictureUrl: String,
    val bio: String,
    override val id: String = UUID.randomUUID().toString(),
): MultiEntity

data class ListSwitcherEntity(
    val type: ListType,
    override val id: String = UUID.randomUUID().toString(),
): MultiEntity