package com.team6.smartbudget.core.presentation.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.team6.smartbudget.core.presentation.util.runWhen

object TTheme {
    val colorScheme: TColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalTColorScheme.current
    val typography: TTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTTypography.current

    val spacing: TSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalTSpacing.current

    val shape: TShape
        @Composable
        @ReadOnlyComposable
        get() = LocalTShape.current
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme =
        remember(darkTheme) {
            if (darkTheme) darkTColorScheme() else lightTColorScheme()
        }
    val typography = remember { defaultTTypography() }
    val spacing = remember { defaultTSpacing() }
    val shape = remember { defaultTShape() }
    // MaterialTheme is needed for platform-specific features like ripple
    MaterialTheme {
        CompositionLocalProvider(
            LocalTColorScheme provides colorScheme,
            LocalTTypography provides typography,
            LocalTSpacing provides spacing,
            LocalTShape provides shape,
            LocalTextStyle provides typography.bodyM,
            LocalContentColor provides colorScheme.onBackground,
            content = content,
        )
    }
}

@Composable
fun TPreviewTheme(
    modifier: Modifier = Modifier,
    enablePadding: Boolean = true,
    content: @Composable () -> Unit,
) {
    TTheme {
        Box(
            modifier
                .background(TTheme.colorScheme.background)
                .runWhen(enablePadding) {
                    padding(TTheme.spacing.l)
                },
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}
