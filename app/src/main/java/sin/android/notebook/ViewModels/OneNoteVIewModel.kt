package sin.android.notebook.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import sin.android.notebook.data.Note
import sin.android.notebook.data.NoteDao
import sin.android.notebook.data.NoteDatabase
import sin.android.notebook.data.NoteRepository

class OneNoteVIewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao: NoteDao
        get() = NoteDatabase.getDatabase(getApplication()).noteDao()


    private val noteRepository = NoteRepository(noteDao, viewModelScope)

    private fun addNote(note: Note) = noteRepository.addNote(note)


    fun tryToAddNote(id:Int, title: String, description: String): Boolean {
        if (title != "" && description != "") {
            addNote(Note(id, title, description,0))
            return true
        }
        return false
    }
}