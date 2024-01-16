package code.banana.todo_app

import androidx.lifecycle.ViewModel
import code.banana.todo_app.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by Maksym Kovalchuk on 2/13/2023.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {

    val navigationFlow = appNavigator.navigationFlow
}