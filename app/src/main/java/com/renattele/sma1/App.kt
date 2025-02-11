package com.renattele.sma1

import android.app.Application
import com.renattele.sma1.data.RootModuleImpl
import com.renattele.sma1.domain.RootModule

class App: Application() {
    val rootModule: RootModule by lazy { RootModuleImpl(this) }
}