package code.banana.todo_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import code.banana.todo_app.navigation.destinations.listComposable
import code.banana.todo_app.util.Constants.LIST_SCREEN

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
@Composable
fun SetupNavigation(navController: NavHostController) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }
    NavHost(navController = navController, startDestination = LIST_SCREEN) {
        listComposable()
    }
}