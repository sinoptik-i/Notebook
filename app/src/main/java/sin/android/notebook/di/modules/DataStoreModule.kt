package sin.android.notebook.di.modules

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataStoreModule {

    @Provides
    @Singleton
    fun store(context: Context): DataStore<Preferences> =
        context.createDataStore("notebook_settings")
}