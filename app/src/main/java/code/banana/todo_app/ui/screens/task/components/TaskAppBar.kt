package code.banana.todo_app.ui.screens.task.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import code.banana.todo_app.R
import code.banana.todo_app.ui.screens.task.TaskScreenState
import code.banana.todo_app.ui.theme.topAppBarBackgroundColor
import code.banana.todo_app.ui.theme.topAppBarContentColor

/**
 * Created by Maksym Kovalchuk on 2/15/2023.
 */
@Composable
fun TaskAppBar(
    modifier: Modifier = Modifier,
    taskTitle: String,
    saveTask: () -> Unit,
    onCloseClicked: () -> Unit,
    updateTask: () -> Unit,
    deleteTask: () -> Unit,
    screenState: TaskScreenState.ScreenState,
) {
    when (screenState) {
        TaskScreenState.ScreenState.AddTask -> NewTaskAppBar(
            modifier = modifier,
            onCloseClicked = onCloseClicked,
            saveTask = saveTask
        )

        TaskScreenState.ScreenState.EditTask -> ExistingTaskAppBar(
            modifier = modifier,
            onCloseClicked = onCloseClicked,
            updateTask = updateTask,
            deleteTask = deleteTask,
            taskTitle = taskTitle
        )
        TaskScreenState.ScreenState.Default -> {}
    }
}

@Composable
fun NewTaskAppBar(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit,
    saveTask: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            BackAction(onBackClicked = onCloseClicked)
        },
        title = {
            Text(
                text = stringResource(R.string.add_task),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            AddAction(onAddClicked = saveTask)
        }
    )
}

@Composable
fun ExistingTaskAppBar(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit,
    updateTask: () -> Unit,
    deleteTask: () -> Unit,
    taskTitle: String,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            CloseAction(onCloseClicked = onCloseClicked)
        },
        title = {
            Text(
                text = taskTitle,
                color = MaterialTheme.colors.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            ExistingTaskAppBarActions(
                updateTask = updateTask,
                deleteTask = deleteTask
            )
        }
    )
}

//
@Composable
fun ExistingTaskAppBarActions(
    updateTask: () -> Unit,
    deleteTask: () -> Unit,
) {
//    var openDialog by remember {
//        mutableStateOf(false)
//    }
//    DisplayAlertDialog(
//        title = stringResource(id = R.string.delete_task, task.title),
//        message = stringResource(id = R.string.delete_task_confirmation, task.title),
//        openDialog = openDialog,
//        closeDialog = { openDialog = false },
//        onYesClicked = { navigateToListScreen(Action.DELETE) })

    DeleteAction(onDeleteClicked = deleteTask)
    UpdateAction(onUpdateClicked = updateTask)
}

@Composable
fun BackAction(onBackClicked: () -> Unit) {
    IconButton(onClick = onBackClicked) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_arrow),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun CloseAction(onCloseClicked: () -> Unit) {
    IconButton(onClick = onCloseClicked) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(R.string.back_arrow),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun AddAction(onAddClicked: () -> Unit) {
    IconButton(onClick = onAddClicked) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.add_task),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun DeleteAction(onDeleteClicked: () -> Unit) {
    IconButton(onClick = { onDeleteClicked() }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}


@Composable
fun UpdateAction(onUpdateClicked: () -> Unit) {
    IconButton(onClick = onUpdateClicked) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.update_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}