package code.banana.todo_app.navigation


sealed class Destination(protected val route: String, vararg params: String) {

    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }


    object Splash : NoArgumentsDestination("splash")

    object TasksListScreen : NoArgumentsDestination("tasks_list")
    object TaskDetailsScreen : Destination("tasks_list", "task_id") {
        const val TASK_ID = "task_id"
        operator fun invoke(taskId: Int) = route.appendParams(
            TASK_ID to taskId
        )
    }

    internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
        val builder = StringBuilder(this)

        params.forEach {
            it.second?.toString()?.let { arg ->
                builder.append("/$arg")
            }
        }

        return builder.toString()
    }
}
