package code.banana.todo_app.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import code.banana.todo_app.R
import code.banana.todo_app.models.Priority
import code.banana.todo_app.ui.theme.MEDIUM_PADDING
import code.banana.todo_app.ui.theme.PRIORITY_DROPDOWN_HEIGHT
import code.banana.todo_app.ui.theme.PRIORITY_INDICATOR_SIZE
import code.banana.todo_app.util.colorByPriority

/**
 * Created by Maksym Kovalchuk on 2/15/2023.
 */
@Composable
fun PriorityDropdown(
    modifier: Modifier = Modifier,
    priority: Priority,
    priorityDropdownExpanded: Boolean = false,
    onPriorityDropdownClicked: () -> Unit,
    dismissPriorityDropdown: () -> Unit,
    onPrioritySelected: (Priority) -> Unit,
) {
    val angle: Float by animateFloatAsState(
        targetValue = if (priorityDropdownExpanded) 180f else 0f,
        label = ""
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(PRIORITY_DROPDOWN_HEIGHT)
            .clickable { onPriorityDropdownClicked() }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                shape = MaterialTheme.shapes.small
            )
            .background(MaterialTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Canvas(modifier = Modifier
            .size(PRIORITY_INDICATOR_SIZE)
            .padding(start = MEDIUM_PADDING), onDraw = {
            drawCircle(color = priority.colorByPriority())
        })
        Text(
            text = priority.name,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .weight(1f)
                .padding(start = MEDIUM_PADDING)
        )
        IconButton(
            onClick = onPriorityDropdownClicked,
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(angle)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(R.string.dropdown_arrow)
            )
        }
        DropdownMenu(
            expanded = priorityDropdownExpanded,
            onDismissRequest = dismissPriorityDropdown,
            modifier = Modifier.fillMaxWidth(fraction = 0.96f)
        ) {
            DropdownMenuItem(onClick = {
                onPrioritySelected(Priority.LOW)
            }) {
                PriorityItem(priority = Priority.LOW)
            }
            DropdownMenuItem(onClick = {
                onPrioritySelected(Priority.MEDIUM)
            }) {
                PriorityItem(priority = Priority.MEDIUM)
            }
            DropdownMenuItem(onClick = {
                onPrioritySelected(Priority.HIGH)
            }) {
                PriorityItem(priority = Priority.HIGH)
            }
        }
    }
}

@Preview
@Composable
fun PriorityDropdownPreview() {
    PriorityDropdown(
        priority = Priority.MEDIUM, onPrioritySelected = {},
        modifier = Modifier,
        priorityDropdownExpanded = false,
        onPriorityDropdownClicked = {},
        dismissPriorityDropdown = {},
    )
}