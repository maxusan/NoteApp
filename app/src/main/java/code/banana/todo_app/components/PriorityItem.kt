package code.banana.todo_app.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import code.banana.todo_app.models.Priority
import code.banana.todo_app.ui.theme.MEDIUM_PADDING
import code.banana.todo_app.ui.theme.PRIORITY_INDICATOR_SIZE
import code.banana.todo_app.util.colorByPriority

/**
 * Created by Maksym Kovalchuk on 2/14/2023.
 */
@Composable
fun PriorityItem(priority: Priority, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Box(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .background(priority.colorByPriority(), shape = CircleShape)
        )
        Text(
            modifier = Modifier.padding(start = MEDIUM_PADDING),
            text = priority.name,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Preview
@Composable
fun PriorityItemPreview() {
    PriorityItem(priority = code.banana.todo_app.models.Priority.HIGH)
}