package sin.android.notebook.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sin.android.notebook.ViewModels.MainViewModel
import sin.android.notebook.data.Note
import sin.android.notebook.ui.theme.NotebookTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteView(
    note: Note,
    onClicked: () -> Unit,
    onLongClicked: () -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .combinedClickable (
            onClick = onClicked,
            onLongClick = onLongClicked
            )
        /*    .selectable(
                selected = true,
                onClick = onClicked
            )*/
    ) {
        Row(
            Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text(text = note.title)
        }
    }
}

@Preview
@Composable
fun NoteViewPreview() {
    NotebookTheme {
        // NoteView("title", {})
    }
}

@Composable
fun AllNotesView(
    note: Note,
    onNoteSelect: (Note) -> Unit,
    onContinueClicked: () -> Unit,
    mainViewModel: MainViewModel
) {
    Column() {
        val createNewNote = {
            onNoteSelect(Note(0, "", ""))
            onContinueClicked()
        }
        IconButton(
            onClick = createNewNote,
            modifier = Modifier
                .size(60.dp)           // .absoluteOffset(x = 200.dp, y = 200.dp)
                .border(2.dp, MaterialTheme.colors.error, CircleShape)

        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null
            )

        }
        val notes: List<Note> = List(4) { Note(it, "$it title", "descr") }
        val items by mainViewModel.flowAllNotes().collectAsState(initial = notes)

        LazyColumn(
            Modifier
                .padding(top = 4.dp, bottom = 4.dp)

        ) {
            items(items) {
                val selectNote = {
                    onNoteSelect(it)
                    onContinueClicked()
                }
                val deleteNote={
                    mainViewModel.deleteNote(it)
                }
                NoteView(
                    it,
                    {
                        selectNote()
                    },
                    deleteNote
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllNotesViewPreview() {
    NotebookTheme {
        // AllNotesView(onContinueClicked = {})
    }
}
