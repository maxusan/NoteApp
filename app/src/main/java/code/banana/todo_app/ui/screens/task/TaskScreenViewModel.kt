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
                    priority = task?.priority ?: Priority.LOW
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
    }

    fun saveTask() {
        viewModelScope.launch {
            tasksRepository.insertTask(
                task = currentState.run {
                    Task(
                        title = title,
                        description = description,
                        priority = priority
                    )
                }
            )
            setEffect(TaskScreenEffect.ShowToast(text = AppText.StringResText(textRes = R.string.task_created)))
            navigateBack()
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            appNavigator.navigateBack()
        }
    }

    fun updateTask() {
        viewModelScope.launch {
            tasksRepository.updateTask(
                task = currentState.run {
                    Task(
                        id = taskId,
                        title = title,
                        description = description,
                        priority = priority
                    )
                }
            )
            setEffect(TaskScreenEffect.ShowToast(text = AppText.StringResText(textRes = R.string.task_updated)))
            navigateBack()
        }
    }

    fun deleteTask() {

    }
}