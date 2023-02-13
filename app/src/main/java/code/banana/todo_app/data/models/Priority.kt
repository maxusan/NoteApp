package code.banana.todo_app.data.models

import androidx.compose.ui.graphics.Color
import code.banana.todo_app.ui.theme.HighPriorityColor
import code.banana.todo_app.ui.theme.LowPriorityColor
import code.banana.todo_app.ui.theme.MediumPriorityColor
import code.banana.todo_app.ui.theme.NonePriorityColor

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}