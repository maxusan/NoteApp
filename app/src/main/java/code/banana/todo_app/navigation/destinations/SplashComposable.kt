package code.banana.todo_app.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import code.banana.todo_app.ui.screens.splash.SplashScreen
import code.banana.todo_app.util.Constants.SPLASH_SCREEN

/**
 * Created by Maksym Kovalchuk on 2/16/2023.
 */
fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = SPLASH_SCREEN
    ) {
        SplashScreen(navigateToListScreen = navigateToListScreen)
    }
}