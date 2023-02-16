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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import code.banana.todo_app.R
import code.banana.todo_app.ui.theme.LOGO_HEIGHT
import code.banana.todo_app.ui.theme.splashScreenBackground
import code.banana.todo_app.util.Constants.SPLASH_SCREEN_DELAY
import kotlinx.coroutines.delay

/**
 * Created by Maksym Kovalchuk on 2/16/2023.
 */
@Composable
fun SplashScreen(navigateToListScreen: () -> Unit) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 100.dp,
        animationSpec = tween(durationMillis = 1000)
    )
    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(1000)
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(SPLASH_SCREEN_DELAY)
        navigateToListScreen()
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
    SplashScreen(navigateToListScreen = {})
}