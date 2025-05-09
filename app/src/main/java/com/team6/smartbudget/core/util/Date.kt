package com.team6.smartbudget.core.util

import java.time.Instant
import java.util.Date
import kotlin.time.Duration
import kotlin.time.toJavaDuration

fun Date(millis: Long): Date {
    return Date.from(Instant.ofEpochMilli(millis))
}

fun Date.hasPassed(duration: Duration): Boolean {
    val now = Instant.now()
    val thisInstant = this.toInstant()
    return java.time.Duration.between(thisInstant, now) >= duration.toJavaDuration()
}
