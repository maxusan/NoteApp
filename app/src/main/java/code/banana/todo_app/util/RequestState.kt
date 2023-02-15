package code.banana.todo_app.util

/**
 * Created by Maksym Kovalchuk on 2/15/2023.
 */
sealed class RequestState<out T>{
    object Idle: RequestState<Nothing>()
    object Loading: RequestState<Nothing>()
    data class Success<T>(val data: T): RequestState<T>()
    data class Error(val error: Throwable): RequestState<Nothing>()
}
