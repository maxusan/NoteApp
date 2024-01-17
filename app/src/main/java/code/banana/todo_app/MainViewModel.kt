package code.banana.todo_app

import androidx.lifecycle.ViewModel
import code.banana.todo_app.navigation.navigator.AppNavigator
import code.banana.todo_app.usecase.localcache.ReadIsSystemDarkThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    appNavigator: AppNavigator,
    val readIsSystemDarkThemeUseCase: ReadIsSystemDarkThemeUseCase
) : ViewModel() {

    val navigationFlow = appNavigator.navigationFlow
    val isSystemDarkTheme = readIsSystemDarkThemeUseCase()
}