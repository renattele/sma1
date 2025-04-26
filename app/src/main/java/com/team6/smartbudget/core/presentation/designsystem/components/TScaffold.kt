package com.team6.smartbudget.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.team6.smartbudget.sma1.R
import com.team6.smartbudget.core.presentation.designsystem.theme.ProvideTextStyle
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.util.plus

private val TopBarHeight = 80.dp
private val FabHeight = 80.dp

@Composable
fun TScaffold(
    modifier: Modifier = Modifier,
    floatingActionButton: (@Composable () -> Unit)? = null,
    title: (@Composable RowScope.() -> Unit)? = null,
    backButton: (@Composable () -> Unit)? = null,
    content: @Composable (padding: PaddingValues) -> Unit,
) {
    Box(modifier.fillMaxSize()) {
        val topPadding = if (backButton != null || title != null) {
            TopBarHeight
        } else {
            0.dp
        }
        val bottomPadding = if (floatingActionButton != null) {
            FabHeight
        } else {
            0.dp
        }

        content(
            WindowInsets.systemBars.asPaddingValues() +
                PaddingValues(top = topPadding, bottom = bottomPadding),
        )
        TTopBar(
            title = title,
            backButton = backButton,
            modifier = Modifier
                .padding(WindowInsets.statusBars.asPaddingValues())
                .background(TTheme.colorScheme.background),
        )
        if (floatingActionButton != null) {
            Box(
                modifier
                    .padding(WindowInsets.navigationBars.asPaddingValues())
                    .align(Alignment.BottomCenter)
                    .height(FabHeight),
                contentAlignment = Alignment.Center,
            ) {
                floatingActionButton()
            }
        }
    }
}

@Composable
private fun TTopBar(
    title: @Composable (RowScope.() -> Unit)?,
    backButton: @Composable (() -> Unit)?,
    modifier: Modifier,
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(horizontal = TTheme.spacing.m)
            .height(TopBarHeight),
    ) {
        if (backButton != null) {
            Box(
                Modifier
                    .align(Alignment.CenterStart),
            ) {
                backButton()
            }
        }
        if (title != null) {
            Row(
                Modifier
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ProvideTextStyle(TTheme.typography.heading4) {
                    title()
                }
            }
        }
    }
}

@PreviewLightDark
@Preview(showSystemUi = true)
@Composable
private fun TScaffoldPreview() {
    TPreviewTheme(enablePadding = false) {
        TScaffold(title = {
            Text(stringResource(R.string.label_done))
        }, backButton = {
            TButton(onClick = {}, style = TButtonStyle.flat()) {
                Icon(
                    Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                    contentDescription = null,
                )
            }
        }, floatingActionButton = {
            TButton(onClick = {}) {
                Text(stringResource(R.string.label_done))
            }
        }) { padding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(TTheme.colorScheme.onBackgroundLow),
            )
        }
    }
}
