package code.banana.todo_app.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import code.banana.todo_app.models.Priority

fun Modifier.drawPriorityBadge(priority: Priority) = drawWithContent {
    drawContent()
    if (priority == Priority.NONE) return@drawWithContent
    val circleRadius = size.width / 8
    drawCircle(
        color = priority.colorByPriority(),
        radius = circleRadius,
        center = Offset(size.width - circleRadius / 2, circleRadius / 2)
    )
}