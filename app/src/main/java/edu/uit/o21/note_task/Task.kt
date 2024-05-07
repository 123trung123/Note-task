package edu.uit.o21.note_task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Task(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String,
    val isChecked: Boolean = false
)