package sin.android.notebook.ViewModels

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import sin.android.notebook.data.Note
import sin.android.notebook.data.NoteDao
import sin.android.notebook.data.NoteDatabase
import sin.android.notebook.data.NoteRepository





//class MainViewModel(application: Application) : ViewModel() {
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao: NoteDao
        get() = NoteDatabase.getDatabase(getApplication()).noteDao()

    private val noteRepository = NoteRepository(noteDao, viewModelScope)

    init {
        viewModelScope.launch {
            noteDao.getAllNotes().collect {
                _allNotes.value = it
            }
        }
    }
    /*  var allNotes: MutableLiveData<List<Note>>
      get() =  {
          viewModelScope.launch {
              noteDao.getAllNotes().collect {
      allNotes=it
              }
          }
      }*/

    fun flowAllNotes() = noteDao.getAllNotes().flowOn(Dispatchers.IO)

    fun tryToAddNote(id:Int, title: String, description: String): Boolean {
        if (title != "" && description != "") {
            addNote(Note(id, title, description))
            return true
        }
        return false
    }


    fun tryToAddNote(note: Note): Boolean {
        if (note.title != "" && note.description != "") {
            addNote(note)
            return true
        }
        return false
    }


    private fun addNote(note: Note) = noteRepository.addNote(note)

    fun deleteNote(note: Note)=noteRepository.deleteNote(note)


    private val _allNotes = MutableStateFlow<List<Note>>(emptyList())
    val allNotes: Flow<List<Note>> = _allNotes



}