package edu.uit.o21.note_task

data class NoteUiState (
    val id: String = "",
    val title: String = "",
    val content: String =""
)
data class NoteListUiState(
    val list_notes: List<Note> = listOf()
)
data class TaskUiState (
    val id: String = "",
    val title:String = "",
    val content: String = "",
    val isChecked: Boolean = false
)
data class TaskListUiState(
    val list_tasks: List<Task> = listOf()
)
