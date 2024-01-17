package code.banana.todo_app.ui.screens.list

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import code.banana.todo_app.R
import code.banana.todo_app.components.AppAlertDialog
import code.banana.todo_app.components.DrawerContent
import code.banana.todo_app.ui.screens.list.components.ListContent
import code.banana.todo_app.ui.screens.list.components.ListScreenTopBar
import code.banana.todo_app.ui.theme.fabBackgroundColor
import code.banana.todo_app.util.getText
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

    val listState = rememberLazyListState()

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    LaunchedEffect("ObserveEffects") {
        viewModel.effect.collectLatest {
            when (it) {
                is ListScreenEffect.ShowToast -> {
                    Toast.makeText(context, it.text.getText(context), Toast.LENGTH_SHORT).show()
                }

                ListScreenEffect.OpenDrawer -> {
                    scope.launch {
                        drawerState.open()
                    }
                }
            }
        }
    }

    ModalDrawer(
        drawerContent = {
            DrawerContent(
                modifier = Modifier.fillMaxSize(),
                onDeleteAllClicked = viewModel::onDeleteAllClicked,
                onSwitchThemeClicked = viewModel::onSwitchThemeClicked
            )
        },
        drawerState = drawerState,
        drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
        drawerElevation = 16.dp
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ListScreenTopBar(
                    searchText = searchText,
                    onSearchIconClicked = viewModel::showSearchField,
                    onCloseSearchClicked = viewModel::closeSearch,
                    onBurgerClicked = viewModel::openDrawer,
                    onSearchTextChanged = viewModel::setSearchQuery,
                    onSortClicked = viewModel::onSortClicked,
                    onFilterItemClicked = viewModel::onFilterItemClicked,
                    onFilterPicked = viewModel::onFilterPicked,
                    filterDropdownExpanded = state.filterDropdownExpanded,
                    dismissFilterDropdown = viewModel::dismissFilterDropdown,
                    priorityFilter = priorityFilter,
                    sort = sort,
                    topBarState = state.topBarState,
                )
            },
            content = {
                ListContent(
                    modifier = Modifier.padding(it),
                    state = state,
                    listState = listState,
                    onSwipeToDelete = viewModel::onSwipeToDelete,
                    navigateToTaskScreen = viewModel::navigateToTaskScreen
                )
            },
            floatingActionButton = {
                ListFab(onFabClicked = { viewModel.navigateToTaskScreen(-1) })
            }
        )
    }
    AppAlertDialog(
        title = stringResource(id = R.string.delete_all_tasks),
        message = stringResource(id = R.string.are_you_sure),
        openDialog = state.showConfirmDeleteAllDialog,
        closeDialog = viewModel::dismissConfirmDeleteAllDialog,
        onYesClicked = viewModel::deleteAllTasksConfirmed
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