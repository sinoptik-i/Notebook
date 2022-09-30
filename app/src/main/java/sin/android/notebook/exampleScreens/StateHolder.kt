package sin.android.notebook.exampleScreens

import androidx.compose.runtime.*
import sin.android.notebook.data.Note

class AppState() {
    var note: Note by mutableStateOf(Note(0, "", ""))

    var allNotesScreen: Boolean by mutableStateOf(true)

    fun updateNote(newNote: Note) {
        note = newNote
    }

    fun changeScreeen() {
        allNotesScreen = !allNotesScreen
    }

}

@Composable
fun rememberAppState(): AppState = remember {
    AppState()
}