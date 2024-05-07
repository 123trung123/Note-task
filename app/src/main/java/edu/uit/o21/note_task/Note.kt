package edu.uit.o21.note_task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Note(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String
)
