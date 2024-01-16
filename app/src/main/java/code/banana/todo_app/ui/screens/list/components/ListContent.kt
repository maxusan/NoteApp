package code.banana.todo_app.ui.screens.list.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import code.banana.todo_app.R
import code.banana.todo_app.models.Priority
import code.banana.todo_app.models.Task
import code.banana.todo_app.ui.screens.list.ListScreenState
import code.banana.todo_app.ui.theme.*
import code.banana.todo_app.util.colorByPriority

/**
 * Created by Maksym Kovalchuk on 2/14/2023.
 */
@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    state: ListScreenState,
    onSwipeToDelete: (Task) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    HandleListContent(
        modifier = modifier,
        tasks = state.tasks,
        onSwipeToDelete = onSwipeToDelete,
        navigateToTaskScreen = navigateToTaskScreen
    )
}

@Composable
fun HandleListContent(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onSwipeToDelete: (Task) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(
            modifier = modifier,
            allTasks = tasks,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}

@Composable
fun DisplayTasks(
    modifier: Modifier = Modifier,
    allTasks: List<Task>,
    onSwipeToDelete: (Task) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(allTasks,
            key = { task ->
                task.id
            }) { task ->
            TaskItem(
                task = task,
                navigateToTaskScreen = navigateToTaskScreen,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    Surface(
        modifier = modifier
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
                .padding(LARGE_PADDING),
            horizontalAlignment = Alignment.End
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
                        drawCircle(task.priority.colorByPriority())
                    }
                }
            }
            Text(
                text = task.description,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.taskItemTextColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = task.formattedDate,
                color = MaterialTheme.colors.taskItemTextColor,
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Divider(color = Color.LightGray)
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = LARGEST_PADDING), contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = Color.White,
            modifier = Modifier.rotate(degrees = degrees)
        )
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    TaskItem(task = Task(
        0, "Title", "Desc", Priority.MEDIUM
    ), navigateToTaskScreen = {})
}