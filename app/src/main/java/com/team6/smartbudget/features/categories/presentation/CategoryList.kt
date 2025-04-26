package com.team6.smartbudget.features.categories.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.team6.smartbudget.sma1.R
import com.team6.smartbudget.core.presentation.designsystem.components.TButton
import com.team6.smartbudget.core.presentation.designsystem.components.TButtonStyle
import com.team6.smartbudget.core.presentation.designsystem.components.TCard
import com.team6.smartbudget.core.presentation.designsystem.components.TForwardButton
import com.team6.smartbudget.core.presentation.designsystem.components.TLinearProgressBar
import com.team6.smartbudget.core.presentation.designsystem.components.TVerticalSeparator
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.bold
import com.team6.smartbudget.core.presentation.util.NumberFormatter
import com.team6.smartbudget.features.categories.presentation.components.CategoryIcon

private const val PercentMultiplier = 100

@Composable
fun CategoryList(
    categories: List<UiCategoryEntity>,
    onCategoryClick: (UiCategoryEntity) -> Unit,
    onAddTransactionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TCard(modifier) {
        categories.forEachIndexed { index, category ->
            CategoryOverviewItem(
                category = category,
                onClick = {
                    onCategoryClick(category)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = TTheme.spacing.s,
                        vertical = TTheme.spacing.m,
                    ),
            )
            if (index != categories.lastIndex) {
                TVerticalSeparator()
            }
        }
        TButton(
            onClick = onAddTransactionClick,
            Modifier.fillMaxWidth(),
            style = TButtonStyle.secondary(),
        ) {
            Icon(Icons.Outlined.Add, contentDescription = null)
            Text(stringResource(R.string.label_add_transaction))
        }
    }
}

@Composable
private fun CategoryOverviewItem(
    category: UiCategoryEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .clip(CircleShape)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(TTheme.spacing.m),
    ) {
        CategoryIcon(icon = category.icon, color = category.color)
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(TTheme.spacing.m)) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(TTheme.spacing.s),
            ) {
                Text(
                    category.name,
                    modifier = Modifier.weight(1f),
                    style = TTheme.typography.bodyM.bold(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (category.enabled) {
                    Text(
                        stringResource(
                            R.string.label_percent,
                            (category.spentRelativePercent * PercentMultiplier).toInt(),
                        ),
                    )
                } else {
                    Text(
                        stringResource(
                            R.string.label_money,
                            NumberFormatter.formatMoney(category.spentAmount),
                        ),
                    )
                }
            }
            if (category.enabled) {
                TLinearProgressBar(
                    { category.spentRelativePercent },
                    color = if (category.spentPercent >= category.targetPercent) {
                        TTheme.colorScheme.error
                    } else {
                        TTheme.colorScheme.primary
                    },
                )
            }
        }
        TForwardButton(onClick)
    }
}

@PreviewLightDark
@Composable
private fun CategoryListPreview() {
    TPreviewTheme {
        CategoryList(
            categories = defaultCategories(),
            onAddTransactionClick = {},
            onCategoryClick = {},
        )
    }
}
