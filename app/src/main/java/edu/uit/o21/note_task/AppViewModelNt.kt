package edu.uit.o21.note_task

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
// ViewModel factory is used for creating Note and Task related ViewModels
object AppViewModelNt {//ViewModel factory is setup using viewModelFactory
    val Factory = viewModelFactory {
        initializer {
            val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NoteTaskApplication
            NoteViewModel(app.dao)
        }
        initializer {
            val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NoteTaskApplication
            TaskViewModel(app.dao)
        }
        initializer {
            val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NoteTaskApplication
            TaskListViewModel(app.dao)
        }
        initializer {
            val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NoteTaskApplication
            NoteListViewModel(app.dao)
        }
    }
}
