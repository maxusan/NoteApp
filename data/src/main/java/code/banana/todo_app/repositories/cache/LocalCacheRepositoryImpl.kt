package code.banana.todo_app.repositories.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import code.banana.todo_app.Constants.FILTER_KEY
import code.banana.todo_app.Constants.IS_SYSTEM_DAK_THEME_KEY
import code.banana.todo_app.Constants.PREFERENCES_NAME
import code.banana.todo_app.models.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

internal class LocalCacheRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : LocalCacheRepository {

    private object PreferencesKeys {
        val filterKey = stringPreferencesKey(name = FILTER_KEY)
        val isSystemDarkThemeKey = booleanPreferencesKey(name = IS_SYSTEM_DAK_THEME_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun persistFilterState(priority: Priority) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.filterKey] = priority.name
        }
    }

    override fun readFilterKey(): Flow<Priority> =
        getPreference(PreferencesKeys.filterKey, Priority.NONE.name)
            .map { Priority.fromName(it) }

    override suspend fun persistIsSystemDarkTheme(isSystemDarkTheme: Boolean) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.isSystemDarkThemeKey] = isSystemDarkTheme
        }
    }

    override fun readIsSystemDarkTheme(): Flow<Boolean> =
        getPreference(PreferencesKeys.isSystemDarkThemeKey, true)

    private fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val result = preferences[key] ?: defaultValue
            result
        }
}