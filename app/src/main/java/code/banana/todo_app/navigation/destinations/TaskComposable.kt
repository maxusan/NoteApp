@file:OptIn(ExperimentalAnimationApi::class)

package code.banana.todo_app.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import code.banana.todo_app.ui.screens.task.TaskScreen
import code.banana.todo_app.ui.viewmodels.SharedViewModel
import code.banana.todo_app.util.Action
import code.banana.todo_app.util.Constants

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    viewModel: SharedViewModel
) {
    composable(
        route = Constants.TASK_SCREEN,
        arguments = listOf(navArgument(Constants.TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        }), enterTransition = {
            slideInHorizontally(
                animationSpec = tween(durationMillis = 1000)
            )
        }
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(Constants.TASK_ARGUMENT_KEY)
        LaunchedEffect(key1 = taskId, block = { viewModel.getSelectedTask(taskId = taskId) })

        val selectedTask by viewModel.selectedTask.collectAsState()
        LaunchedEffect(key1 = selectedTask, block = {
            if (selectedTask != null || taskId == -1)
                viewModel.updateTaskFields(task = selectedTask)
        })
        TaskScreen(
            selectedTask = selectedTask,
            viewModel = viewModel,
            navigateToListScreen = navigateToListScreen
        )
    }
}