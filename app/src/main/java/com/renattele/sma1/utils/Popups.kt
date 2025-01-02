package com.renattele.sma1.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Context.showToast(
    text: String,
    duration: Int = Toast.LENGTH_LONG
) {
    Toast.makeText(this, text, duration).show()
}

fun View.snackbar(text: String, duration: Int = Snackbar.LENGTH_LONG) =
    Snackbar.make(this, text, duration)

fun View.showSnackbar(text: String, duration: Int = Snackbar.LENGTH_LONG) {
    snackbar(text, duration).show()
}