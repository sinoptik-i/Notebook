package sin.android.notebook.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import sin.android.notebook.data.Note
import sin.android.notebook.data.NoteDao
import sin.android.notebook.data.NoteDatabase
import sin.android.notebook.data.NoteRepository

class AllNotesVIewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao: NoteDao
        get() = NoteDatabase.getDatabase(getApplication()).noteDao()

    private val noteRepository = NoteRepository(noteDao, viewModelScope)
    private val _query = MutableStateFlow<String>("")

    fun flowAllNotes() = _query
        .debounce(1000L)
        .distinctUntilChanged()
        .combine(noteDao.getAllNotes()) { query, items ->
            if (query.isEmpty()) {
                items
            } else {
                items.filter { it.title.contains(query) }
            }

        }.distinctUntilChanged()


    fun setSearchArgNotes(searchArgTitle: String) {
        _query.value = searchArgTitle
    }

    fun getQuery()=_query.value

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