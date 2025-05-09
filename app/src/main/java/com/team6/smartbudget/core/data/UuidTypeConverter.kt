package com.team6.smartbudget.core.data

import androidx.room.TypeConverter
import kotlin.uuid.Uuid

object UuidTypeConverter {
    @TypeConverter
    fun toUuid(uuid: String): Uuid? = Uuid.parse(uuid)

    @TypeConverter
    fun fromUuid(uuid: Uuid?): String? = uuid?.toString()
}
