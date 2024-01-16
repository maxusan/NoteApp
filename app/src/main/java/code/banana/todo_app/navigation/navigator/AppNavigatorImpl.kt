package code.banana.todo_app.navigation.navigator

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor() : AppNavigator {

    private val _navigationFlow = MutableSharedFlow<NavigationIntent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val navigationFlow: SharedFlow<NavigationIntent> = _navigationFlow.asSharedFlow()

    override suspend fun navigateBack(
        route: String?,
        inclusive: Boolean,
    ) {
        _navigationFlow.emit(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = inclusive
            )
        )
    }

    override suspend fun navigateTo(
        route: String,
        popUpDestination: String?,
        inclusive: Boolean,
        isSingleTop: Boolean,
    ) {
        _navigationFlow.emit(
            NavigationIntent.NavigateTo(
                route = route,
                popUpToRoute = popUpDestination,
                inclusive = inclusive,
                isSingleTop = isSingleTop,
            )
        )
    }


    override suspend fun navigateWithClearBackstack(
        route: String,
        isSingleTop: Boolean,
    ) {
        _navigationFlow.emit(
            NavigationIntent.NavigateToWithClearBackStack(
                route = route,
                isSingleTop = isSingleTop
            )
        )
    }
}