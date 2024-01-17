package code.banana.todo_app.ui.screens.list

import androidx.lifecycle.viewModelScope
import code.banana.todo_app.R
import code.banana.todo_app.base.AppText
import code.banana.todo_app.base.BaseViewModel
import code.banana.todo_app.models.Priority
import code.banana.todo_app.navigation.Destination
import code.banana.todo_app.navigation.navigator.AppNavigator
import code.banana.todo_app.repositories.cache.LocalCacheRepository
import code.banana.todo_app.repositories.task.TasksRepository
import code.banana.todo_app.usecase.localcache.PersistFilterStateUseCase
import code.banana.todo_app.usecase.localcache.ReadFilterKeyUseCase
import code.banana.todo_app.usecase.task.DeleteTaskByIdUseCase
import code.banana.todo_app.usecase.task.GetAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val persistFilterStateUseCase: PersistFilterStateUseCase,
    private val readFilterKeyUseCase: ReadFilterKeyUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val deleteTaskByIdUseCase: DeleteTaskByIdUseCase
) : BaseViewModel<ListScreenState, ListScreenEffect>() {

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _sort: MutableStateFlow<ListScreenState.ListSortState> =
        MutableStateFlow(ListScreenState.ListSortState.ASCENDING)
    val sort = _sort.asStateFlow()

    private val _priorityFilter: MutableStateFlow<Priority> = MutableStateFlow(Priority.NONE)
    val priorityFilter = _priorityFilter.asStateFlow()

    private val tasksFlow = combine(
        getAllTasksUseCase(),
        searchQuery,
        readFilterKeyUseCase(),
        sort
    ) { tasks, query, priority, sort ->
        val filteredTasks = tasks.filter {
            it.title.contains(query, ignoreCase = true)
        }.filter {
            if (priority == Priority.NONE) true else it.priority == priority
        }
        when (sort) {
            ListScreenState.ListSortState.ASCENDING -> filteredTasks.sortedBy { it.title.lowercase() }
            ListScreenState.ListSortState.DESCENDING -> filteredTasks.sortedByDescending { it.title.lowercase() }
        }
    }

    init {
        viewModelScope.launch {
            tasksFlow.collectLatest {
                setState {
                    copy(
                        tasks = it
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

    fun setSearchQuery(searchQuery: String) {
        _searchQuery.update { searchQuery }
    }

    fun onSortClicked() {
        _sort.update { currentState ->
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

    fun clearSearchQuery() {
        setSearchQuery("")
    }
}