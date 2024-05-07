package edu.uit.o21.note_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(val dao: NoteTaskDao) : ViewModel() {
    private val _state: MutableStateFlow<TaskUiState> = MutableStateFlow(TaskUiState())
    val state: StateFlow<TaskUiState> = _state.asStateFlow()
    fun setId(id: String) {
        _state.update {
            it.copy( id = id)
        }
    }
    fun setTitle(title: String) {
        _state.update {
            it.copy(title = title)
        }
    }

    fun setcontent(content: String) {
        _state.update {
            it.copy(content= content)
        }
    }

    fun setChecked(isChecked: Boolean) {
        _state.update {
            it.copy(isChecked = isChecked)
        }
    }

    fun insertTask() {
        viewModelScope.launch {
            val task = Task(
                id = _state.value.id,
                title = _state.value.title,
                content = _state.value.content,
                isChecked = _state.value.isChecked
            )
            dao.insertTask(task)
        }
        _state.update {
            it.copy(id = "", title = "",content = "", isChecked =false )
        }
    }
}