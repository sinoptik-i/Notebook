package sin.android.notebook.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NoteRepository(
    private val noteDao: NoteDao,
    //scope :
    private val scope: CoroutineScope
) {


    /*private val noteDao: NoteDao
        get() = NoteDatabase.getDatabase(getApplication()).noteDao()*/

    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    fun addNote(note: Note) {
        scope.launch {
            noteDao.addNote(note)
        }
    }

    fun getNote(noteID: Int) = noteDao.getNoteById(noteID)

/*
    fun getNote(noteID: Int) {
        scope.launch {
            noteDao.getNoteById(noteID).collect {  }
        }
    }
*/

     fun deleteNote(note: Note) {
        scope.launch {
            noteDao.deleteNote(note)
        }
    }
}