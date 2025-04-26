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
@file:OptIn(ExperimentalMaterial3Api::class)

package com.team6.smartbudget.core.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDestinationBuilder
import androidx.navigation.NavDestinationDsl
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.get
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * Add the [content] [Composable] as bottom sheet content to the [NavGraphBuilder]
 *
 * @param route route for the destination
 * @param arguments list of arguments to associate with destination
 * @param deepLinks list of deep links to associate with the destinations
 * @param content the sheet content at the given destination
 */
fun NavGraphBuilder.bottomSheet(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    confirmValueChange: (SheetValue) -> Boolean = { true },
    content: @Composable (backstackEntry: NavBackStackEntry) -> Unit,
) {
    addDestination(
        BottomSheetNavigator
            .Destination(
                provider[BottomSheetNavigator::class],
                confirmValueChange,
                content,
            ).apply {
                this.route = route
                arguments.fastForEach { (argumentName, argument) ->
                    addArgument(argumentName, argument)
                }
                deepLinks.fastForEach { deepLink ->
                    addDeepLink(deepLink)
                }
            },
    )
}

inline fun <reified T : Any> NavGraphBuilder.bottomSheet(
    deepLinks: List<NavDeepLink> = emptyList(),
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    noinline confirmValueChange: (SheetValue) -> Boolean = { true },
    noinline content: @Composable (backstackEntry: NavBackStackEntry) -> Unit,
) {
    destination(
        BottomSheetNavigatorDestinationBuilder(
            provider[BottomSheetNavigator::class],
            T::class,
            typeMap,
            confirmValueChange,
            content,
        ).apply {
            deepLinks.forEach { deepLink -> deepLink(deepLink) }
        },
    )
}

/** DSL for constructing a new [ComposeNavigator.Destination] */
@NavDestinationDsl
class BottomSheetNavigatorDestinationBuilder :
    NavDestinationBuilder<BottomSheetNavigator.Destination> {
    private val composeNavigator: BottomSheetNavigator
    private val confirmValueChange: (SheetValue) -> Boolean
    private val content: @Composable (NavBackStackEntry) -> Unit

    constructor(
        navigator: BottomSheetNavigator,
        route: String,
        confirmValueChange: (SheetValue) -> Boolean,
        content: @Composable (NavBackStackEntry) -> Unit,
    ) : super(navigator, route) {
        this.composeNavigator = navigator
        this.confirmValueChange = confirmValueChange
        this.content = content
    }

    constructor(
        navigator: BottomSheetNavigator,
        route: KClass<*>,
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>,
        confirmValueChange: (SheetValue) -> Boolean,
        content: @Composable (NavBackStackEntry) -> Unit,
    ) : super(navigator, route, typeMap) {
        this.composeNavigator = navigator
        this.confirmValueChange = confirmValueChange
        this.content = content
    }

    override fun instantiateDestination(): BottomSheetNavigator.Destination =
        BottomSheetNavigator.Destination(composeNavigator, confirmValueChange, content)

    override fun build(): BottomSheetNavigator.Destination = super.build()
}
