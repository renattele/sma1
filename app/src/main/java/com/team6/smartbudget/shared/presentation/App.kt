package com.team6.smartbudget.shared.presentation

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.team6.smartbudget.component
import com.team6.smartbudget.core.presentation.designsystem.theme.TTheme
import com.team6.smartbudget.core.presentation.navigation.BottomSheetNavigator
import com.team6.smartbudget.core.presentation.navigation.ModalBottomSheetLayout
import com.team6.smartbudget.core.presentation.viewmodel.LocalViewModelFactory
import com.team6.smartbudget.features.details.presentation.DetailsScreen
import com.team6.smartbudget.features.graph.presentation.GraphScreen
import com.team6.smartbudget.features.onboarding.OnboardingScreen
import com.team6.smartbudget.features.overview.presentation.OverviewScreen
import com.team6.smartbudget.shared.domain.AppConfig
import com.team6.smartbudget.sma1.R

@Composable
fun App(
    config: AppConfig,
    bottomSheetNavigator: BottomSheetNavigator,
    controller: NavHostController,
    modifier: Modifier = Modifier,
) {
    ProvideDependencies {
        TTheme {
            ModalBottomSheetLayout(bottomSheetNavigator, modifier) {
                NavHost(
                    controller,
                    modifier = Modifier.fillMaxSize(),
                    startDestination = Destination.Onboarding,
                ) {
                    destinations(config, controller)
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

private fun NavGraphBuilder.destinations(config: AppConfig, controller: NavController) {
    composable<Destination.Onboarding> {
        OnboardingScreen(onNext = {
            controller.navigate(Destination.Overview) {
                popUpTo(Destination.Onboarding) {
                    inclusive = true
                }
            }
        })
    }

    composable<Destination.Overview> {
        val activity = LocalActivity.current
        val context = LocalContext.current
        OverviewScreen(onGoToTrack = { track ->
            if (config.detailsEnabled) {
                controller.navigate(Destination.TrackDetails(track.artist, track.title))
            } else {
                Toast.makeText(context, R.string.label_feature_not_available, Toast.LENGTH_SHORT)
                    .show()
            }
        }, onGoBack = {
            activity?.finish()
        }, onGoToGraph = {
            controller.navigate(Destination.Graph)
        })
    }
    composable<Destination.TrackDetails> {
        val route = it.toRoute<Destination.TrackDetails>()
        DetailsScreen(route.artist, route.title, onGoBack = {
            controller.navigateUp()
        })
    }

    composable<Destination.Graph> {
        GraphScreen()
    }
}
