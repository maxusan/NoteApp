package code.banana.todo_app.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import code.banana.todo_app.data.models.Priority
import code.banana.todo_app.util.Constants.PREFERENCES_NAME
import code.banana.todo_app.util.Constants.PREFERENCE_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Maksym Kovalchuk on 2/15/2023.
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys {
        val sortKey = stringPreferencesKey(name = PREFERENCE_KEY)
    }

    private val dataStore = context.dataStore

    suspend fun persistSortState(priority: Priority) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.sortKey] = priority.name
        }
    }

    val readSortState: Flow<String> = dataStore.data.catch {
        if (it is IOException) emit(
            emptyPreferences()
        ) else throw it
    }.map { preferences ->
        val sortState = preferences[PreferencesKeys.sortKey] ?: Priority.NONE.name
        sortState
    }
}