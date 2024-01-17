package code.banana.todo_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import code.banana.todo_app.navigation.destinations.splashComposable
import code.banana.todo_app.navigation.destinations.taskDetailsComposable
import code.banana.todo_app.navigation.destinations.tasksListComposable

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Destination,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.fullRoute,
    ) {

        splashComposable()
        tasksListComposable()
        taskDetailsComposable()
    }
}