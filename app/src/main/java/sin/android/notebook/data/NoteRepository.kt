package sin.android.notebook.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteRepository(
    private val noteDao: NoteDao,
    //scope :
    private val scope: CoroutineScope
) {


    /*private val noteDao: NoteDao
        get() = NoteDatabase.getDatabase(getApplication()).noteDao()*/

    val allNotes : Flow<List<Note>> = noteDao.getAllNotes()

    fun addNote(note: Note) {
        scope.launch {
            noteDao.addNote(note)
        }
    }

    suspend fun changeNote(note: Note) {
        noteDao.changeNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}