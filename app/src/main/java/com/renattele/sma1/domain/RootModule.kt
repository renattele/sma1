package com.renattele.sma1.domain

import com.renattele.sma1.domain.user.UserRepository
import com.renattele.sma1.domain.wallpaper.WallpaperRepository

interface RootModule {
    val userRepository: UserRepository
    val wallpaperRepository: WallpaperRepository
}