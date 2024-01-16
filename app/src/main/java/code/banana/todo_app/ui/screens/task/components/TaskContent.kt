package code.banana.todo_app.ui.screens.task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import code.banana.todo_app.R
import code.banana.todo_app.components.PriorityDropdown
import code.banana.todo_app.models.Priority
import code.banana.todo_app.ui.theme.MEDIUM_PADDING

/**
 * Created by Maksym Kovalchuk on 2/15/2023.
 */
@Composable
fun TaskContent(
    modifier: Modifier = Modifier,
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priorityDropdownExpanded: Boolean = false,
    onPriorityDropdownClicked: () -> Unit,
    dismissPriorityDropdown: () -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(all = MEDIUM_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = onTitleChange,
            label = { Text(text = stringResource(R.string.title)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,

            )
        PriorityDropdown(
            modifier = Modifier.padding(top = MEDIUM_PADDING),
            priority = priority,
            onPrioritySelected = onPrioritySelected,
            priorityDropdownExpanded = priorityDropdownExpanded,
            onPriorityDropdownClicked = onPriorityDropdownClicked,
            dismissPriorityDropdown = dismissPriorityDropdown,

            )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text(text = stringResource(R.string.description)) },
            textStyle = MaterialTheme.typography.body1,
        )
    }
}

@Preview
@Composable
fun TaskContentPreview() {
    TaskContent(
        title = "",
        onTitleChange = {},
        description = "",
        onDescriptionChange = {},
        priority = Priority.LOW,
        onPrioritySelected = {},
        modifier = Modifier,
        priorityDropdownExpanded = false,
        onPriorityDropdownClicked = {},
        dismissPriorityDropdown = {},

        )
}