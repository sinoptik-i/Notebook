package sin.android.notebook

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import sin.android.notebook.data.Note
import sin.android.notebook.ui.theme.NotebookTheme
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainActivity : ComponentActivity() {


    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotebookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainFun(mainViewModel)
                }
            }
        }
    }
}


@Composable
fun NoteView(title: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text(text = title)
        }
    }
}

@Preview
@Composable
fun NoteViewPreview() {
    NotebookTheme {
        NoteView("title")
    }
}

@Composable
fun AllNotesView(
    notes: List<Note> = List(4) { Note(it, "$it title", "descr") },
    onContinueClicked: () -> Unit,
    mainViewModel: MainViewModel
) {
    Column() {
        IconButton(
            onClick = onContinueClicked,
            modifier = Modifier
                .size(60.dp)           // .absoluteOffset(x = 200.dp, y = 200.dp)
                .border(2.dp, MaterialTheme.colors.error, CircleShape)

        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null
            )

        }

        LazyColumn(
            Modifier
                .padding(top = 4.dp, bottom = 4.dp)
        ) {
//            lifecycle.coroutineScope.launch{}


            /* coroutineScope {
                 mainViewModel.flowAllNotes().collect()
             }*/
            items(mainViewModel.allNotes.value ?: notes) {
                NoteView(title = it.title)
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


@Composable
fun TitleTextField(title: String) {
    var text by rememberSaveable { mutableStateOf(title) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        value = text,
        onValueChange = { text = it },
        label = { Text("Title:") }
    )
}

@Preview
@Composable
fun TitleTextFieldPreview() {
    TitleTextField(title = "title")
}

@Composable
fun DescriptionTextField(
    description: String,
    onContinueClicked: (newDescription: String) -> Unit = {}
) {
    var text by rememberSaveable { mutableStateOf(description) }

    /* val uniteContinuations = {

     }*/

    TextField(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxSize(),
        value = text,
        onValueChange = { text = it },
        label = { Text("Description:") }
    )
}


@Composable
fun OneFullNote(
    mainViewModel: MainViewModel,
    numOfSelectedNote: Int,
    //note: Note = Note(1, "anyTitle", "ololo pwpw"),
    onContinueClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        var textTitle by rememberSaveable { mutableStateOf("") }
        var textDescription by rememberSaveable { mutableStateOf("") }
        val uniteContinuations: () -> Unit = {
            if(mainViewModel.tryToAddNote(textTitle,textDescription)){
                textTitle="sucess"
            }
            onContinueClicked
        }
        IconButton(
            onClick =
            //onContinueClicked,
            uniteContinuations,
            modifier = Modifier
                .size(60.dp)
                .border(2.dp, MaterialTheme.colors.error, CircleShape)
            //.absoluteOffset(x=30.dp)//,0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null
            )
        }
        //   note : Note = if(numOfSelectedNote>-1) {}

        TitleTextField(title = textTitle)//note.title)
        DescriptionTextField(description = textDescription)//note.description)
    }
}

/*
@Preview
@Composable
fun OneFullNotePreview() {
    OneFullNote(

       // note = Note(1, "anyTitle", "ololo pwpw"),
        2,
        {})
}
*/


@Composable
fun MainFun(mainViewModel: MainViewModel) {
    var seeAllNotes by rememberSaveable { mutableStateOf(true) }
    if (seeAllNotes) {
        AllNotesView(
            onContinueClicked = { seeAllNotes = false },
            mainViewModel = mainViewModel
        )
    } else {
        OneFullNote(
            mainViewModel = mainViewModel,
            2,
            onContinueClicked = { seeAllNotes = true })
    }
}