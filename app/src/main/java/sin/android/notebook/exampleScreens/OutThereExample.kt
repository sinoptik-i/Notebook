package sin.android.notebook.exampleScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun GreetingContainer() {
    val text = remember { mutableStateOf("") }
    InputGreeting(
        value = text.value,
        onValueChange = {
            text.value = it
        }
    )
    Question(value = text.value)
}

@Composable
fun InputGreeting(
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange
        )
        Text(text = "Hello $value")
    }
}

@Composable
fun Question(value: String) {
    Text(text = "How are you today $value ?")
}