package edu.uit.o21.note_task

import android.app.Application

class NoteTaskApplication : Application() {
    lateinit var dao: NoteTaskDao// Data Access Object for accessing the database
    override fun onCreate() {
        super.onCreate()
        dao = AppDatabase.getDatabase(this.applicationContext).noteTaskDao()
    }
}