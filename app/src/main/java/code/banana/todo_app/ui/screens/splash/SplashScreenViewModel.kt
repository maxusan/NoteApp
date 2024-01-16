package code.banana.todo_app.ui.screens.splash

import androidx.lifecycle.viewModelScope
import code.banana.todo_app.base.BaseViewModel
import code.banana.todo_app.navigation.Destination
import code.banana.todo_app.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
) : BaseViewModel<SplashScreenState, SplashScreenEffect>() {
    override fun createInitialState(): SplashScreenState {
        return SplashScreenState()
    }

    fun navigateToList(){
        viewModelScope.launch {
            appNavigator.navigateTo(Destination.TasksListScreen.fullRoute)
        }
    }
}