package code.banana.todo_app.ui.screens.list

import code.banana.todo_app.base.UiState
import code.banana.todo_app.models.Task

data class ListScreenState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val filterDropdownExpanded: Boolean = false,
    val showConfirmDeleteAllDialog: Boolean = false,
    val listSortState: ListSortState = ListSortState.ASCENDING,
    val topBarState: TopBarState = TopBarState.Default,
) : UiState {

    enum class ListSortState {
        ASCENDING,
        DESCENDING
    }

    enum class TopBarState{
        Default,
        Search
    }
}