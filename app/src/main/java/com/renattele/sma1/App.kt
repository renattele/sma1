package com.renattele.sma1

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class App: Application() {
    val glide by lazy {
        Glide.with(this)
    }
}

val Context.glide: RequestManager
    get() = (applicationContext as App).glide