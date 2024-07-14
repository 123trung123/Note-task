package edu.uit.o21.note_task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
//Dao for all the function within the application
@Dao
interface NoteTaskDao {
    @Insert
    suspend fun insertNote(note: Note)

    @Insert
    suspend fun insertTask(task: Task)
    @Delete
    suspend fun deleteNote(note: Note)

    @Delete
    suspend fun deleteTask(task: Task)
    @Update
    suspend fun updateNote(note: Note)
    @Update
    suspend fun updateTask(task: Task)
    @Query("DELETE FROM note WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Int)
    @Query("DELETE FROM task WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: Int)
    @Update
    suspend fun updateTaskStatus(task: Task)
    @Query("SELECT * FROM NOTE")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM TASK")
    fun getAllTasks(): Flow<List<Task>>
}