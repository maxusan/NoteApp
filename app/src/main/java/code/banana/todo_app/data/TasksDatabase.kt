package code.banana.todo_app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import code.banana.todo_app.data.models.Task

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TasksDatabase: RoomDatabase() {

    abstract fun taskDao(): TasksDao
}