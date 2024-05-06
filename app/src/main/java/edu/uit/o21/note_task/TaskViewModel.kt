package edu.uit.o21.note_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: NoteTaskDao) : ViewModel() {
    private val _state: MutableStateFlow<TaskUiState> = MutableStateFlow(TaskUiState())
    val state: StateFlow<TaskUiState> = _state.asStateFlow()

    fun setTitle(title: String) {
        _state.update {
            it.copy(title = title)
        }
    }

    fun setDescription(description: String) {
        _state.update {
            it.copy(description = description)
        }
    }

    fun setChecked(isChecked: Boolean) {
        _state.update {
            it.copy(isChecked = isChecked)
        }
    }

    fun insertTask() {
        viewModelScope.launch {
            val task = Task(_state.value.title, _state.value.description, _state.value.isChecked)
            taskDao.insert(task)
        }
        _state.update {
            it.copy(title = "", description = "", isChecked = false)
        }
    }
}
