package code.banana.todo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import code.banana.todo_app.navigation.Navigation
import code.banana.todo_app.ui.theme.ToDoAppTheme
import code.banana.todo_app.ui.viewmodels.SharedViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                val navController = rememberAnimatedNavController()
                val viewModel = hiltViewModel<SharedViewModel>()
                Navigation(navController = navController, viewModel = viewModel)
            }
        }
    }
}