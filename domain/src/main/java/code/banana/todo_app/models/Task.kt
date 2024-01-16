package code.banana.todo_app.models

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
    val timestamp: Long = System.currentTimeMillis(),

    val formattedDate: String = ""
)