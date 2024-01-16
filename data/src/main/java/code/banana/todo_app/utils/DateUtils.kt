package code.banana.todo_app.utils

import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class DateUtils @Inject constructor() {

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())

    fun formatTaskDateByTimestamp(timestamp: Long): String {
        return dateFormat.format(timestamp)
    }
}