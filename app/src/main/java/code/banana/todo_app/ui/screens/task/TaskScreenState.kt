package code.banana.todo_app.ui.screens.task

import code.banana.todo_app.base.UiState
import code.banana.todo_app.models.Priority

data class TaskScreenState(
    val taskId: Int = -1,
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW,
    val taskState: ScreenState = ScreenState.Default
) : UiState {

    enum class ScreenState {
        Default,
        AddTask,
        EditTask
    }
}