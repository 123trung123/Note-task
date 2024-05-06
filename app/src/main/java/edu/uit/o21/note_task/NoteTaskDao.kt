package edu.uit.o21.note_task

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteTaskDao {
    @Insert
    suspend fun insertNote(note: Note)

    @Insert
    suspend fun insertTask(task: Task)

    @Query("SELECT * FROM Note")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM Task")
    fun getAllTasks(): Flow<List<Task>>
}