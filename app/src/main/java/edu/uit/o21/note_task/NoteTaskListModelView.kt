package edu.uit.o21.note_task
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
class NoteListViewModel(private val dao: NoteTaskDao) : ViewModel() {
    val state: StateFlow<NoteListUiState>
        get() {
            return dao.getAllNotes().map { NoteListUiState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = NoteListUiState()
                )
        }
}

class TaskListViewModel(private val dao: NoteTaskDao) : ViewModel() {

    val state: StateFlow<TaskListUiState>
        get() {
            return dao.getAllTasks().map { TaskListUiState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = TaskListUiState()
                )
        }
}
