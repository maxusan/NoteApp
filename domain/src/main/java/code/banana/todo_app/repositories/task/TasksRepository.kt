package code.banana.todo_app.repositories.task

import code.banana.todo_app.models.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun getAllTasks(): Flow<List<Task>>

    suspend fun getTaskById(taskId: Int): Task?
    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTaskById(taskId: Int)

    suspend fun deleteAllTasks()
}