package code.banana.todo_app.ui.screens.task

import code.banana.todo_app.base.UiState
import code.banana.todo_app.models.Priority

data class TaskScreenState(
    val taskId: Int = -1,
    val title: String = "",
    val description: String = "",
    val priorityDropdownExpanded: Boolean = false,
    val showDeleteTaskDialog: Boolean = false,
    val priority: Priority = Priority.LOW,
    val timestamp: Long = 0L,
    val taskState: ScreenState = ScreenState.Default
) : UiState {


    enum class ScreenState {
        Default,
        AddTask,
        EditTask
    }
}