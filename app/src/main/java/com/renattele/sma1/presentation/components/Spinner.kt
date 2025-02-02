package com.renattele.sma1.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun <T> Spinner(
    selected: T,
    vararg values: Pair<T, String>,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier.clickable { expanded = true }) {
        val selectedLabel =
            remember(selected, values) { values.first { it.first == selected }.second }
        val interactionSource = remember { MutableInteractionSource() }
        LaunchedEffect(interactionSource){
            interactionSource.interactions.collect {
                if (it is PressInteraction.Release) {
                    expanded = true
                }
            }
        }
        OutlinedTextField(
            selectedLabel,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, null)
            },
            modifier = Modifier.fillMaxWidth(),
            interactionSource = interactionSource
        )
        DropdownMenu(expanded, onDismissRequest = {
            expanded = false
        }, Modifier.fillMaxWidth()) {
            values.forEach { (value, label) ->
                DropdownMenuItem(text = {
                    Text(label)
                }, onClick = {
                    onSelect(value)
                    expanded = false
                })
            }
        }
    }
}