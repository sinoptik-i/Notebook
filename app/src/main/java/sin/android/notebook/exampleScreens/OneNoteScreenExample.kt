package sin.android.notebook.exampleScreens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sin.android.notebook.data.Note
import sin.android.notebook.exampleViewModels.ExampleViewModel
import sin.android.notebook.exampleViewModels.ScreenStateVM
import sin.android.notebook.screens.DescriptionTextField
import sin.android.notebook.screens.TitleTextField


@Composable
fun OneFullNoteExample(
   // mainViewModel: ExampleViewModel,
    //selectedNote: Note = Note(0,"",""),
    onContinueClicked: () -> Unit
) {
 /*   val sсreenStateVM: State<ScreenStateVM?> =
        mainViewModel.screenStateVM.observeAsState()*/
//    mainViewModel.screenStateVM.observeAsState(ScreenStateVM())

    val selectedNote: Note =Note(0,"","")
 //       sсreenStateVM.value?.selectedNote ?: Note(0, "", "")

    var textTitle = remember {
        mutableStateOf(
            selectedNote.title
            //screenStateVM.value?.selectedNote.title
        )
    }
//    var textDescription by rememberSaveable { mutableStateOf("") }
    var textDescription = remember {
        mutableStateOf(
            selectedNote.description
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val uniteContinuations: () -> Unit = {
           /* if (mainViewModel.tryToAddNote(
                    Note(
                        selectedNote.id,
                        textTitle.value,
                        textDescription.value
                    )
                )
            ) {
                onContinueClicked()
            }*/
        }
        IconButton(
            onClick = onContinueClicked,
            modifier = Modifier
                .size(60.dp)
                .border(2.dp, MaterialTheme.colors.error, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null
            )
        }
        TitleTextField(title = textTitle)
        DescriptionTextField(description = textDescription)
    }
}