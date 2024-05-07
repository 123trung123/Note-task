package edu.uit.o21.note_task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
data class NoteUiState (
    val id: String = "",
    val title: String = "",
    val content: String =""
)
data class TaskUiState (
    val id: String = "",
    val title:String = "",
    val content: String = "",
    val isChecked: Boolean = false
)