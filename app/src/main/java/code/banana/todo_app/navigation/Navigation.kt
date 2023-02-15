package code.banana.todo_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import code.banana.todo_app.navigation.destinations.listComposable
import code.banana.todo_app.navigation.destinations.taskComposable
import code.banana.todo_app.ui.viewmodels.SharedViewModel
import code.banana.todo_app.util.Constants.LIST_SCREEN

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
@Composable
fun Navigation(navController: NavHostController, viewModel: SharedViewModel) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }
    NavHost(navController = navController, startDestination = LIST_SCREEN) {
        listComposable(
            navigateToTaskScreen = screen.task,
            viewModel = viewModel
        )
        taskComposable(
            navigateToListScreen = screen.list,
            viewModel = viewModel
        )
    }
}