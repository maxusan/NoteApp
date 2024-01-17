package code.banana.todo_app.usecase.task

import code.banana.todo_app.models.Task
import code.banana.todo_app.repositories.task.TasksRepository
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
) {

    suspend operator fun invoke(taskId: Int): Task? {
        return tasksRepository.getTaskById(taskId)
    }
}