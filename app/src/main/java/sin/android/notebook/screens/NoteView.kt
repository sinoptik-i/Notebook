package sin.android.notebook.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sin.android.notebook.data.Note
import java.text.SimpleDateFormat


@Composable
fun DeleteNoteAlertDialog(
    onClickYes: () -> Unit,
    onClickNo: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClickNo,
        buttons = {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Button(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .weight(1F),
                    onClick = {
                        onClickYes()
                        onClickNo()
                    }) {
                    Text("Да")
                }
                Button(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .weight(1F),
                    onClick = onClickNo
                ) {
                    Text("Нет")
                }
            }
        },
        title = {
            Text(
                text = "Удалить эту запись?",
                modifier = Modifier.fillMaxWidth(1f),
                textAlign = TextAlign.Center// не работает без ^
            )
        }
    )

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteView(
    note: Note,
    onClicked: () -> Unit,
    onLongClicked: () -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .combinedClickable(
                onClick = onClicked,
                onLongClick = { openDialog.value = true }
            )
    ) {
        Row(
            Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = note.title,
                modifier = Modifier
                    .weight(3f)
            )
            Text(
                text = timeFromLongToString(note.time),
                modifier = Modifier
                    .weight(2f)
                //align(Alignment.End)
            )
        }
    }
    if (openDialog.value) {
        DeleteNoteAlertDialog(
            onClickYes = onLongClicked,
            onClickNo = { openDialog.value = false }
        )
    }
}

fun timeFromLongToString(time: Long): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy hh:mm")
    return dateFormat.format(time)
}