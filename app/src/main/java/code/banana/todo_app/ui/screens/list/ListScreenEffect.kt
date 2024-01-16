package code.banana.todo_app.ui.screens.list

import code.banana.todo_app.base.AppText
import code.banana.todo_app.base.UiEffect

sealed class ListScreenEffect: UiEffect{

    class ShowToast(val text: AppText): ListScreenEffect()

}