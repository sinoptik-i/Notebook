package sin.android.notebook.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import sin.android.notebook.data.Note
import sin.android.notebook.data.NoteDao
import sin.android.notebook.data.NoteDatabase
import sin.android.notebook.data.NoteRepository

class AllNotesVIewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao: NoteDao
        get() = NoteDatabase.getDatabase(getApplication()).noteDao()

    private val noteRepository = NoteRepository(noteDao, viewModelScope)

    fun flowAllNotes() = noteDao.getAllNotes().flowOn(Dispatchers.IO)

    fun deleteNote(note: Note) = noteRepository.deleteNote(note)

}