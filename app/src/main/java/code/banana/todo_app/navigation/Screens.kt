package code.banana.todo_app.navigation

import androidx.navigation.NavHostController
import code.banana.todo_app.util.Action
import code.banana.todo_app.util.Constants.LIST_SCREEN
import code.banana.todo_app.util.Constants.SPLASH_SCREEN

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
class Screens(navController: NavHostController) {
    val task: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }
    val list: (Int) -> Unit = { taskId ->
        navController.navigate("task/${taskId}")
    }
    val splash: () -> Unit = {
        navController.navigate("list/${Action.NO_ACTION.name}") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }
}