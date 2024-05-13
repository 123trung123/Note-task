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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
//
//@Composable
//fun HomeScreen() {
//    Column {
//        NoteDetail()
//        TaskDetail()
//        NoteList()
//        TaskList()
//    }
//}
@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = "TheMain"
    ) {
        composable(route = "TheMain") {
            TheMain(
                toNoteDetail = { navHostController.navigate("NoteDetail") },
                toTaskDetail = { navHostController.navigate("TaskDetail") },
            )
        }
        composable(route = "NoteDetail") {
            NoteDetail(
                onClickBack = { navHostController.navigateUp() },
                toNoteList = { navHostController.navigate("NoteList") },
                toTaskDetail = { navHostController.navigate("TaskDetail") }
            )
        }
        composable(route = "NoteList") {
            NoteList(
                onClickBack = { navHostController.navigateUp() },
                toTheMain = { navHostController.navigate("TheMain") }
            )
        }
        composable(route = "TaskDetail") {
            TaskDetail(
                onClickBack = { navHostController.navigateUp() },
                toTaskList = { navHostController.navigate("TaskList") },
                toNoteDetail = { navHostController.navigate("TheMain") }
            )
        }

        composable(route = "TaskList") {
            TaskList(
                onClickBack = { navHostController.navigateUp() },
                toTheMain = { navHostController.navigate("TheMain") }
            )
        }
    }
}

@Composable
fun TheMain(
    modifier: Modifier = Modifier,
    toNoteDetail: () -> Unit,
    toTaskDetail: () -> Unit,
){
    Row {
        Button(onClick = toNoteDetail) {
            Text(text = "Note")
        }
        Button(onClick = toTaskDetail) {
            Text(text = "Task")
        }
    }
}
@Composable
fun NoteDetail(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    toNoteList: () -> Unit,
    toTaskDetail: () -> Unit,
    noteViewModel: NoteViewModel = viewModel(factory = AppViewModelNt.Factory)
) {
    val state by noteViewModel.state.collectAsState()
    Column {
        Header(text = "NOTE DETAIL")
        Row {
            Button(onClick = onClickBack) {
                Text(text = "Back")
            }
            Button(onClick = toNoteList) {
                Text(text = "View Notes")
            }
            Button(onClick = toTaskDetail) {
                Text(text = "Tasks")
            }
        }
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
            Button(onClick = {noteViewModel.deleteNote()}) {
                Text(text = "Delete All")
            }
        }
    }
}

@Composable
fun TaskDetail(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    toTaskList: () -> Unit,
    toNoteDetail: () -> Unit,
    taskViewModel: TaskViewModel = viewModel(factory = AppViewModelNt.Factory)
) {
    val state by taskViewModel.state.collectAsState()
    Column {
        Header(text = "TASK DETAIL")
        Row {
            Button(onClick = onClickBack) {
                Text(text = "Back")
            }
            Button(onClick = toTaskList) {
                Text(text = "View Tasks")
            }
            Button(onClick = toNoteDetail) {
                Text(text = "Notes")
            }
        }
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
            Button(onClick = {taskViewModel.deleteTask()}) {
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
    onClickBack: () -> Unit,
    toTheMain: () -> Unit,
    noteViewModel: NoteListViewModel =viewModel(factory = AppViewModelNt.Factory)
) {
    val state by noteViewModel.state.collectAsState()
    Header(text = "NOTE LIST")
    Row {
        Button(onClick = onClickBack) {
            Text(text = "Back")
        }
        Button(onClick = toTheMain) {
            Text(text = "Home")
        }
    }
    LazyColumn {
            items(items = state.list_notes, key={it.id}) {
                Row {
                    Text(text = it.id, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
                    Spacer(Modifier.padding(20.dp))
                    Text(text = it.title, fontSize = 25.sp, modifier = Modifier.padding(10.dp))
                    Spacer(Modifier.padding(20.dp))
                    Text(text = it.content, fontSize = 25.sp, modifier = Modifier.padding(10.dp)) }
            }
    }
}

@Composable
fun TaskList(
    onClickBack: () -> Unit,
    toTheMain: () -> Unit,
    taskListViewModel: TaskListViewModel = viewModel(factory = AppViewModelNt.Factory)
) {
    val state by taskListViewModel.state.collectAsState()
    Header(text = "NOTE LIST")
    Row {
        Button(onClick = onClickBack) {
            Text(text = "Back")
        }
        Button(onClick = toTheMain) {
            Text(text = "Home")
        }
    }
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
