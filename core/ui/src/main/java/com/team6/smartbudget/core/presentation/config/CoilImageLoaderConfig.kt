package com.team6.smartbudget.core.presentation.config

import android.content.Context
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.asImage
import coil3.request.crossfade
import com.team6.smartbudget.core.domain.ApplicationConfig
import com.team6.smartbudget.core.ui.R
import javax.inject.Inject

class CoilImageLoaderConfig @Inject constructor(
    private val context: Context,
) : ApplicationConfig {
    override fun configure() {
        val error = context.getDrawable(R.drawable.im_track_thumbnail_fallback)?.asImage()
        SingletonImageLoader.setSafe {
            ImageLoader.Builder(context)
                .crossfade(true)
                .fallback(error)
                .error(error)
                .build()
        }
    }
}
