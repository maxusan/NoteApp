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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import code.banana.todo_app.ui.screens.task.components.TaskAppBar
import code.banana.todo_app.ui.screens.task.components.TaskContent
import code.banana.todo_app.ui.theme.getText
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
            onPrioritySelected = { viewModel.updatePriority(it) }
        )
    }
}


//    val title: String by viewModel.title
//    val description: String by viewModel.description
//    val priority: Priority by viewModel.priority
//    val context = LocalContext.current
//
//    BackHandler(onBackPressed = { navigateToListScreen(Action.NO_ACTION) })
//    Scaffold(topBar = {
//        TaskAppBar(navigateToListScreen = { action ->
//            if (action == Action.NO_ACTION) {
//                navigateToListScreen(action)
//            } else {
//                if (viewModel.validateFields())
//                    navigateToListScreen(action)
//                else
//                    displayToast(context = context)
//            }
//        }, selectedTask = selectedTask)
//    }, content = {
//        TaskContent(
//            title = title,
//            onTitleChange = { viewModel.updateTitle(it) },
//            description = description,
//            onDescriptionChange = { viewModel.description.value = it },
//            priority = priority,
//            onPrioritySelected = { viewModel.priority.value = it },
//            modifier = Modifier.padding(it)
//        )
//    })
//    }
//
//fun displayToast(context: Context) {
//    Toast.makeText(context, context.getString(R.string.fields_empty), Toast.LENGTH_SHORT).show()
//}
//
//@Composable
//fun BackHandler(
//    onBackPressedDispatcher: OnBackPressedDispatcher? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
//    onBackPressed: () -> Unit
//) {
//    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
//
//    val backCallback = remember {
//        object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                currentOnBackPressed()
//            }
//
//        }
//    }
//
//    DisposableEffect(key1 = onBackPressedDispatcher, effect = {
//        onBackPressedDispatcher?.addCallback(backCallback)
//        onDispose {
//            backCallback.remove()
//        }
//    })
//}
