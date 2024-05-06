package edu.uit.o21.note_task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class NoteUiState {
    val title: MutableState<String> = mutableStateOf("")
    val content: MutableState<String> = mutableStateOf("")
}

class TaskUiState {
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val isChecked: MutableState<Boolean> = mutableStateOf(false)
}