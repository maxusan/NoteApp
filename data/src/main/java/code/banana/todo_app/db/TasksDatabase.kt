package code.banana.todo_app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import code.banana.todo_app.db.models.TaskEntity

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun taskDao(): TasksDao
}