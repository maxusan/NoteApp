package code.banana.todo_app.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import code.banana.todo_app.ui.screens.list.ListScreen
import code.banana.todo_app.ui.viewmodels.SharedViewModel
import code.banana.todo_app.util.Constants.LIST_ARGUMENT_KEY
import code.banana.todo_app.util.Constants.LIST_SCREEN
import code.banana.todo_app.util.toAction

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    viewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
        LaunchedEffect(key1 = action, block = {
            viewModel.action.value = action
        })

        ListScreen(navigateToTaskScreen = navigateToTaskScreen, viewModel = viewModel)
    }
}