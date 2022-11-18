package sin.android.notebook

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import sin.android.notebook.ViewModels.AllNotesVIewModel
import sin.android.notebook.data.Note

class MyTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }

    @Test
    fun stringTest() {
        val str="123"
        val str2="12345678"
        val str3="12 345 67"
        val str4="123qwerty"
        println(str2.substring(0,6))
        println(str3.substring(0,6))
        println(str4.substring(0,6))
    }


    @Test
    fun TestSearch() {
        runBlocking {

            val notes: List<Note> = List(4) {
                Note(
                    it,
                    "qwe${it%2}$it title",
                    "descr",
                    ""
                )
            }



            notes.forEach { println(it.title) }
            println("----")

            val strQuery="qwe0"
            val foundNotes = notes.asFlow()
                .filter {
                    it.title.contains(strQuery)
                }
                .collect {
                    println(it.title)
                }


         //  foundNotes
            println(foundNotes)
        }


    }

}