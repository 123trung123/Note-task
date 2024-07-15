package edu.uit.o21.note_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
//View Model for the Note screen
class NoteViewModel(val dao: NoteTaskDao) : ViewModel() {
    private val _state: MutableStateFlow<NoteUiState> = MutableStateFlow(NoteUiState())
    val state: StateFlow<NoteUiState> = _state.asStateFlow()

    fun setTitle(title: String) {
        _state.value = _state.value.copy(title = title)
    }

    fun setcontent(content: String) {
        _state.value = _state.value.copy(content = content)
    }

    fun insertNote() {
        viewModelScope.launch {
            val note = Note(
                title = _state.value.title,
                content = _state.value.content
            )
            dao.insertNote(note)
        }
        _state.value = NoteUiState()
    }
    fun deleteNote() {
        viewModelScope.launch {
            val note = Note(
                title = _state.value.title,
                content = _state.value.content
            )
            dao.deleteNote(note)
        }
        _state.value = NoteUiState()
    }

}