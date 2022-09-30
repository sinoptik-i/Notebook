package sin.android.notebook.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import sin.android.notebook.data.NoteDao
import sin.android.notebook.data.NoteDatabase

class AllNotesVIewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao: NoteDao
        get() = NoteDatabase.getDatabase(getApplication()).noteDao()

    fun flowAllNotes() = noteDao.getAllNotes().flowOn(Dispatchers.IO)

}