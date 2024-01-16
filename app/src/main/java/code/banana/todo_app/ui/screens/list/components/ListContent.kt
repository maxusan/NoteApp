@file:OptIn(ExperimentalFoundationApi::class)

package code.banana.todo_app.ui.screens.list.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import code.banana.todo_app.components.TaskItem
import code.banana.todo_app.models.Priority
import code.banana.todo_app.models.Task
import code.banana.todo_app.ui.screens.list.ListScreenState

/**
 * Created by Maksym Kovalchuk on 2/14/2023.
 */
@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    state: ListScreenState,
    listState: LazyListState = rememberLazyListState(),
    onSwipeToDelete: (taskId: Int) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    HandleListContent(
        modifier = modifier,
        listState = listState,
        tasks = state.tasks,
        onSwipeToDelete = onSwipeToDelete,
        navigateToTaskScreen = navigateToTaskScreen
    )
}

@Composable
fun HandleListContent(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    tasks: List<Task>,
    onSwipeToDelete: (taskId: Int) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(
            modifier = modifier,
            listState = listState,
            allTasks = tasks,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}

@Composable
fun DisplayTasks(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    allTasks: List<Task>,
    onSwipeToDelete: (taskId: Int) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        state = listState
    ) {
        items(items = allTasks,
            key = { task ->
                task.id
            }) { task ->
            TaskItem(
                task = task,
                onTaskClicked = navigateToTaskScreen,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement(),
                onSwipeToDelete = onSwipeToDelete
            )
        }
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    TaskItem(task = Task(
        0, "Title", "Desc", Priority.MEDIUM
    ), onTaskClicked = {}, onSwipeToDelete = {})
}