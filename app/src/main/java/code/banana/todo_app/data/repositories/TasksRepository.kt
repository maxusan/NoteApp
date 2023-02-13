package code.banana.todo_app.data.repositories

import code.banana.todo_app.data.TasksDao
import code.banana.todo_app.data.models.Task
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
@ViewModelScoped
class TasksRepository @Inject constructor(private val tasksDao: TasksDao) {

    val getAllTasks: Flow<List<Task>> = tasksDao.getAllTasks()
    val sortByLowPriority: Flow<List<Task>> = tasksDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<Task>> = tasksDao.sortByHighPriority()

    fun getSelectedTask(taskId: Int): Flow<Task> {
        return tasksDao.getSelectedTask(taskId = taskId)
    }

    suspend fun insertTask(task: Task) {
        tasksDao.addTAsk(task = task)
    }

    suspend fun updateTask(task: Task) {
        tasksDao.updateTask(task = task)
    }

    suspend fun deleteTask(task: Task) {
        tasksDao.deleteTask(task = task)
    }

    suspend fun deleteAllTasks() {
        tasksDao.deleteAllTasks()
    }

    fun searchDatabase(searchQuery: String): Flow<List<Task>> {
        return tasksDao.searchDatabase(searchQuery = searchQuery)
    }
}