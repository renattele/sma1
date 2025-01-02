package com.renattele.sma1.utils

import android.view.LayoutInflater
import android.view.ViewGroup

fun <T> ViewGroup.attachBinding(inflater: (LayoutInflater, ViewGroup, Boolean) -> T): T {
   return inflater(LayoutInflater.from(context), this, false)
}