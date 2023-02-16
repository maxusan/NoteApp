@file:OptIn(ExperimentalAnimationApi::class)

package code.banana.todo_app.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.*
import code.banana.todo_app.navigation.destinations.listComposable
import code.banana.todo_app.navigation.destinations.splashComposable
import code.banana.todo_app.navigation.destinations.taskComposable
import code.banana.todo_app.ui.viewmodels.SharedViewModel
import code.banana.todo_app.util.Constants.LIST_SCREEN
import code.banana.todo_app.util.Constants.SPLASH_SCREEN
import com.google.accompanist.navigation.animation.AnimatedNavHost

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
@Composable
fun Navigation(navController: NavHostController, viewModel: SharedViewModel) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }
    AnimatedNavHost(navController = navController, startDestination = SPLASH_SCREEN) {
        splashComposable(
            navigateToListScreen = screen.splash
        )
        listComposable(
            navigateToTaskScreen = screen.list,
            viewModel = viewModel
        )
        taskComposable(
            navigateToListScreen = screen.task,
            viewModel = viewModel
        )
    }
}