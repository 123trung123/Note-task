package edu.uit.o21.note_task

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object AppViewModelNt {
    val NoteFactory = viewModelFactory {
        initializer {
            val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NoteTaskApplication
            NoteViewModel(app.dao)
        }
    }

    val TaskFactory = viewModelFactory {
        initializer {
            val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NoteTaskApplication
            TaskViewModel(app.dao)
        }
    }
}