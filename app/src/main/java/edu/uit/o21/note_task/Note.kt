package edu.uit.o21.note_task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Note(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val title: String,
    val content: String
)
