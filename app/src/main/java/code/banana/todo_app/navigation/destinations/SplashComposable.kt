package code.banana.todo_app.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import code.banana.todo_app.navigation.Destination
import code.banana.todo_app.ui.screens.splash.SplashScreen

fun NavGraphBuilder.splashComposable() {
    composable(route = Destination.Splash.fullRoute) {
        SplashScreen()
    }
}