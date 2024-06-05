package edu.uit.o21.note_task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Note(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    var title: String,
    var content: String
){
    fun copy(id: Int = this.id, title: String = this.title, content: String = this.content): Note {
        return Note(id, title, content)
    }
}

