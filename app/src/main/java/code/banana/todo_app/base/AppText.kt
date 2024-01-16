package code.banana.todo_app.base

import androidx.annotation.StringRes

sealed class AppText {
    class Text(val text: String) : AppText()
    class StringResText(@StringRes val textRes: Int) : AppText()
}