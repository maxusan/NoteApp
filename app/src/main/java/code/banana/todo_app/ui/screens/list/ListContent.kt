package code.banana.todo_app.ui.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import code.banana.todo_app.data.models.Priority
import code.banana.todo_app.data.models.Task
import code.banana.todo_app.ui.theme.*
import code.banana.todo_app.util.RequestState

/**
 * Created by Maksym Kovalchuk on 2/14/2023.
 */
@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    allTasks: RequestState<List<Task>>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (allTasks is RequestState.Success) {
        if (allTasks.data.isEmpty()) {
            EmptyContent()
        } else {
            DisplayTasks(
                modifier = modifier,
                allTasks = allTasks.data,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}

@Composable
fun DisplayTasks(
    modifier: Modifier = Modifier,
    allTasks: List<Task>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(allTasks, key = { task ->
            task.id
        }) {
            TaskItem(task = it, navigateToTaskScreen = navigateToTaskScreen)
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigateToTaskScreen(task.id)
            },
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LARGE_PADDING)
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = task.title,
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
                        drawCircle(task.priority.color)
                    }
                }
            }
            Text(
                text = task.description, modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.taskItemTextColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    TaskItem(task = Task(0, "Title", "Desc", Priority.MEDIUM), navigateToTaskScreen = {})
}