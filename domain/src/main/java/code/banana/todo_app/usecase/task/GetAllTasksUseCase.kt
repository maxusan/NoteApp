package code.banana.todo_app.usecase.task

import code.banana.todo_app.models.Task
import code.banana.todo_app.repositories.task.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
) {

    operator fun invoke(): Flow<List<Task>> {
        return tasksRepository.getAllTasks()
    }
}