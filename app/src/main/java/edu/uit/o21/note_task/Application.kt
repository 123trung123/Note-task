package edu.uit.o21.note_task

import android.app.Application

class NoteTaskApplication : Application() {

    companion object {
        lateinit var instance: NoteTaskApplication
            private set
    }

    lateinit var dao: NoteTaskDao
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        dao = AppDatabase.getDatabase(this.applicationContext).noteTaskDao()
    }
}