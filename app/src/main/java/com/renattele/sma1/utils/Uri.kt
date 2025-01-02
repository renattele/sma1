package com.renattele.sma1.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri

fun Context.uriFor(resource: Int) =
    Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(resources.getResourcePackageName(resource))
        .appendPath(resources.getResourceTypeName(resource))
        .appendPath(resources.getResourceEntryName(resource))
        .build()