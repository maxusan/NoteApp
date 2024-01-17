@file:OptIn(ExperimentalMaterialApi::class)

package code.banana.todo_app.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.ThresholdConfig
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import code.banana.todo_app.R
import code.banana.todo_app.models.Task
import code.banana.todo_app.ui.theme.HighPriorityColor
import code.banana.todo_app.ui.theme.LARGEST_PADDING
import code.banana.todo_app.ui.theme.LARGE_PADDING
import code.banana.todo_app.ui.theme.PRIORITY_INDICATOR_SIZE
import code.banana.todo_app.ui.theme.TASK_ITEM_ELEVATION
import code.banana.todo_app.ui.theme.taskItemBackgroundColor
import code.banana.todo_app.ui.theme.taskItemTextColor
import code.banana.todo_app.util.colorByPriority
import kotlinx.coroutines.delay

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onSwipeToDelete: (taskId: Int) -> Unit,
    onTaskClicked: (taskId: Int) -> Unit,
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            it != DismissValue.DismissedToEnd
        }
    )
    val dismissDirection = dismissState.dismissDirection
    val isDismissed =
        dismissState.isDismissed(DismissDirection.EndToStart)


    LaunchedEffect(isDismissed && dismissDirection == DismissDirection.EndToStart) {
        if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
            delay(100)
            onSwipeToDelete(task.id)
        }
    }

    val degrees by animateFloatAsState(
        targetValue = if (dismissState.targetValue == DismissValue.Default)
            0f
        else
            -45f,
        label = ""
    )
    SwipeToDismiss(
        state = dismissState,
        background = {
            SwipeToDeleteBackground(degrees = degrees)
        }, modifier = modifier,
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = {
            FractionalThreshold(0.5f)
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onTaskClicked(task.id)
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
}

@Composable
fun SwipeToDeleteBackground(degrees: Float) {
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
