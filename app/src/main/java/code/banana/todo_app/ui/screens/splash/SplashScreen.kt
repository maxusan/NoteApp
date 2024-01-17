package code.banana.todo_app.ui.screens.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import code.banana.todo_app.R
import code.banana.todo_app.ui.theme.LOGO_HEIGHT
import code.banana.todo_app.ui.theme.splashScreenBackground
import kotlinx.coroutines.delay

/**
 * Created by Maksym Kovalchuk on 2/16/2023.
 */
@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel = hiltViewModel(),
) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 100.dp,
        animationSpec = tween(durationMillis = 1000), label = ""
    )
    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(1000), label = ""
    )

    LaunchedEffect(true) {
        startAnimation = true
        delay(300)
        viewModel.navigateToList()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.splashScreenBackground),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = getLogo()),
            contentDescription = null,
            modifier = Modifier
                .size(
                    LOGO_HEIGHT
                )
                .offset(y = offsetState)
                .alpha(alpha = alphaState)
        )
    }
}

@Composable
fun getLogo(): Int {
    return if (isSystemInDarkTheme()) R.drawable.logo_dark else R.drawable.logo_light
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}