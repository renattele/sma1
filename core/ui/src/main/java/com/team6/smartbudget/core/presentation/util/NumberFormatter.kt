package com.team6.smartbudget.core.presentation.util

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import java.util.Locale

object NumberFormatter {
    fun formatMoney(amount: Float): String {
        val symbols = DecimalFormatSymbols(Locale.getDefault())
        symbols.setGroupingSeparator(' ')
        symbols.setDecimalSeparator(',')
        val formatter = DecimalFormat("#,##0.00", symbols)
        return formatter.format(amount)
    }
}
