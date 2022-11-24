package sin.android.notebook

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey
import androidx.datastore.preferences.toMutablePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsMaster @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val DARK_THEME = preferencesKey<Boolean>("dark_Theme")

    suspend fun saveTheme(isDarkTheme: Boolean) {
        dataStore.updateData {
            it.toMutablePreferences().apply {
                set(DARK_THEME, isDarkTheme)
            }
        }
    }

    fun isDarkThemeFlow() = dataStore.data.map { it.get(DARK_THEME) ?: false }

    suspend fun getTheme(getIsDarkTheme: (Boolean) -> Unit) {
        coroutineScope {
            launch {
                dataStore.data.collect {
                    val dark_Theme: Boolean? = it[DARK_THEME]
                    getIsDarkTheme(dark_Theme ?: false)
                }
            }
        }
    }

    suspend fun getTheme2() = dataStore.data.collect { it[DARK_THEME] }

}