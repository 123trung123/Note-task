package edu.uit.o21.note_task

import androidx.room.Entity
import androidx.room.PrimaryKey
//entity class for task, representing a task in the application.
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val priority: Int,
    val done: Boolean
)