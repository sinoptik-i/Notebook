package sin.android.notebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import sin.android.notebook.ViewModels.MainViewModel
import sin.android.notebook.data.Note
import sin.android.notebook.exampleScreens.BeginScreen
import sin.android.notebook.exampleScreens.GreetingContainer
import sin.android.notebook.exampleScreens.MainScreen
import sin.android.notebook.exampleScreens.StartFunTTN
import sin.android.notebook.screens.AllNotesView
import sin.android.notebook.screens.OneFullNote
import sin.android.notebook.ui.theme.NotebookTheme

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
                    //StartFunTTN()// It work!!!
                    //  GreetingContainer()
                   // BeginScreen()// It work!!!
                    //  MainScreen()
                    //  StartFun(mainViewModel)
                     MainFun(mainViewModel)
                    // mainViewModel.tryToAddNote("ExampleTitle","Descr")
                }
            }
        }
    }
}


@Composable
fun MainFun(mainViewModel: MainViewModel) {
    var seeAllNotes by rememberSaveable { mutableStateOf(true) }
    val note = remember { mutableStateOf(Note(0, "", "")) }
    if (seeAllNotes) {
        AllNotesView(
            note = note.value,
            onNoteSelect = {
                note.value = it
            },
            onContinueClicked = { seeAllNotes = false },
            mainViewModel = mainViewModel
        )
    } else {
        OneFullNote(
            note.value,
            mainViewModel = mainViewModel,
            onContinueClicked = { seeAllNotes = true })
    }
}