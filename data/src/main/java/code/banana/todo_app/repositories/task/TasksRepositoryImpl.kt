package code.banana.todo_app.repositories.task

import code.banana.todo_app.db.TasksDao
import code.banana.todo_app.db.mappers.toData
import code.banana.todo_app.db.mappers.toDomain
import code.banana.todo_app.models.Task
import code.banana.todo_app.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
class TasksRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao,
    private val dateUtils: DateUtils,
) : TasksRepository {

    override fun getAllTasks(): Flow<List<Task>> {
        return tasksDao.getAllTasks().map { tasks ->
            tasks.map {
                it.toDomain().copy(
                    formattedDate = dateUtils.formatTaskDateByTimestamp(it.timestamp)
                )
            }
        }
    }

    override suspend fun getSelectedTask(taskId: Int): Task? {
        return tasksDao.getSelectedTask(taskId = taskId)?.toDomain()
    }

    override suspend fun insertTask(task: Task) {
        tasksDao.addTask(task = task.toData())
    }

    override suspend fun updateTask(task: Task) {
        tasksDao.updateTask(task = task.toData())
    }

    override suspend fun deleteTask(task: Task) {
        tasksDao.deleteTask(task = task.toData())
    }

    override suspend fun deleteTaskById(taskId: Int) {
        tasksDao.deleteTaskById(taskId = taskId)
    }

    override suspend fun deleteAllTasks() {
        tasksDao.deleteAllTasks()
    }
}