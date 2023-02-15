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
) {
    LaunchedEffect(key1 = true) {
        viewModel.getAllTasks()
    }
    val action by viewModel.action
    val allTasks by viewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by viewModel.searchAppBarState
    val searchTextState: String by viewModel.searchTextState

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { viewModel.handleDatabaseActions(action = action) },
        taskTitle = viewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                viewModel = viewModel,
                searchAppBarState = searchAppBarState,
                searchTextState
            )
        },
        content = {
            ListContent(
                modifier = Modifier.padding(it),
                allTasks,
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
    handleDatabaseActions: () -> Unit,
    taskTitle: String,
    action: Action
) {
    handleDatabaseActions()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult =
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "${action.name} : $taskTitle",
                        actionLabel = "OK"
                    )
            }
        }
    }
}

@Composable
@Preview
fun ListScreenPreview() {
    ListScreen(navigateToTaskScreen = {}, viewModel = hiltViewModel())
}