package sin.android.notebook.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import sin.android.notebook.data.Note
import sin.android.notebook.data.NoteDao
import sin.android.notebook.data.NoteDatabase
import sin.android.notebook.data.NoteRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class OneNoteVIewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private fun addNote(note: Note) = noteRepository.addNote(note)

    fun tryToAddNote(id: Int, title: String, description: String): Boolean {
        if (title != "") {
            addNote(
                Note(
                    id, title, description,
                    getNowTime()
                )
            )
            return true
        } else if (description == "") {
            return false
        } else {
            val titleLength = if (description.length > 10) {
                10
            } else {
                description.length
            }
            addNote(
                Note(
                    id, description.substring(0, titleLength), description,
                    getNowTime()
                )
            )
            return true
        }


    }

    fun getNowTime(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy hh:mm")
        return dateFormat.format(Calendar.getInstance().timeInMillis)
    }
}