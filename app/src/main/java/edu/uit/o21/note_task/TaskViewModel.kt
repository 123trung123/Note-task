//package edu.uit.o21.note_task
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//
//class TaskViewModel(val dao: NoteTaskDao) : ViewModel() {
//    private val _state: MutableStateFlow<TaskUiState> = MutableStateFlow(TaskUiState())
//    val state: StateFlow<TaskUiState> = _state.asStateFlow()
//    fun setId(id: String) {
//        _state.update {
//            it.copy( id = id)
//        }
//    }
//    fun setTitle(title: String) {
//        _state.update {
//            it.copy(title = title)
//        }
//    }
//
//    fun setcontent(content: String) {
//        _state.update {
//            it.copy(content= content)
//        }
//    }
//
//    fun insertTask() {
//        viewModelScope.launch {
//            val task = Task(
//                id = _state.value.id,
//                title = _state.value.title,
//                content = _state.value.content,
//            )
//            dao.insertTask(task)
//        }
//        _state.value = TaskUiState()
//    }
//    fun deleteTask() {
//        viewModelScope.launch {
//            val task = Task(
//                id = _state.value.id,
//                title = _state.value.title,
//                content = _state.value.content,
//            )
//            dao.deleteTask(task)
//        }
//        _state.value = TaskUiState()
//    }
//}
package edu.uit.o21.note_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(val dao: NoteTaskDao) : ViewModel() {
    private val _state: MutableStateFlow<TaskUiState> = MutableStateFlow(TaskUiState())
    val state: StateFlow<TaskUiState> = _state.asStateFlow()

//    fun setId(id: String) {
//        _state.update {
//            it.copy(id = id)
//        }
//    }

    fun setTitle(title: String) {
        _state.update {
            it.copy(title = title)
        }
    }

    fun setcontent(content: String) {
        _state.update {
            it.copy(content= content)
        }
    }

    fun setPriority(priority: Int) {
        _state.update {
            it.copy(priority = priority)
        }
    }


    fun setDone(done: Boolean) {
        _state.update {
            it.copy(done = done)
        }
    }

    fun insertTask() {
        viewModelScope.launch {
            val task = Task(
                title = _state.value.title,
                content = _state.value.content,
                priority = _state.value.priority,
                done = _state.value.done
            )
            dao.insertTask(task)
        }
        _state.value = TaskUiState()
    }

    fun deleteTask() {
        viewModelScope.launch {
            val task = Task(
                title = _state.value.title,
                content = _state.value.content,
                priority = _state.value.priority,
                done = _state.value.done
            )
            dao.deleteTask(task)
        }
        _state.value = TaskUiState()
    }

}