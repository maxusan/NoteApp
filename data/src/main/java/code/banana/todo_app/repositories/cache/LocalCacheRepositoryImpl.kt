package code.banana.todo_app.repositories.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import code.banana.todo_app.Constants.FILTER_KEY
import code.banana.todo_app.Constants.PREFERENCES_NAME
import code.banana.todo_app.models.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class LocalCacheRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : LocalCacheRepository {

    private object PreferencesKeys {
        val filterKey = stringPreferencesKey(name = FILTER_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun persistFilterState(priority: Priority) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.filterKey] = priority.name
        }
    }

    override fun readFilterKey() = dataStore.data.catch {
        if (it is IOException) emit(
            emptyPreferences()
        ) else throw it
    }.map { preferences ->
        val sortState = preferences[PreferencesKeys.filterKey] ?: Priority.NONE.name
        Priority.fromName(sortState)
    }
}