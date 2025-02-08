package com.renattele.sma1.presentation.util

import android.content.Context
import android.net.Uri

fun Context.uriFor(resource: Int): Uri =
    Uri.parse("android.resource://$packageName/$resource")