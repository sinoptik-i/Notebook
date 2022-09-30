package sin.android.notebook.exampleScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import sin.android.notebook.data.Note
import sin.android.notebook.exampleViewModels.ExampleViewModel


@Composable
fun StartFunTTN() {
    val tumbler = remember { mutableStateOf(true) }
    val note = remember { mutableStateOf(Note(0, "", "")) }

    if (tumbler.value) {
        ScreenFedor(note = note.value,
            onNoteChange = {
                note.value = it
            },
            switchTumbler = {
                tumbler.value = false
            }
        )
    } else {
        ScreenSawa(note = note.value,
            onNoteChange = {
                note.value = it
            },
            switchTumbler = {
                tumbler.value = true
            }
        )
    }
}

@Composable
fun ScreenFedor(
    note: Note,
    onNoteChange: (Note) -> Unit,
    switchTumbler: () -> Unit
) {
    Column {
        val action = {
            onNoteChange(Note(0, "fromFedor", "fdescr"))
            switchTumbler()
        }
        Text(text = "Ama Fedor, t: ${note.title}, d: ${note.description}")
        Button(onClick = { action() }) {}
    }
}

@Composable
fun ScreenSawa(
    note: Note,
    onNoteChange: (Note) -> Unit,
    switchTumbler: () -> Unit
) {
    Column {
        val action = {
            onNoteChange(Note(0, "fromSawa", "sdescr"))
            switchTumbler()
        }
        Text(text = "Ama Sawa, t: ${note.title}, d: ${note.description}")
        Button(onClick = { action() }) {}
    }
}