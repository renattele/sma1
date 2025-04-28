@file:OptIn(ExperimentalMaterial3Api::class)

package com.team6.smartbudget.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.team6.smartbudget.core.presentation.designsystem.theme.ProvideTextStyle
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.util.FullLineColumn
import com.team6.smartbudget.sma1.R

@Composable
fun TBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded,
    ),
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        containerColor = TTheme.colorScheme.background,
        contentColor = TTheme.colorScheme.onBackground,
    ) {
        content()
    }
}

@Composable
fun TBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberStandardBottomSheetState(),
    header: @Composable RowScope.() -> Unit = {},
    buttons: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    TBottomSheet(onDismissRequest, modifier, sheetState) {
        TBottomSheetLayout(
            header = header,
            buttons = buttons,
            content = content,
        )
    }
}

@Composable
fun TBottomSheetLayout(
    modifier: Modifier = Modifier,
    header: @Composable RowScope.() -> Unit = {},
    buttons: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(TTheme.spacing.l),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(TTheme.spacing.m),
    ) {
        TBottomSheetHeader(Modifier.fillMaxWidth(), header)
        FullLineColumn {
            content()
        }
        TBottomSheetButtons(Modifier.fillMaxWidth(), buttons)
    }
}

@Composable
private fun TBottomSheetButtons(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    FullLineColumn(
        modifier,
        spacing = TTheme.spacing.m,
    ) {
        content()
    }
}

@Composable
private fun TBottomSheetHeader(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(TTheme.spacing.s),
    ) {
        ProvideTextStyle(TTheme.typography.heading5) {
            content()
        }
    }
}

@PreviewLightDark
@Preview(showSystemUi = true)
@Composable
private fun TBottomSheetPreview() {
    TPreviewTheme(enablePadding = false) {
        TBottomSheet(onDismissRequest = {}) {
            TButton(onClick = {}) {
                Text(stringResource(R.string.label_done))
            }
        }
    }
}

@PreviewLightDark
@Preview(showSystemUi = true)
@Composable
private fun TBottomSheetPreviewWithButtons() {
    TPreviewTheme(enablePadding = false) {
        TBottomSheet(
            onDismissRequest = {},
            header = {
                Text(stringResource(R.string.label_done))
            },
            buttons = {
                TButton(onClick = {}, style = TButtonStyle.error()) {
                    Icon(Icons.Outlined.Delete, contentDescription = null)
                    Text(stringResource(R.string.label_delete))
                }
                TButton(onClick = {}) {
                    Text(stringResource(R.string.label_done))
                }
            },
        ) {
            TTextField(TTextFieldAction.Selector(stringResource(R.string.label_done)) { })
        }
    }
}
