package code.banana.todo_app.ui.screens.list

import androidx.lifecycle.viewModelScope
import code.banana.todo_app.R
import code.banana.todo_app.base.AppText
import code.banana.todo_app.base.BaseViewModel
import code.banana.todo_app.models.Priority
import code.banana.todo_app.models.Task
import code.banana.todo_app.navigation.Destination
import code.banana.todo_app.navigation.navigator.AppNavigator
import code.banana.todo_app.usecase.localcache.PersistFilterStateUseCase
import code.banana.todo_app.usecase.localcache.PersistIsSystemDarkModeUseCase
import code.banana.todo_app.usecase.localcache.ReadFilterKeyUseCase
import code.banana.todo_app.usecase.localcache.ReadIsSystemDarkThemeUseCase
import code.banana.todo_app.usecase.task.DeleteAllTasksUseCase
import code.banana.todo_app.usecase.task.DeleteTaskByIdUseCase
import code.banana.todo_app.usecase.task.GetAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val persistFilterStateUseCase: PersistFilterStateUseCase,
    private val persistIsSystemDarkModeUseCase: PersistIsSystemDarkModeUseCase,
    private val readIsSystemDarkThemeUseCase: ReadIsSystemDarkThemeUseCase,
    private val readFilterKeyUseCase: ReadFilterKeyUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val deleteTaskByIdUseCase: DeleteTaskByIdUseCase,
    private val deleteAllTasksUseCase: DeleteAllTasksUseCase,
) : BaseViewModel<ListScreenState, ListScreenEffect>() {

    private val searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    private val sort: MutableStateFlow<ListScreenState.ListSortState> =
        MutableStateFlow(ListScreenState.ListSortState.ASCENDING)

    private val combinedResultFlow: Flow<CombineResult> = combine(
        getAllTasksUseCase(),
        searchQuery,
        readFilterKeyUseCase(),
        sort,
        readIsSystemDarkThemeUseCase()
    ) { tasks, query, priority, sort, isSystemDarkTheme ->
        val filteredTasks = tasks.filter {
            it.title.contains(query, ignoreCase = true)
        }.filter {
            if (priority == Priority.NONE) true else it.priority == priority
        }.sortedBy {
            when (sort) {
                ListScreenState.ListSortState.ASCENDING -> it.timestamp
                ListScreenState.ListSortState.DESCENDING -> -it.timestamp
            }
        }

        CombineResult(
            tasks = filteredTasks,
            searchText = query,
            priorityFilter = priority,
            sort = sort,
            isSystemDarkTheme = isSystemDarkTheme
        )
    }

    init {
        viewModelScope.launch {
            combinedResultFlow.collectLatest { result ->
                setState {
                    copy(
                        tasks = result.tasks,
                        searchText = result.searchText,
                        priorityFilter = result.priorityFilter,
                        sort = result.sort,
                        isSystemDarkTheme = result.isSystemDarkTheme
                    )
                }
            }
        }
    }

    override fun createInitialState(): ListScreenState {
        return ListScreenState()
    }

    fun navigateToTaskScreen(taskId: Int) {
        viewModelScope.launch {
            appNavigator.navigateTo(
                route = Destination.TaskDetailsScreen(taskId = taskId)
            )
        }
    }

    fun onSwipeToDelete(taskId: Int) {
        viewModelScope.launch {
            deleteTaskByIdUseCase(taskId)
            setEffect(ListScreenEffect.ShowToast(AppText.StringResText(R.string.task_deleted)))
        }
    }

    fun setSearchQuery(query: String) {
        searchQuery.update { query }
    }

    fun onSortClicked() {
        sort.update { currentState ->
            when (currentState) {
                ListScreenState.ListSortState.ASCENDING -> ListScreenState.ListSortState.DESCENDING
                ListScreenState.ListSortState.DESCENDING -> ListScreenState.ListSortState.ASCENDING
            }
        }
    }

    fun onFilterItemClicked() {
        setState {
            copy(
                filterDropdownExpanded = !filterDropdownExpanded
            )
        }
    }

    fun onFilterPicked(priority: Priority) {
        viewModelScope.launch {
            persistFilterStateUseCase(priority)
            dismissFilterDropdown()
        }
    }

    fun dismissFilterDropdown() {
        setState {
            copy(
                filterDropdownExpanded = false
            )
        }
    }

    private fun clearSearchQuery() {
        setSearchQuery("")
    }

    fun showSearchField() {
        setState {
            copy(
                topBarState = ListScreenState.TopBarState.Search
            )
        }
    }

    fun closeSearch() {
        setState {
            copy(
                topBarState = ListScreenState.TopBarState.Default
            )
        }
        clearSearchQuery()
    }

    fun openDrawer() {
        setEffect(
            ListScreenEffect.OpenDrawer
        )
    }

    fun onDeleteAllClicked() {
        setState {
            copy(
                showConfirmDeleteAllDialog = true
            )
        }
    }

    fun dismissConfirmDeleteAllDialog() {
        setState {
            copy(
                showConfirmDeleteAllDialog = false
            )
        }
    }

    fun deleteAllTasksConfirmed() {
        setState {
            copy(
                showConfirmDeleteAllDialog = false
            )
        }
        viewModelScope.launch {
            deleteAllTasksUseCase()
            setEffect(ListScreenEffect.ShowToast(AppText.StringResText(R.string.all_tasks_deleted)))
        }
    }

    fun onSwitchThemeClicked() {
        viewModelScope.launch {
            persistIsSystemDarkModeUseCase(
                !currentState.isSystemDarkTheme
            )
        }
    }

    class CombineResult(
        val tasks: List<Task>,
        val searchText: String,
        val priorityFilter: Priority,
        val sort: ListScreenState.ListSortState,
        val isSystemDarkTheme: Boolean,
    )
}