package code.banana.todo_app.repositories.cache

import code.banana.todo_app.models.Priority
import kotlinx.coroutines.flow.Flow

interface LocalCacheRepository {

    suspend fun persistFilterState(priority: Priority)

    fun readFilterKey(): Flow<Priority>

    suspend fun persistIsSystemDarkTheme(isSystemDarkTheme: Boolean)

    fun readIsSystemDarkTheme(): Flow<Boolean>
}