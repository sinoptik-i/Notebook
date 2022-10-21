package sin.android.notebook.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sin.android.notebook.SupportSelectedMode
import sin.android.notebook.ViewModels.AllNotesVIewModel
import sin.android.notebook.data.Note
import sin.android.notebook.ui.theme.NotebookTheme


val TAG = "AllNotesScreen"

@Composable
fun AllNotesView(
    onNoteSelect: (Note) -> Unit,
    onAddNewNoteClicked: () -> Unit,
    allNotesVIewModel: AllNotesVIewModel
) {

    // var deleteButtonClicked by remember { mutableStateOf(false) }

    Column() {
        val notes = allNotesVIewModel.getExampleNotes(4)
        val items by allNotesVIewModel.flowAllNotes().collectAsState(initial = notes)

        val supportSelectedMode by remember { mutableStateOf(SupportSelectedMode(items)) }


        Button(

            onClick = {
                allNotesVIewModel
                    .deleteSelectedNotes(supportSelectedMode.getSelectedNotes())
            }) {
            Text(text = "delete selected")
        }
        Button(onClick = { allNotesVIewModel.add7Notes() }) {
            Text(text = "add 7 notes")
        }

        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
            //horizontalAlignment = Alignment.End
        ) {
            val createNewNote = {
                onNoteSelect(Note(0, "", "", ""))// Calendar.getInstance().timeInMillis.toInt()))
                onAddNewNoteClicked()
            }


            val mode by supportSelectedMode.selectedMode
            //var selectedMode by remember { mutableStateOf(false) }
            LazyColumn(
                Modifier
                    .padding(top = 4.dp, bottom = 4.dp)
            ) {
                items(items) {
                    var checkState by supportSelectedMode.getCheckState(it)
//                    var checkState by rememberSaveable { mutableStateOf(false) }

                    /* if (deleteButtonClicked) {
                         Log.e(TAG, "$it, $checkState")
                         if (checkState) {
                             allNotesVIewModel.deleteNote(it)
                         }
                     }*/

                    val selectNote = {
                        onNoteSelect(it)
                        onAddNewNoteClicked()
                    }
                    val itNote = it

                    NoteView(
                        it,
                        mode,//supportSelectedMode.selectedMode.value,
                        checkState,
                        {
                            supportSelectedMode.changeSelectofNote(itNote, it)
                        },
                        selectNote,
                        {
                            supportSelectedMode.changeSelectedMode(it)
                        }
                    )
                }
            }

            IconButton(
                onClick = createNewNote,
                modifier = Modifier

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
}

@Preview(showBackground = true)
@Composable
fun AllNotesViewPreview() {
    NotebookTheme {
        //   AllNotesView({},{},MainViewModel(a))
    }
}
