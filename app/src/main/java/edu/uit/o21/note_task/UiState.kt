package edu.uit.o21.note_task

data class NoteUiState (
    val id: Int = 0,
    val title: String = "",
    val content: String =""
)

data class NoteListUiState(
    val list_notes: List<Note> = listOf(),
    val title: String = "",
    val content: String =""
)
data class TaskUiState (
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val priority: Int = 0,
    val done: Boolean = false
)
data class TaskListUiState(
    val list_tasks: List<Task> = listOf()
)
