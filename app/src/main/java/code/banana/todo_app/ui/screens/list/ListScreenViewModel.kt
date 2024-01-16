package code.banana.todo_app.ui.screens.list

import androidx.lifecycle.viewModelScope
import code.banana.todo_app.base.BaseViewModel
import code.banana.todo_app.models.Priority
import code.banana.todo_app.models.Task
import code.banana.todo_app.navigation.Destination
import code.banana.todo_app.navigation.navigator.AppNavigator
import code.banana.todo_app.repositories.cache.LocalCacheRepository
import code.banana.todo_app.repositories.task.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    tasksRepository: TasksRepository,
    private val localCacheRepository: LocalCacheRepository,
) : BaseViewModel<ListScreenState, ListScreenEffect>() {

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _sort: MutableStateFlow<ListScreenState.ListSortState> =
        MutableStateFlow(ListScreenState.ListSortState.ASCENDING)
    val sort = _sort.asStateFlow()

    private val _priorityFilter: MutableStateFlow<Priority> = MutableStateFlow(Priority.NONE)
    val priorityFilter = _priorityFilter.asStateFlow()

    private val tasksFlow = combine(
        tasksRepository.getAllTasks(),
        searchQuery,
        localCacheRepository.readFilterKey(),
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

    fun onSwipeToDelete(task: Task) {

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
            localCacheRepository.persistFilterState(priority)
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