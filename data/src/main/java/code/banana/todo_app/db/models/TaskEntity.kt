package code.banana.todo_app.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import code.banana.todo_app.Constants
import code.banana.todo_app.models.Priority

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
@Entity(tableName = Constants.DATABASE_TABLE)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
    val timestamp: Long
)
