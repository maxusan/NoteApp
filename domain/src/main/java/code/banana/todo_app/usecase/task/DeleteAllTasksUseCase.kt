package code.banana.todo_app.usecase.task

import code.banana.todo_app.repositories.task.TasksRepository
import javax.inject.Inject

class DeleteAllTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
){

    suspend operator fun invoke() {
        tasksRepository.deleteAllTasks()
    }
}