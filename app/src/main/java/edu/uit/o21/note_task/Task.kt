package edu.uit.o21.note_task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val title: String,
    val content: String,
    val isChecked: Boolean = false
)