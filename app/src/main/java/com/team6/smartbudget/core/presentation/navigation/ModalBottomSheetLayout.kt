/*
 *  Copyright 2024 stefanoq21
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */
package com.team6.smartbudget.core.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.team6.smartbudget.core.presentation.designsystem.components.TBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetLayout(
    bottomSheetNavigator: BottomSheetNavigator,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        bottomSheetNavigator.sheetInitializer()
        content()
    }

    val context = LocalContext.current

    if (bottomSheetNavigator.sheetEnabled) {
        TBottomSheet(
            onDismissRequest = bottomSheetNavigator.onDismissRequest,
            sheetState = bottomSheetNavigator.sheetState,
        ) {
            CompositionLocalProvider(
                LocalContext provides context,
            ) {
                bottomSheetNavigator.sheetContent()
            }
        }
    }
}
