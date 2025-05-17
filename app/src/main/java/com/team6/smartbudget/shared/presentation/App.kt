package com.team6.smartbudget.shared.presentation

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.team6.smartbudget.component
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.navigation.ModalBottomSheetLayout
import com.team6.smartbudget.core.presentation.navigation.rememberBottomSheetNavigator
import com.team6.smartbudget.core.presentation.viewmodel.LocalViewModelFactory
import com.team6.smartbudget.features.details.presentation.DetailsScreen
import com.team6.smartbudget.features.graph.presentation.GraphScreen
import com.team6.smartbudget.features.onboarding.OnboardingScreen
import com.team6.smartbudget.features.overview.presentation.OverviewScreen

@Composable
fun App(modifier: Modifier = Modifier) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val controller = rememberNavController(bottomSheetNavigator)
    ProvideDependencies {
        TTheme {
            ModalBottomSheetLayout(bottomSheetNavigator, modifier) {
                NavHost(
                    controller,
                    modifier = Modifier.fillMaxSize(),
                    startDestination = Destinations.Graph,
                ) {
                    destinations(controller)
                }
            }
        }
    }
}

@Composable
private fun ProvideDependencies(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val viewModelFactory = context.component.viewModelFactory
    CompositionLocalProvider(
        LocalViewModelFactory provides viewModelFactory,
        content = content,
    )
}

private fun NavGraphBuilder.destinations(controller: NavController) {
    composable<Destinations.Onboarding> {
        OnboardingScreen(onNext = {
            controller.navigate(Destinations.Overview) {
                popUpTo(Destinations.Onboarding) {
                    inclusive = true
                }
            }
        })
    }

    composable<Destinations.Overview> {
        val activity = LocalActivity.current
        OverviewScreen(onGoToTrack = { track ->
            controller.navigate(Destinations.TrackDetails(track.artist, track.title))
        }, onGoBack = {
            activity?.finish()
        })
    }
    composable<Destinations.TrackDetails> {
        val route = it.toRoute<Destinations.TrackDetails>()
        DetailsScreen(route.artist, route.title, onGoBack = {
            controller.navigateUp()
        })
    }

    composable<Destinations.Graph> {
        GraphScreen()
    }
}
