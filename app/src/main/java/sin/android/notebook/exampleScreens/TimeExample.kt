package sin.android.notebook.exampleScreens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import sin.android.notebook.data.Note
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ExTime(){
    val note = Note(0, "", "", Calendar.getInstance().timeInMillis)

    val dateFormat = SimpleDateFormat("dd.MM.yyyy hh:mm")
    val strTime=dateFormat.format(note.time)

    Text(text = "${note.time} ${strTime} note.time")

}