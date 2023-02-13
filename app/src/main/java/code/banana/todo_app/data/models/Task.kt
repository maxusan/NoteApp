package code.banana.todo_app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import code.banana.todo_app.util.Constants

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
@Entity(tableName = Constants.DATABASE_TABLE)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)
