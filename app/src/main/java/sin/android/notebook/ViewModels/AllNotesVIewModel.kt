package sin.android.notebook.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import sin.android.notebook.SupportSelectedMode
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

    fun deleteSelectedNotes(notes: List<Note>) {
        for (note in notes) {
            noteRepository.deleteNote(note)
        }
    }

    // val supportSelectedMode=SupportSelectedMode()
    fun add7Notes() {
        for (i in 1..7) {
            noteRepository.addNote(Note(0, "${i}", "${i}", ""))
        }
    }

    fun getExampleNotes(count: Int) = List(count) {
        Note(
            it,
            "$it title",
            "descr",
            ""
        )
    }


}