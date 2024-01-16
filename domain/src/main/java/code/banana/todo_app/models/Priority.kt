package code.banana.todo_app.models


/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
enum class Priority {
    HIGH,
    MEDIUM,
    LOW,
    NONE;

    companion object {
        fun fromName(name: String): Priority {
            return when (name) {
                HIGH.name -> HIGH
                MEDIUM.name -> MEDIUM
                LOW.name -> LOW
                else -> NONE
            }
        }
    }
}