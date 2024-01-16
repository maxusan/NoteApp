package code.banana.todo_app.navigation.navigator

import android.content.Intent
import kotlinx.coroutines.flow.SharedFlow

interface AppNavigator {
    val navigationFlow: SharedFlow<NavigationIntent>

    suspend fun navigateBack(
        route: String? = null,
        inclusive: Boolean = false,
    )

    suspend fun navigateTo(
        route: String,
        popUpDestination: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )

    suspend fun navigateWithClearBackstack(
        route: String,
        isSingleTop: Boolean = false,
    )
}