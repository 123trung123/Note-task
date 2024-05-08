package edu.uit.o21.note_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(val dao: NoteTaskDao) : ViewModel() {
    private val _state: MutableStateFlow<NoteUiState> = MutableStateFlow(NoteUiState())
    val state: StateFlow<NoteUiState> = _state.asStateFlow()

    fun setId(id: String) {
//        _state.update {
//            it.copy(id = id)
//        }
        _state.value = _state.value.copy(id = id)
    }

    fun setTitle(title: String) {
//        _state.update {
//            it.copy(title = title)
//        }
        _state.value = _state.value.copy(title = title)
    }

    fun setcontent(content: String) {
//        _state.update {
//            it.copy(content = content)
//        }
        _state.value = _state.value.copy(content = content)
    }

    fun insertNote() {
        viewModelScope.launch {
            val note = Note(
                id = _state.value.id,
                title = _state.value.title,
                content = _state.value.content
            )
            dao.insertNote(note)
        }
        _state.value = NoteUiState()
    }
}