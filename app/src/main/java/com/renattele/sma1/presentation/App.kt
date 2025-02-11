package com.renattele.sma1.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.renattele.sma1.domain.RootModule
import com.renattele.sma1.presentation.screen.auth.SignInScreen
import com.renattele.sma1.presentation.screen.auth.SignUpScreen
import com.renattele.sma1.presentation.screen.auth.rememberSignInScreenState
import com.renattele.sma1.presentation.screen.auth.rememberSignUpScreenState
import com.renattele.sma1.presentation.screen.splash.SplashScreen
import com.renattele.sma1.presentation.screen.splash.rememberSplashScreenState
import com.renattele.sma1.presentation.screen.wallpaper.NewWallpaperScreen
import com.renattele.sma1.presentation.screen.wallpaper.WallpaperScreen
import com.renattele.sma1.presentation.screen.wallpaper.rememberNewWallpaperScreenState
import com.renattele.sma1.presentation.screen.wallpaper.rememberWallpaperScreenState
import com.renattele.sma1.presentation.util.navigateWithPopBackStack

@Composable
fun App(rootModule: RootModule, modifier: Modifier = Modifier) {
    val controller = rememberNavController()
    NavHost(
        navController = controller,
        startDestination = Destination.SPLASH,
        modifier = modifier
    ) {
        composable(Destination.SPLASH) {
            val state = rememberSplashScreenState(rootModule.userRepository,
                onGoToMain = {
                    controller.navigateWithPopBackStack(Destination.WALLPAPERS)
                }, onGoToSignIn = {
                    controller.navigateWithPopBackStack(Destination.SIGN_IN)
                })
            SplashScreen(state)
        }
        composable(Destination.SIGN_IN) {
            val state = rememberSignInScreenState(
                userRepository = rootModule.userRepository,
                onGoToSignUp = { controller.navigate(Destination.SIGN_UP) },
                onGoToMain = {
                    controller.navigateWithPopBackStack(Destination.WALLPAPERS)
                }
            )
            SignInScreen(state)
        }
        composable(Destination.SIGN_UP) {
            val state = rememberSignUpScreenState(
                userRepository = rootModule.userRepository,
                onGoToSignIn = { controller.navigate(Destination.SIGN_IN) },
                onGoToMainScreen = {
                    controller.navigateWithPopBackStack(Destination.WALLPAPERS)
                }
            )
            SignUpScreen(state)
        }
        composable(Destination.WALLPAPERS) {
            val state = rememberWallpaperScreenState(
                rootModule.userRepository,
                rootModule.wallpaperRepository,
                onGoToAddWallpaper = {
                    controller.navigate(Destination.NEW_WALLPAPER)
                }, onGoToLogin = {
                    controller.navigateWithPopBackStack(Destination.SIGN_IN)
                })
            WallpaperScreen(state)
        }
        composable(Destination.NEW_WALLPAPER) {
            val state =
                rememberNewWallpaperScreenState(rootModule.wallpaperRepository, onGoToBack = {
                    controller.navigateUp()
                })
            NewWallpaperScreen(state)
        }
    }
}