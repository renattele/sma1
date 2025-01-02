package com.renattele.sma1.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

fun <T> RecyclerView.Adapter<*>.inflate(parent: ViewGroup, inflater: (LayoutInflater, ViewGroup, Boolean) -> T): T {
   return inflater(LayoutInflater.from(parent.context), parent, false)
}