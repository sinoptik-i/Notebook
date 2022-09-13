package sin.android.notebook.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Note::class), version = 1)
abstract class NoteDatabase :RoomDatabase(){
    abstract fun noteDao(): NoteDao

    companion object{
        //@Volatile - za4em ono nado, esli est synchronized?
        private var INSTANCE :NoteDatabase?=null

        fun getDatabase(context: Context):NoteDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }
}