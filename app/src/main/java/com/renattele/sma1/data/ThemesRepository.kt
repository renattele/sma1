package com.renattele.sma1.data

import com.renattele.sma1.R
import com.renattele.sma1.domain.Theme

object ThemesRepository {
    val themes = listOf(
        Theme(R.color.red, R.style.Base_Theme_SMA1_Red),
        Theme(R.color.green, R.style.Base_Theme_SMA1_Green),
        Theme(R.color.blue, R.style.Base_Theme_SMA1_Blue)
    )
}