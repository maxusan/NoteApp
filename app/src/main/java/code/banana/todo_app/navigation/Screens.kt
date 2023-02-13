package code.banana.todo_app.navigation

import androidx.navigation.NavHostController
import code.banana.todo_app.util.Action
import code.banana.todo_app.util.Constants.LIST_SCREEN

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
class Screens(navController: NavHostController) {
    val list: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }
    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/${taskId}")
    }
}