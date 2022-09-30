package sin.android.notebook.exampleScreens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sin.android.notebook.ViewModels.AllNotesVIewModel
import sin.android.notebook.data.Note
import sin.android.notebook.exampleViewModels.ExampleViewModel


@Composable
fun StartFun(exampleViewModel: ExampleViewModel) {
    //  var screenState by rememberSaveable { mutableStateOf(ScreenState()) }
//    val screenStateVM: State<ScreenStateVM?> = mainViewModel.screenStateVM.observeAsState()


/*    val screenStateVM: State<ScreenStateVM2> =
        mainViewModel.screenState.observeAsState()*/

    val screenStateVM = exampleViewModel.screenState.observeAsState()

/*    if (screenStateVM.value?.seeAllNotes == true) {
        AllNotesViewExample(
            //         onContinueClicked = { screenStateVM.value.seeAllNotes = false },
            onContinueClicked = { exampleViewModel.changeScreen() },
            mainViewModel = exampleViewModel
        )
    } else {
        OneFullNoteExample(
            mainViewModel = exampleViewModel,
            //onContinueClicked = { screenStateVM.value?.seeAllNotes = true }
            onContinueClicked = { exampleViewModel.changeScreen() }
        )
    }*/
}


@Composable
fun AllNotesViewExample(
    notes: List<Note> = List(4) { Note(it, "$it title", "descr") },
    onContinueClicked: () -> Unit,
  //  viewModel: AllNotesVIewModel
) {
    Column() {
        IconButton(
            //onClick = { mainViewModel.screenStateVM2.value?.seeAllNotes = false },
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
            items(notes) {
                NoteViewExample(
                    it,
                    {
                        onContinueClicked()
                      //  viewModel.changeScreen()
                    }
                )
            }
        }
    }
}

@Composable
fun NoteViewExample(note: Note, onClicked: () -> Unit) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .selectable(
                selected = true,
                onClick = onClicked
            )
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

@Composable
fun KittenView(count: Int, onClicked: () -> Unit) {
    Column {
        Text(text = " THere are $count cats")
        Button(onClick = onClicked) {
            Text(text = "getKittens")
        }

    }
}

@Composable
fun MainScreen(exampleViewModel: ExampleViewModel = ExampleViewModel()) {
    val count: Int by exampleViewModel.count.observeAsState(3)

    KittenView(count = count, { exampleViewModel.releaseNewKittens() })

}


/*@Composable
fun FirstFun(
    note: Note = Note(0, "", ""),
    moveToScreen: (screenState: ScreenState) -> Unit
) {
    Column() {
        Text(text = note.title)
        Button(onClick = { moveToScreen }) {

        }
    }
}*/