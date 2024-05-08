package edu.uit.o21.note_task

import android.app.Application

class NoteTaskApplication : Application() {
    lateinit var dao: NoteTaskDao
    override fun onCreate() {
        super.onCreate()
        dao = AppDatabase.getDatabase(this.applicationContext).noteTaskDao()
    }
}