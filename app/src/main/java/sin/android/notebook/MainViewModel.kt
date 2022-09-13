package sin.android.notebook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import sin.android.notebook.data.Note
import sin.android.notebook.data.NoteDao
import sin.android.notebook.data.NoteDatabase
import sin.android.notebook.data.NoteRepository

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

    fun tryToAddNote(title: String, description: String): Boolean {
        if (title != "" && description != "") {
            addNote(Note(0, title, description))
            return true
        }
        return false
    }

    fun addNote(note: Note) = noteRepository.addNote(note)


    private val _allNotes = MutableLiveData<List<Note>>()
    val allNotes: LiveData<List<Note>> = _allNotes


}