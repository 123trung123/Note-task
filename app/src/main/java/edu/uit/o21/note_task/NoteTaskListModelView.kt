package edu.uit.o21.note_task
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteListViewModel(private val dao: NoteTaskDao) : ViewModel() {
    private val _state: MutableStateFlow<NoteUiState> = MutableStateFlow(NoteUiState())

    val state: StateFlow<NoteListUiState>
        get() {
            return dao.getAllNotes().map { NoteListUiState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = NoteListUiState()
                )
        }


    fun deleteNoteById(noteId: Int) {
        viewModelScope.launch {
            dao.deleteNoteById(noteId)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            dao.updateNote(note)
        }
    }
}

class TaskListViewModel(private val dao: NoteTaskDao) : ViewModel() {

    private val _tasks = dao.getAllTasks().map { TaskListUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = TaskListUiState()
        )

    val state: StateFlow<TaskListUiState>
        get() = _tasks

    fun updateTask(task: Task) {
        viewModelScope.launch {
            dao.updateTask(task)
        }
    }
    fun deleteTaskById(taskId: Int) {
        viewModelScope.launch {
            dao.deleteTaskById(taskId)
        }
    }
}