package sin.android.notebook.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sin.android.notebook.ViewModels.AllNotesVIewModel
import sin.android.notebook.data.Note
import sin.android.notebook.ui.theme.NotebookTheme
import java.util.*



@Composable
fun AllNotesView(
    onNoteSelect: (Note) -> Unit,
    onContinueClicked: () -> Unit,
    allNotesVIewModel: AllNotesVIewModel
) {

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
        //horizontalAlignment = Alignment.End
    ) {
        val createNewNote = {
            onNoteSelect(Note(0, "", "", Calendar.getInstance().timeInMillis))
            onContinueClicked()
        }

        val notes: List<Note> = List(4) {
            Note(
                it,
                "$it title",
                "descr",
                Calendar.getInstance().timeInMillis
            )
        }
        val items by allNotesVIewModel.flowAllNotes().collectAsState(initial = notes)

        LazyColumn(
            Modifier
                .padding(top = 4.dp, bottom = 4.dp)
            // .weight(1f)

        ) {
            items(items) {
                val selectNote = {
                    onNoteSelect(it)
                    onContinueClicked()
                }
                val deleteNote = {
                    allNotesVIewModel.deleteNote(it)
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
        /*  IconButton(onClick = {  }) {
              Icon(
                  Icons.Filled.Info,
                  contentDescription = "Информация о приложении",
                  modifier = Modifier.size(80.dp),
                  tint = Color.Red
              )
          }*/
        IconButton(
            onClick = createNewNote,
            modifier = Modifier
            //   .background(Color.White)
            //     .size(60.dp)           // .absoluteOffset(x = 200.dp, y = 200.dp)
            //    .border(2.dp, MaterialTheme.colors.error, CircleShape)

        ) {
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                tint = Color.White
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllNotesViewPreview() {
    NotebookTheme {
        //   AllNotesView({},{},MainViewModel(a))
    }
}
