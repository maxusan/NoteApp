package code.banana.todo_app.ui.screens.task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import code.banana.todo_app.R
import code.banana.todo_app.base.AppText
import code.banana.todo_app.base.BaseViewModel
import code.banana.todo_app.models.Priority
import code.banana.todo_app.models.Task
import code.banana.todo_app.navigation.Destination
import code.banana.todo_app.navigation.navigator.AppNavigator
import code.banana.todo_app.repositories.task.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appNavigator: AppNavigator,
    private val tasksRepository: TasksRepository,
) : BaseViewModel<TaskScreenState, TaskScreenEffect>() {

    init {
        val taskId: Int =
            savedStateHandle.get<String>(Destination.TaskDetailsScreen.TASK_ID)?.toInt()
                ?: throw IllegalArgumentException("Task id is null")

        viewModelScope.launch {
            val task: Task? = tasksRepository.getSelectedTask(taskId)
            setState {
                copy(
                    taskId = taskId,
                    taskState = if (taskId == -1) TaskScreenState.ScreenState.AddTask else TaskScreenState.ScreenState.EditTask,
                    title = task?.title.orEmpty(),
                    description = task?.description.orEmpty(),
                    priority = task?.priority ?: Priority.LOW,
                    timestamp = task?.timestamp ?: System.currentTimeMillis()
                )
            }

        }
    }

    override fun createInitialState(): TaskScreenState {
        return TaskScreenState()
    }

    fun updateTitle(title: String) {
        setState {
            copy(
                title = title
            )
        }
    }

    fun updateDescription(description: String) {
        setState {
            copy(
                description = description
            )
        }
    }

    fun updatePriority(priority: Priority) {
        setState {
            copy(
                priority = priority
            )
        }
        dismissPriorityDropdown()
    }

    fun saveTask() {
        validateFields { canSaveTask ->
            viewModelScope.launch {
                if (canSaveTask) {
                    tasksRepository.insertTask(
                        task = currentState.run {
                            Task(
                                title = title,
                                description = description,
                                priority = priority,
                                timestamp = currentState.timestamp
                            )
                        }
                    )
                    setEffect(TaskScreenEffect.ShowToast(text = AppText.StringResText(textRes = R.string.task_created)))
                    navigateBack()
                } else {
                    setEffect(TaskScreenEffect.ShowToast(text = AppText.StringResText(textRes = R.string.fields_empty)))
                }
            }
        }
    }

    private fun validateFields(isValid: (Boolean) -> Unit) {
        val titleEmpty = currentState.title.trim().isEmpty()
        val descriptionEmpty = currentState.description.trim().isEmpty()

        val hasError = listOf(titleEmpty, descriptionEmpty).any { it }

        isValid(!hasError)
    }

    fun navigateBack() {
        viewModelScope.launch {
            appNavigator.navigateBack()
        }
    }

    fun updateTask() {
        validateFields { canSaveTask ->
            viewModelScope.launch {
                if (canSaveTask) {
                    tasksRepository.updateTask(
                        task = currentState.run {
                            Task(
                                id = taskId,
                                title = title,
                                description = description,
                                priority = priority,
                                timestamp = timestamp
                            )
                        }
                    )
                    setEffect(TaskScreenEffect.ShowToast(text = AppText.StringResText(textRes = R.string.task_updated)))
                    navigateBack()
                } else {
                    setEffect(TaskScreenEffect.ShowToast(text = AppText.StringResText(textRes = R.string.fields_empty)))

                }
            }
        }
    }

    fun deleteTask() {
        setState {
            copy(showDeleteTaskDialog = true)
        }
    }

    fun onPriorityDropdownClicked() {
        setState {
            copy(
                priorityDropdownExpanded = !priorityDropdownExpanded
            )
        }
    }

    fun dismissPriorityDropdown() {
        setState {
            copy(
                priorityDropdownExpanded = false
            )
        }
    }

    fun deleteTaskConfirmed() {
        viewModelScope.launch {
            tasksRepository.deleteTaskById(taskId = currentState.taskId)
            setEffect(
                TaskScreenEffect.ShowToast(
                    text = AppText.StringResText(textRes = R.string.task_deleted)
                )
            )
            navigateBack()
        }
    }

    fun dismissDeleteTaskDialog() {
        setState {
            copy(
                showDeleteTaskDialog = false
            )
        }
    }
}