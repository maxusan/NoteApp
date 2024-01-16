package code.banana.todo_app.ui.screens.task

import code.banana.todo_app.base.AppText
import code.banana.todo_app.base.UiEffect

sealed class TaskScreenEffect: UiEffect{

    class ShowToast(val text: AppText): TaskScreenEffect()
}