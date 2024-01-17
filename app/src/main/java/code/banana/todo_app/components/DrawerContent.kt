package code.banana.todo_app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import code.banana.todo_app.BuildConfig
import code.banana.todo_app.R
import code.banana.todo_app.ui.theme.MEDIUM_PADDING

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    onDeleteAllClicked: () -> Unit,
    onSwitchThemeClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING),
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h2
        )
        Text(
            text = stringResource(id = R.string.version, BuildConfig.VERSION_NAME),
            style = MaterialTheme.typography.body1
        )
        Divider()
        Text(
            text = stringResource(id = R.string.delete_all_action),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.clickable { onDeleteAllClicked() }
        )
        Text(
            text = stringResource(id = R.string.switch_theme),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.clickable { onSwitchThemeClicked() }
        )
    }
}