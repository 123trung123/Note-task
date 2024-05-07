package edu.uit.o21.note_task

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class, Task::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteTaskDao(): NoteTaskDao // Changed from noteDao() and taskDao()

    companion object {
        @Volatile
        private var DataBase: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return DataBase ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "note_task_database"
                ).build()
                DataBase = instance
                instance
            }
        }
    }
}