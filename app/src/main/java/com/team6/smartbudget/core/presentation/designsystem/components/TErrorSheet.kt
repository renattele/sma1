package com.team6.smartbudget.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team6.smartbudget.core.presentation.designsystem.theme.ProvideTextStyleAndColor
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.sma1.R

private val WarningIconSize = 64.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TErrorSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded,
        skipHiddenState = false,
    ),
    content: @Composable () -> Unit,
) {
    TBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(TTheme.spacing.l),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(TTheme.spacing.m),
        ) {
            Icon(
                Icons.Outlined.Warning,
                contentDescription = null,
                Modifier.size(WarningIconSize),
                tint = TTheme.colorScheme.error,
            )
            Text(
                stringResource(R.string.label_something_went_wrong),
                style = TTheme.typography.heading5,
            )
            ProvideTextStyleAndColor(
                TTheme.typography.bodyL,
                TTheme.colorScheme.onBackgroundMedium,
            ) {
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TErrorSheetPreview() {
    TTheme {
        TErrorSheet(onDismissRequest = {}) {
            Text(stringResource(R.string.label_something_went_wrong))
        }
    }
}
