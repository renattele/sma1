package com.team6.smartbudget.features.categories.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team6.smartbudget.sma1.R
import com.team6.smartbudget.core.presentation.designsystem.components.TButton
import com.team6.smartbudget.core.presentation.designsystem.components.TButtonStyle
import com.team6.smartbudget.core.presentation.designsystem.components.TCard
import com.team6.smartbudget.core.presentation.designsystem.components.TSlider
import com.team6.smartbudget.core.presentation.designsystem.components.TVerticalSeparator
import com.team6.smartbudget.core.presentation.designsystem.theme.TPreviewTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.designsystem.theme.bold
import com.team6.smartbudget.features.categories.presentation.components.CategoryIcon

private const val PercentMultiplier = 100
private val SliderHeight = 24.dp

@Composable
fun CategoryEditList(
    categories: List<UiCategoryEntity>,
    onValueChange: (UiCategoryEntity, Float) -> Unit,
    onDelete: (UiCategoryEntity) -> Unit,
    onAdd: (UiCategoryEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    TCard(modifier) {
        categories.forEachIndexed { index, category ->
            val categoryItemModifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = TTheme.spacing.xs,
                    vertical = TTheme.spacing.m,
                )
            if (category.enabled) {
                EnabledCategoryItem(
                    category = category,
                    onDelete = {
                        onDelete(category)
                    },
                    onValueChange = { value ->
                        onValueChange(category, value)
                    },
                    modifier = categoryItemModifier,
                )
            } else {
                SuggestedCategoryItem(
                    category,
                    onAdd = { onAdd(category) },
                    modifier = categoryItemModifier,
                )
            }
            if (index != categories.lastIndex) {
                TVerticalSeparator()
            }
        }
    }
}

@Composable
private fun EnabledCategoryItem(
    category: UiCategoryEntity,
    onDelete: () -> Unit,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .clip(CircleShape),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(TTheme.spacing.s),
    ) {
        CategoryIcon(icon = category.icon, color = category.color)
        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(TTheme.spacing.s),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(TTheme.spacing.s),
            ) {
                Text(
                    category.name,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = TTheme.spacing.s),
                    style = TTheme.typography.bodyM.bold(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    stringResource(
                        R.string.label_percent,
                        (category.targetPercent * PercentMultiplier).toInt(),
                    ),
                )
            }
            TSlider(
                category.targetPercent,
                onValueChange,
                Modifier
                    .fillMaxWidth()
                    .height(SliderHeight),
                valueRange = 0f..1f,
            )
        }
        TButton(onClick = onDelete, style = TButtonStyle.flat()) {
            Icon(Icons.Outlined.Close, contentDescription = null)
        }
    }
}

@Composable
private fun SuggestedCategoryItem(
    category: UiCategoryEntity,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .clip(CircleShape),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(TTheme.spacing.s),
    ) {
        CategoryIcon(icon = category.icon, color = category.color)
        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(TTheme.spacing.s),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(TTheme.spacing.s),
            ) {
                Text(
                    category.name,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = TTheme.spacing.s),
                    style = TTheme.typography.bodyM.bold(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        TButton(onClick = onAdd, style = TButtonStyle.flat()) {
            Icon(Icons.Outlined.Add, contentDescription = null)
        }
    }
}

@Preview
@Composable
private fun CategoryEditListPreview() {
    TPreviewTheme {
        val categories = defaultCategories()
        val mutableCategories = remember { categories.toMutableStateList() }
        CategoryEditList(
            categories = mutableCategories,
            onValueChange = { category, value ->
                val index = mutableCategories.indexOf(category)
                mutableCategories[index] = category.copy(targetPercent = value)
            },
            onDelete = { category ->
                val index = mutableCategories.indexOf(category)
                mutableCategories[index] = category.copy(enabled = false)
            },
            onAdd = { category ->
                val index = mutableCategories.indexOf(category)
                mutableCategories[index] = category.copy(enabled = true)
            },
        )
    }
}
