package code.banana.todo_app.ui.screens.list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import code.banana.todo_app.R
import code.banana.todo_app.components.PriorityItem
import code.banana.todo_app.models.Priority
import code.banana.todo_app.ui.screens.list.ListScreenState

/**
 * Created by Maksym Kovalchuk on 2/14/2023.
 */

@Composable
fun ListScreenTopBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onClearSearchClicked: () -> Unit,
    onSortClicked: () -> Unit,
    onFilterItemClicked: () -> Unit,
    onFilterPicked: (Priority) -> Unit,
    filterDropdownExpanded: Boolean,
    dismissFilterDropdown: () -> Unit,
    priorityFilter: Priority,
    sort: ListScreenState.ListSortState,
) {
    val sortIconScaleY by animateFloatAsState(
        targetValue = if (sort == ListScreenState.ListSortState.ASCENDING) 1f else -1f,
        label = ""
    )
    val trailingIconVisible by remember(searchText) {
        derivedStateOf { searchText.isNotEmpty() }
    }
    TopAppBar(
        title = {
            TextField(
                value = searchText,
                onValueChange = onSearchTextChanged,
                placeholder = { Text(text = stringResource(id = R.string.search_tasks)) },
                trailingIcon = {
                    if (trailingIconVisible)
                        IconButton(onClick = onClearSearchClicked) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = stringResource(id = R.string.clear_searchfield)
                            )
                        }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        actions = {
            IconButton(onClick = onSortClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = stringResource(id = R.string.sort_tasks),
                    modifier = Modifier.scale(1f, sortIconScaleY)
                )
            }
            IconButton(onClick = onFilterItemClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter_list),
                    contentDescription = stringResource(id = R.string.filter_tasks)
                )
                DropdownMenu(
                    expanded = filterDropdownExpanded,
                    onDismissRequest = dismissFilterDropdown
                ) {
                    DropdownMenuItem(onClick = {
                        onFilterPicked(Priority.LOW)
                    }) {
                        PriorityItem(priority = Priority.LOW)
                    }
                    DropdownMenuItem(onClick = {
                        onFilterPicked(Priority.MEDIUM)
                    }) {
                        PriorityItem(priority = Priority.MEDIUM)
                    }
                    DropdownMenuItem(onClick = {
                        onFilterPicked(Priority.HIGH)
                    }) {
                        PriorityItem(priority = Priority.HIGH)
                    }
                    DropdownMenuItem(onClick = {
                        onFilterPicked(Priority.NONE)
                    }) {
                        PriorityItem(priority = Priority.NONE)
                    }
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}
