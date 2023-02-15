package code.banana.todo_app.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import code.banana.todo_app.R
import code.banana.todo_app.data.models.Priority
import code.banana.todo_app.data.models.Task
import code.banana.todo_app.ui.viewmodels.SharedViewModel
import code.banana.todo_app.util.Action

/**
 * Created by Maksym Kovalchuk on 2/15/2023.
 */
@Composable
fun TaskScreen(
    selectedTask: Task?,
    navigateToListScreen: (Action) -> Unit,
    viewModel: SharedViewModel
) {
    val title: String by viewModel.title
    val description: String by viewModel.description
    val priority: Priority by viewModel.priority
    val context = LocalContext.current
    Scaffold(topBar = {
        TaskAppBar(navigateToListScreen = { action ->
            if (action == Action.NO_ACTION) {
                navigateToListScreen(action)
            } else {
                if (viewModel.validateFields())
                    navigateToListScreen(action)
                else
                    displayToast(context = context)
            }
        }, selectedTask = selectedTask)
    }, content = {
        TaskContent(
            title = title,
            onTitleChange = { viewModel.updateTitle(it) },
            description = description,
            onDescriptionChange = { viewModel.description.value = it },
            priority = priority,
            onPrioritySelected = { viewModel.priority.value = it },
            modifier = Modifier.padding(it)
        )
    })
}

fun displayToast(context: Context) {
    Toast.makeText(context, context.getString(R.string.fields_empty), Toast.LENGTH_SHORT).show()
}
