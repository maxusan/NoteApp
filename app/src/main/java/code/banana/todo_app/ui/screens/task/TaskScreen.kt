package code.banana.todo_app.ui.screens.task

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import code.banana.todo_app.R
import code.banana.todo_app.components.AppAlertDialog
import code.banana.todo_app.ui.screens.task.components.TaskAppBar
import code.banana.todo_app.ui.screens.task.components.TaskContent
import code.banana.todo_app.util.getText
import kotlinx.coroutines.flow.collectLatest


/**
 * Created by Maksym Kovalchuk on 2/15/2023.
 */
@Composable
fun TaskScreen(
    viewModel: TaskScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect("ObserveEffects") {
        viewModel.effect.collectLatest {
            when (it) {
                is TaskScreenEffect.ShowToast -> {
                    Toast.makeText(context, it.text.getText(context), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TaskAppBar(
                modifier = Modifier,
                taskTitle = state.title,
                saveTask = viewModel::saveTask,
                onCloseClicked = viewModel::navigateBack,
                updateTask = viewModel::updateTask,
                deleteTask = viewModel::deleteTask,
                screenState = state.taskState
            )
        }
    ) {
        TaskContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            title = state.title,
            onTitleChange = { viewModel.updateTitle(it) },
            description = state.description,
            onDescriptionChange = { viewModel.updateDescription(it) },
            priority = state.priority,
            onPrioritySelected = { viewModel.updatePriority(it) },
            priorityDropdownExpanded = state.priorityDropdownExpanded,
            onPriorityDropdownClicked = viewModel::onPriorityDropdownClicked,
            dismissPriorityDropdown = viewModel::dismissPriorityDropdown,
        )
    }

    AppAlertDialog(
        title = stringResource(id = R.string.delete_task, state.title),
        message = stringResource(id = R.string.delete_task_confirmation, state.title),
        openDialog = state.showDeleteTaskDialog,
        closeDialog = viewModel::dismissDeleteTaskDialog,
        onYesClicked = viewModel::deleteTaskConfirmed
    )
}

