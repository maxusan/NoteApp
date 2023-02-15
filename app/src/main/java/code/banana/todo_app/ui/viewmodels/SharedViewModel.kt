package code.banana.todo_app.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import code.banana.todo_app.data.models.Task
import code.banana.todo_app.data.repositories.TasksRepository
import code.banana.todo_app.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
@HiltViewModel
class SharedViewModel @Inject constructor(private val tasksRepository: TasksRepository) :
    ViewModel() {

     val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
     val searchTextState: MutableState<String> = mutableStateOf("")

    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> = _allTasks

    fun getAllTasks() {
        viewModelScope.launch {
            tasksRepository.getAllTasks.collect {
                _allTasks.value = it
            }
        }
    }
}