package sin.android.notebook

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sin.android.notebook.ViewModels.AllNotesVIewModel
import sin.android.notebook.ViewModels.OneNoteVIewModel
import sin.android.notebook.ViewModels.ViewModelFactory
import sin.android.notebook.data.Note
import sin.android.notebook.di.DaggerNotesComponent
import sin.android.notebook.di.NotesComponent
import sin.android.notebook.screens.AllNotesView
import sin.android.notebook.screens.OneFullNote
import sin.android.notebook.ui.theme.NotebookTheme
import javax.inject.Inject


class MainApp : Application() {

    lateinit var appComponent: NotesComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerNotesComponent.builder().build()
    }

}


val Context.appComponent :NotesComponent
    get() = when (this){
        is MainApp -> appComponent
        else -> this.applicationContext.appComponent
    }


class MainActivity : ComponentActivity() {




    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val oneNoteVIewModel: OneNoteVIewModel by viewModels(factoryProducer = { factory })
    private val allNotesVIewModel: AllNotesVIewModel by viewModels(factoryProducer = { factory })

    @Inject
    lateinit var settingsMaster: SettingsMaster
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        appComponent.inject(this)

        DaggerNotesComponent.builder()
            .application(application)
            .build().inject(this)

        setContent {
            var darkThemeState = settingsMaster.isDarkThemeFlow().collectAsState(initial = false)
            NotebookTheme(
                darkTheme = darkThemeState.value
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainFun(allNotesVIewModel, oneNoteVIewModel, settingsMaster)
                }
            }
        }
    }
}


@Composable
fun MainFun(
    allNotesVIewModel: AllNotesVIewModel,
    oneNoteVIewModel: OneNoteVIewModel, settingsMaster: SettingsMaster
) {
    // if true - table with all notes, false - one note
    var seeAllNotes by rememberSaveable { mutableStateOf(true) }
    val note = remember { mutableStateOf(Note(0, "", "", "")) }
    if (seeAllNotes) {
        AllNotesView(
            onNoteSelect = {
                note.value = it
            },
            onAddNewNoteClicked = { seeAllNotes = false },
            allNotesVIewModel = allNotesVIewModel, settingsMaster
        )
    } else {
        OneFullNote(
            note.value,
            oneNoteVIewModel = oneNoteVIewModel,
            onContinueClicked = { seeAllNotes = true })
    }
}


/*

@Composable
fun OverMind(
    allNotesVIewModel: AllNotesVIewModel,
    oneNoteVIewModel: OneNoteVIewModel
    ,settingsMaster: SettingsMaster
) {
    //val settingsMaster = SettingsMaster(LocalContext.current)
    val darkThemeState = settingsMaster.isDarkThemeFlow().collectAsState(initial = false)


    NotebookTheme(
        darkTheme = darkThemeState.value
    ) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            MainFun(allNotesVIewModel, oneNoteVIewModel
                ,settingsMaster
            )
        }
    }
}

*/
