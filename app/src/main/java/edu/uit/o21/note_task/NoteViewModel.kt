package edu.uit.o21.note_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(private val noteDao: NoteTaskDao) : ViewModel() {
    private val _state: MutableStateFlow<NoteUiState> = MutableStateFlow(NoteUiState())
    val state: StateFlow<NoteUiState> = _state.asStateFlow()

    fun setTitle(title: String) {
        _state.update {
            it.copy(title = title)
        }
    }

    fun setContent(content: String) {
        _state.update {
            it.copy(content = content)
        }
    }

    fun insertNote() {
        viewModelScope.launch {
            val note = Note(_state.value.title, _state.value.content)
            noteDao.insert(note)
        }
        _state.update {
            it.copy(title = "", content = "")
        }
    }
}
