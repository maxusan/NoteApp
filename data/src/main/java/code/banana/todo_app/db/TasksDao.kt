package code.banana.todo_app.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import code.banana.todo_app.db.models.TaskEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
@Dao
interface TasksDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM todo_table WHERE id =:taskId")
    suspend fun getSelectedTask(taskId: Int): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("DELETE FROM todo_table WHERE id =:taskId")
    suspend fun deleteTaskById(taskId: Int)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTasks()
}