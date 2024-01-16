package code.banana.todo_app.util

import androidx.compose.ui.graphics.Color
import code.banana.todo_app.models.Priority

fun Priority.colorByPriority(): Color{
    return when(this){
        Priority.HIGH -> Color.Red
        Priority.MEDIUM -> Color.Yellow
        Priority.LOW -> Color.Green
        Priority.NONE -> Color.White
    }
}