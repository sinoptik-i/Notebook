package sin.android.notebook.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import sin.android.notebook.data.NoteDao
import sin.android.notebook.data.NoteDatabase
import javax.inject.Singleton

@Module
class DataModule {
    //proverit'
    @Provides
    @Singleton
    fun database(context: Context): NoteDatabase = NoteDatabase.getDatabase(context)

    @Provides
    fun notesDao(db: NoteDatabase): NoteDao = db.noteDao()


    @Provides
    @Singleton
    fun dataScope(): CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
}