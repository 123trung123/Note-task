package edu.uit.o21.note_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.uit.o21.note_task.ui.theme.NotetaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotetaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val noteListViewModel: NoteListViewModel = viewModel(factory = AppViewModelNt.Factory)
    val taskListViewModel: TaskListViewModel = viewModel(factory = AppViewModelNt.Factory)
    val noteListState by noteListViewModel.state.collectAsState()
    val taskListState by taskListViewModel.state.collectAsState()
    Column {
        NoteDetail()
        TaskDetail()
        NoteList(list_notes = noteListState.list_notes)
        TaskList(list_tasks = taskListState.list_tasks)
    }
}

@Composable
fun NoteDetail(
    modifier: Modifier = Modifier,
    noteViewModel: NoteViewModel = viewModel(factory = AppViewModelNt.Factory)
) {
    val state by noteViewModel.state.collectAsState()
    Column {
        Header(text = "NOTE DETAIL")
        OutlinedTextField(
            value = state.id,
            onValueChange = { noteViewModel.setId(it) },
            label = { Text(text = "Id") }
        )
        OutlinedTextField(
            value = state.title,
            onValueChange = { noteViewModel.setTitle(it) },
            label = { Text(text = "Title") }
        )
        OutlinedTextField(
            value = state.content,
            onValueChange = { noteViewModel.setcontent(it) },
            label = { Text(text = "Content") }
        )
        Row {
            Button(onClick = {noteViewModel.insertNote()}) {
                Text(text = "Add")
            }
            Button(onClick = {}) {
                Text(text = "Delete All")
            }
        }
    }
}

@Composable
fun TaskDetail(
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel = viewModel(factory = AppViewModelNt.Factory)
) {
    val state by taskViewModel.state.collectAsState()
    Column {
        Header(text = "TASK DETAIL")
        OutlinedTextField(
            value = state.id,
            onValueChange = { taskViewModel.setId(it) },
            label = { Text(text = "Id") }
        )
        OutlinedTextField(
            value = state.title,
            onValueChange = { taskViewModel.setTitle(it) },
            label = { Text(text = "Title") }
        )
        OutlinedTextField(
            value = state.content,
            onValueChange = { taskViewModel.setcontent(it) },
            label = { Text(text = "Content") }
        )
        Row {
            Button(onClick = {taskViewModel.insertTask()}) {
                Text(text = "Add")
            }
            Button(onClick = {}) {
                Text(text = "Delete All")
            }
        }
    }
}
@Composable
fun Header(text: String = "") {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(
            text = text,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)
    }
}
//@Composable
//fun NoteList(viewModel: NoteListViewModel = viewModel(factory = AppViewModelNt.Factory)
//) {
//    val state by viewModel.state.collectAsState()
//    Header(text = "NOTE LIST")
//    LazyColumn {
//        items(state.list_notes,key = {it.id}) {
//            Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
//                ) {
//                Text(text = it.id, fontSize = 18.sp)
//                Text(text = it.title, fontSize = 18.sp)
//                Text(text = it.content, fontSize = 18.sp)
//            }
//        }
//    }
//}
//@Composable
//fun NoteList(
//    list_notes: List<Note>
//) {
//    Column {
//        Header(text = "NOTE LIST")
//        for (note in list_notes) {
//            Row {
//                Text(text = note.id, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
//                Spacer(Modifier.padding(20.dp))
//                Text(text = note.title,fontSize = 25.sp, modifier = Modifier.padding(10.dp))
//                Spacer(Modifier.padding(20.dp))
//                Text(text = note.content,fontSize = 25.sp, modifier = Modifier.padding(10.dp))
//            }
//        }
//    }
//}
@Composable
fun NoteList(
    list_notes: List<Note>
) {
    Column {
        Header(text = "NOTE LIST")
        LazyColumn {
            items(list_notes) { note ->
                NoteItem(note = note)
            }
        }
    }
}
@Composable
fun NoteItem(note: Note) {
    Row {
        Text(text = note.id, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
        Spacer(Modifier.padding(20.dp))
        Text(text = note.title, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
        Spacer(Modifier.padding(20.dp))
        Text(text = note.content, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
    }
}
@Composable
fun TaskList(
    list_tasks: List<Task>
) {
    Column {
        Header(text = "TASK LIST")
        LazyColumn {
            items(list_tasks) { task ->
                TaskItem(task = task)
            }
        }
    }
}
@Composable
fun TaskItem(task: Task) {
    Row {
        Text(text = task.id, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
        Spacer(Modifier.padding(20.dp))
        Text(text = task.title, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
        Spacer(Modifier.padding(20.dp))
        Text(text = task.content, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
    }
}




@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NotetaskTheme {
        HomeScreen()
    }
}
