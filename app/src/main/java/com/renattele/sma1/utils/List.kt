package com.renattele.sma1.utils

operator fun <T> List<T>.times(count: Int) = List(count * size) { index ->
    get(index / count)
}