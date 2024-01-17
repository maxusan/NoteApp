package code.banana.todo_app.ui.screens.list

import code.banana.todo_app.base.UiState
import code.banana.todo_app.models.Priority
import code.banana.todo_app.models.Task

data class ListScreenState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val searchText: String = "",
    val priorityFilter: Priority = Priority.NONE,
    val sort: ListSortState = ListSortState.ASCENDING,
    val filterDropdownExpanded: Boolean = false,
    val showConfirmDeleteAllDialog: Boolean = false,
    val topBarState: TopBarState = TopBarState.Default,
    val isSystemDarkTheme: Boolean = false,
) : UiState {

    enum class ListSortState {
        ASCENDING,
        DESCENDING
    }

    enum class TopBarState {
        Default,
        Search
    }
}