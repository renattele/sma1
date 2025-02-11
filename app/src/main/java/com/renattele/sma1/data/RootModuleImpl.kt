package com.renattele.sma1.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.renattele.sma1.data.user.UserRepositoryImpl
import com.renattele.sma1.data.wallpaper.WallpaperRepositoryImpl
import com.renattele.sma1.domain.RootModule
import com.renattele.sma1.domain.user.UserRepository
import com.renattele.sma1.domain.wallpaper.WallpaperRepository

class RootModuleImpl(context: Context) : RootModule {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val db = AppDatabase.create(context)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "auth",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    override val userRepository: UserRepository by lazy { UserRepositoryImpl(db.userDao, sharedPreferences) }
    override val wallpaperRepository: WallpaperRepository by lazy {
        WallpaperRepositoryImpl(db.wallpaperDao, userRepository)
    }
}