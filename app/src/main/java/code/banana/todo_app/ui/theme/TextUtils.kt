package code.banana.todo_app.ui.theme

import android.content.Context
import code.banana.todo_app.base.AppText

fun AppText.getText(context: Context): String {
    return try {
        when (this) {
            is AppText.Text -> this.text
            is AppText.StringResText -> context.getString(this.textRes)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}