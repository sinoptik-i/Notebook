package sin.android.notebook.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/*interface INotesRepository {
    fun allNotes(): Flow<List<Note>>
}*/

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao,
    private val scope: CoroutineScope
)  {

  //  var cache: List<Note> = emptyList()
    /*private val noteDao: NoteDao
        get() = NoteDatabase.getDatabase(getApplication()).noteDao()*/

    /*val allNotes: Flow<List<Note>> = noteDao.getAllNotes()
        .onEach { cache = it }
        .onStart { emit(cache) }*/

    fun allNotes() =  noteDao.getAllNotes()

    fun addNote(note: Note) {
        scope.launch {
            noteDao.addNote(note)
        }
    }

   // fun getNote(noteID: Int) = noteDao.getNoteById(noteID)

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