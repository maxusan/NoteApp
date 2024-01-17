package code.banana.todo_app.usecase.task

import code.banana.todo_app.models.Task
import code.banana.todo_app.repositories.task.TasksRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
){

    suspend operator fun invoke(task: Task) {
        tasksRepository.updateTask(task)
    }
}