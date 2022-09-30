package sin.android.notebook.exampleScreens

import android.app.Application
import androidx.compose.runtime.Composable
import sin.android.notebook.ViewModels.MainViewModel

@Composable
fun BeginScreen() {
    val appState = rememberAppState()
    if (appState.allNotesScreen) {
        AllNotesViewExample { appState.changeScreeen() }

    } else {
        OneFullNoteExample {
            appState.changeScreeen()
        }
    }
}





















/*

fun fun1(){
    val a=6
    val labmdaForFun2={ */
/*как передать сюда а? *//*
  }
    fun2(
        labmdaForFun2()
    )
}


fun fun2(lamda: (a: Int) -> Unit) {

}*/
