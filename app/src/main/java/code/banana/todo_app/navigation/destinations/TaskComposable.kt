package code.banana.todo_app.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import code.banana.todo_app.ui.screens.list.ListScreen
import code.banana.todo_app.util.Action
import code.banana.todo_app.util.Constants

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit
){
    composable(
        route = Constants.TASK_SCREEN,
        arguments = listOf(navArgument(Constants.TASK_ARGUMENT_KEY){
            type = NavType.IntType
        })
    ){

    }
}