package code.banana.todo_app.ui.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import code.banana.todo_app.R
import code.banana.todo_app.ui.theme.fabBackgroundColor
import code.banana.todo_app.ui.viewmodels.SharedViewModel
import code.banana.todo_app.util.Action
import code.banana.todo_app.util.SearchAppBarState
import kotlinx.coroutines.launch

/**
 * Created by Maksym Kovalchuk on 2/14/2023.
 */

@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    viewModel: SharedViewModel,
    action: Action,
) {

    LaunchedEffect(key1 = action, block = {
        viewModel.handleDatabaseActions(action)
    })

    val allTasks by viewModel.allTasks.collectAsState()
    val sortState by viewModel.sortState.collectAsState()
    val lowPriorityTasks by viewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by viewModel.highPriorityTasks.collectAsState()

    val searchedTasks by viewModel.searchedTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by viewModel.searchAppBarState
    val searchTextState: String by viewModel.searchTextState

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        onComplete = { viewModel.action.value = it },
        onUndoClicked = {
            viewModel.action.value = it
        },
        taskTitle = viewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                viewModel = viewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
            ListContent(
                modifier = Modifier.padding(it),
                searchAppBarState = searchAppBarState,
                allTasks = allTasks,
                lowPriorityTasks = lowPriorityTasks,
                highPriorityTasks = highPriorityTasks,
                sortState = sortState,
                searchTasks = searchedTasks,
                onSwipeToDelete = { action, task ->
                    viewModel.action.value = action
                    viewModel.updateTaskFields(task = task)
                },
                navigateToTaskScreen = navigateToTaskScreen
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        },
    )
}

@Composable
fun ListFab(onFabClicked: (taskId: Int) -> Unit) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    onComplete: (Action) -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult =
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = setMessage(action = action, taskTitle = taskTitle),
                        actionLabel = setActionLabel(action)
                    )
                undoDeletedTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
            onComplete(Action.NO_ACTION)
        }
    }
}

private fun setMessage(
    action: Action,
    taskTitle: String
): String {
    return when (action) {
        Action.DELETE_ALL -> "All tasks removed"
        else -> "${action.name} : $taskTitle"
    }
}

private fun setActionLabel(action: Action): String {
    return if (action.name == Action.DELETE.name) {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}

@Composable
@Preview
fun ListScreenPreview() {
    ListScreen(navigateToTaskScreen = {}, viewModel = hiltViewModel(), action = Action.NO_ACTION)
}