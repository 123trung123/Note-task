package edu.uit.o21.note_task

import android.app.Application
import androidx.room.Room

class NoteTaskApplication : Application() {

    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "note_task_database"
        ).build()
    }
}