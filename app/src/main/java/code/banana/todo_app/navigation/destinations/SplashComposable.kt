@file:OptIn(ExperimentalAnimationApi::class)

package code.banana.todo_app.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import code.banana.todo_app.ui.screens.splash.SplashScreen
import code.banana.todo_app.util.Constants.SPLASH_SCREEN
import com.google.accompanist.navigation.animation.composable

/**
 * Created by Maksym Kovalchuk on 2/16/2023.
 */
fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = SPLASH_SCREEN,
        exitTransition = {
            slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(1000)
            )
        }
    ) {
        SplashScreen(navigateToListScreen = navigateToListScreen)
    }
}