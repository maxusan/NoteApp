package code.banana.todo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import code.banana.todo_app.navigation.AppNavHost
import code.banana.todo_app.navigation.Destination
import code.banana.todo_app.navigation.navigator.NavigationIntent
import code.banana.todo_app.ui.theme.ToDoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val lifecycleOwner = LocalLifecycleOwner.current
            val isSystemDarkTheme by viewModel.isSystemDarkTheme.collectAsStateWithLifecycle(
                initialValue = true,
                lifecycle = lifecycleOwner.lifecycle,
            )
            ToDoAppTheme(darkTheme = isSystemDarkTheme) {
                val navController = rememberNavController()
                NavigationEffects(
                    navigationFlow = viewModel.navigationFlow,
                    navHostController = navController
                )

                AppNavHost(
                    navController = navController,
                    startDestination = Destination.Splash
                )
            }
        }
    }

    @Composable
    fun NavigationEffects(
        navigationFlow: SharedFlow<NavigationIntent>,
        navHostController: NavHostController,
    ) {
        LaunchedEffect(this, navHostController, navigationFlow) {
            navigationFlow
                .collect { intent ->
                    if (isFinishing) {
                        return@collect
                    }
                    when (intent) {
                        is NavigationIntent.NavigateBack -> {
                            if (intent.route != null) {
                                navHostController.popBackStack(intent.route, intent.inclusive)
                            } else {
                                navHostController.popBackStack()
                            }
                        }

                        is NavigationIntent.NavigateTo -> {
                            navHostController.navigate(intent.route) {
                                launchSingleTop = intent.isSingleTop
                                intent.popUpToRoute?.let { popUpToRoute ->
                                    popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                                }
                            }
                        }

                        is NavigationIntent.NavigateToWithClearBackStack -> {
                            navHostController.navigate(intent.route) {
                                launchSingleTop = intent.isSingleTop
                                popUpTo(navHostController.graph.id)
                            }
                        }
                    }
                }
        }
    }
}