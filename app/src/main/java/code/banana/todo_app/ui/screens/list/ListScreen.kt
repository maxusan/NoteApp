package code.banana.todo_app.ui.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import code.banana.todo_app.R
import code.banana.todo_app.ui.screens.list.components.ListScreenTopBar
import code.banana.todo_app.ui.screens.list.components.ListContent
import code.banana.todo_app.ui.theme.fabBackgroundColor

/**
 * Created by Maksym Kovalchuk on 2/14/2023.
 */

@Composable
fun ListScreen(
    viewModel: ListScreenViewModel = hiltViewModel(),
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val searchText by viewModel.searchQuery.collectAsStateWithLifecycle()
    val priorityFilter by viewModel.priorityFilter.collectAsStateWithLifecycle()
    val sort by viewModel.sort.collectAsStateWithLifecycle()

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListScreenTopBar(
                searchText = searchText,
                onSearchTextChanged = viewModel::setSearchQuery,
                onClearSearchClicked = viewModel::clearSearchQuery,
                onSortClicked = viewModel::onSortClicked,
                onFilterItemClicked = viewModel::onFilterItemClicked,
                onFilterPicked = viewModel::onFilterPicked,
                filterDropdownExpanded = state.filterDropdownExpanded,
                dismissFilterDropdown = viewModel::dismissFilterDropdown,
                priorityFilter = priorityFilter,
                sort = sort
            )
        },
        content = {
            ListContent(
                modifier = Modifier.padding(it),
                state = state,
                onSwipeToDelete = viewModel::onSwipeToDelete,
                navigateToTaskScreen = viewModel::navigateToTaskScreen
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = { viewModel.navigateToTaskScreen(-1) })
        }
    )
}

@Composable
fun ListFab(onFabClicked: () -> Unit) {
    FloatingActionButton(
        onClick = onFabClicked,
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
@Preview
fun ListScreenPreview() {
    ListScreen()
}