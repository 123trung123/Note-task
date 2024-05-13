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
//    val noteListViewModel: NoteListViewModel = viewModel(factory = AppViewModelNt.Factory)
    val taskListViewModel: TaskListViewModel = viewModel(factory = AppViewModelNt.Factory)
//    val noteListState by noteListViewModel.state.collectAsState()
    val taskListState by taskListViewModel.state.collectAsState()
    Column {
        NoteDetail()
        TaskDetail()
        NoteList()
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
@Composable
fun NoteList(
    noteViewModel: NoteListViewModel =viewModel(factory = AppViewModelNt.Factory)
) {
    val state by noteViewModel.state.collectAsState()
    Header(text = "NOTE LIST")
    LazyColumn {
            items(items = state.list_notes, key={it.id}) {
                Row {
                    Text(text = it.id, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
                    Spacer(Modifier.padding(20.dp))
                    Text(text = it.title, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
                    Spacer(Modifier.padding(20.dp))
                    Text(text = it.content, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
                }
            }
    }
}

@Composable
fun TaskList(
    taskListViewModel: TaskListViewModel = viewModel(factory = AppViewModelNt.Factory)
) {
    val state by taskListViewModel.state.collectAsState()
    Header(text = "NOTE LIST")
    LazyColumn {
        items(items = state.list_tasks, key={it.id}) {
            Row {
                Text(text = it.id, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
                Spacer(Modifier.padding(20.dp))
                Text(text = it.title, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
                Spacer(Modifier.padding(20.dp))
                Text(text = it.content, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NotetaskTheme {
        HomeScreen()
    }
}
