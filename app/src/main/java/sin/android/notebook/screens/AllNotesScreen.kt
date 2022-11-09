package sin.android.notebook.screens

import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.End
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sin.android.notebook.SettingsMaster
import sin.android.notebook.SupportScreens.SupportScaffoldMenu
import sin.android.notebook.SupportScreens.SupportSelectedMode
import sin.android.notebook.ViewModels.AllNotesVIewModel
import sin.android.notebook.data.Note
import sin.android.notebook.ui.theme.NotebookTheme
import androidx.compose.runtime.mutableStateOf as mutableStateOf


val TAG = "AllNotesScreen"


@Composable
fun MyBottomAppBar(
    allNotesVIewModel: AllNotesVIewModel,
    supportSelectedMode: SupportSelectedMode
) {
    BottomAppBar {
        IconButton(onClick = {
            allNotesVIewModel
                .deleteSelectedNotes(supportSelectedMode.getSelectedNotes())
            supportSelectedMode.offSelectedMode()
        }) {
            Icon(Icons.Filled.Delete, contentDescription = "")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = {
            supportSelectedMode.offSelectedMode()
            supportSelectedMode.cancelAllSelections()
        }) {
            Icon(Icons.Filled.Close, "")
        }
    }
}

@Composable
fun DriverMenu(
    settingsMaster: SettingsMaster
) {
    // val context = LocalContext.current
    val scope = rememberCoroutineScope()
    // val settingsMaster = SettingsMaster(context)

    Row {

        val state = settingsMaster.isDarkThemeFlow().collectAsState(initial = false)
        //  val chState = settingsMaster.getTheme2()

        Text("Dark theme")
        Switch(
            checked = state.value,
            onCheckedChange = {
                scope.launch {
                    settingsMaster.saveTheme(it)
                }
            }
        )
    }
}


@Composable
fun SearchAlertDialog(
    // allNotesVIewModel: AllNotesVIewModel
    offSearchAlertDialog: () -> Unit
) {
    val searchArgTitle = rememberSaveable { mutableStateOf("") }
    AlertDialog(onDismissRequest = {
        offSearchAlertDialog()
    },
        buttons = {
            Column(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                TitleTextField(title = searchArgTitle)
                Button(modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End),
                    onClick = {
                        //      allNotesVIewModel.flowSearchedNotes(searchArgTitle.value)
                    }) {
                    Text(text = "Search")
                }
            }
        }
    )
}



@Composable
fun AllNotesView(
    onNoteSelect: (Note) -> Unit,
    onAddNewNoteClicked: () -> Unit,
    allNotesVIewModel: AllNotesVIewModel, settingsMaster: SettingsMaster
) {

    val notes = allNotesVIewModel.getExampleNotes(4)
    val items by allNotesVIewModel.flowAllNotes().collectAsState(initial = notes)
   // val allNotes=allNotesVIewModel.flowAllNotes().collect()


    val supportSelectedMode by remember { mutableStateOf(SupportSelectedMode(items)) }

    val supportScaffoldMenu by remember { mutableStateOf(SupportScaffoldMenu()) }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var openSearchAlertDialog by rememberSaveable { mutableStateOf(false) }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar {
                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                    //    supportScaffoldMenu.driverSee.value = true
                }) {
                    Icon(Icons.Filled.Menu, "")
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    openSearchAlertDialog = true
                }) {
                    Icon(Icons.Filled.Search, "")
                }
            }
        },

        bottomBar = {
            if (supportSelectedMode.selectedMode.value) {
                MyBottomAppBar(allNotesVIewModel, supportSelectedMode)
            }
        },
        drawerContent = {
            // if (supportScaffoldMenu.driverSee.value) {
            DriverMenu(settingsMaster)
            //   }
        }


    ) {
        Column() {

            if (openSearchAlertDialog) {
                SearchAlertDialog(
                    //   allNotesVIewModel = allNotesVIewModel
                    { openSearchAlertDialog = false }
                )
            }


/*            Button(

                onClick = {
                    allNotesVIewModel
                        .deleteSelectedNotes(supportSelectedMode.getSelectedNotes())
                }) {
                Text(text = "delete selected")
            }
            Button(onClick = { allNotesVIewModel.add7Notes() }) {
                Text(text = "add 7 notes")
            }*/

            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
                //horizontalAlignment = Alignment.End
            ) {
                val createNewNote = {
                    onNoteSelect(
                        Note(
                            0,
                            "",
                            "",
                            ""
                        )
                    )// Calendar.getInstance().timeInMillis.toInt()))
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
}

@Preview(showBackground = true)
@Composable
fun AllNotesViewPreview() {
    NotebookTheme {
        //   AllNotesView({},{},MainViewModel(a))
    }
}

/*@Preview
@Composable
fun SearchAlertDialogPreview() {
    SearchAlertDialog(
        //  allNotesVIewModel
        {}
    )
}*/

