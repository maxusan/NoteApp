package code.banana.todo_app.navigation.navigator

import android.content.Intent


sealed class NavigationIntent {
    data class NavigateBack(
        val route: String? = null,
        val inclusive: Boolean = false,
    ) : NavigationIntent()

    data class NavigateTo(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
    ) : NavigationIntent()

    data class NavigateToWithClearBackStack(
        val route: String,
        val isSingleTop: Boolean = false,
    ) : NavigationIntent()
}