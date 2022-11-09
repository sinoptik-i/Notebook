package sin.android.notebook

import android.app.Application
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import org.junit.Test

import org.junit.Assert.*
import sin.android.notebook.ViewModels.AllNotesVIewModel
import sin.android.notebook.data.Note

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun mapmapmpa() {
        val notes: List<Note> = List(4) {
            Note(
                it,
                "$it title",
                "descr",
                ""
            )
        }
        val map = listOf(-1, -2, -3, 1, 2, 3, 4).associateWith { it > 0 }
        val map2 = notes.associateWith { false }.toMutableMap()

        //  println(map2)

        for (mapItem in map2) {
            println("${mapItem.key.id}, ${mapItem.value}")
        }
        val note1 = notes[1]

        map2.put(note1, true)
        val q = map2[note1]
        println(map2[note1])

        for (mapItem in map2) {
            println("${mapItem.key.id}, ${mapItem.value}")
        }

    }

    @Test
    suspend fun asdfdsaf() {
        val notes: List<Note> = List(4) {
            Note(
                it,
                "$it title",
                "descr",
                ""
            )
        }
        val allNotesVIewModel = AllNotesVIewModel(application = Application())

        lateinit var notesReal: List<Note>
        lateinit var mapNotes: MutableMap<Note, Boolean>

        allNotesVIewModel.flowAllNotes().collect {
            mapNotes = it.associateWith { false }.toMutableMap()
        }
    }

    @Test
    suspend fun testSearch() {
        val allNotesVIewModel = AllNotesVIewModel(application = Application())

        val allNotes = allNotesVIewModel.flowAllNotes().collect()
        println(allNotes)

        val foundNotes = allNotesVIewModel
            .flowAllNotes()
            .mapLatest { list ->
                list.filter {
                    it.id > 2
                }
            }
         //   .collect()


    }
}