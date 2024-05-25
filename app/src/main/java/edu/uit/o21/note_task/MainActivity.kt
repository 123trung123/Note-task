@file:OptIn(ExperimentalMaterial3Api::class)

package edu.uit.o21.note_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImage
import edu.uit.o21.note_task.ui.theme.NotetaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotetaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               navHostController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            Column{
                TopAppBar(
                    title = { Text(text = "NOTE TASK APP") }
                )
            }
        },

        content = { innerPadding ->
            NavHost(
                navController = navHostController,
                startDestination = "TheMain",
                modifier = Modifier.padding(innerPadding)

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
                        toNoteDetail = { navHostController.navigate("NoteDetail") }
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
    )
}

@Composable
fun TheMain(
    modifier: Modifier = Modifier,
    toNoteDetail: () -> Unit,
    toTaskDetail: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        AsyncImage(
            model = "https://imgflip.com/s/meme/Cute-Cat.jpg",
            contentDescription = null,
        )
        Button(onClick = toNoteDetail, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {
            Text(text = "Note")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = toTaskDetail, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header(text = "NOTE DETAIL")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onClickBack) {
                Text(text = "Back")
            }
            Button(onClick = toNoteList) {
                Text(text = "View Notes")
            }
            Button(onClick = toTaskDetail) {
                Text(text = "to Tasks")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.title,
            onValueChange = { noteViewModel.setTitle(it) },
            label = { Text(text = "Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.content,
            onValueChange = { noteViewModel.setcontent(it) },
            label = { Text(text = "Content") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { noteViewModel.insertNote() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Add")
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header(text = "TASK DETAIL")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onClickBack) {
                Text(text = "Back")
            }
            Button(onClick = toTaskList) {
                Text(text = "View Tasks")
            }
            Button(onClick = toNoteDetail) {
                Text(text = "to Notes")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.title,
            onValueChange = { taskViewModel.setTitle(it) },
            label = { Text(text = "Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.content,
            onValueChange = { taskViewModel.setcontent(it) },
            label = { Text(text = "Content") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.priority.toString(),
            onValueChange = { taskViewModel.setPriority(it.toIntOrNull() ?: 0) },
            label = { Text(text = "Priority") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = state.done,
                onCheckedChange = { taskViewModel.setDone(it) }
            )
            Text(text = "Done")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { taskViewModel.insertTask() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Add")
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
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun NoteList(
    onClickBack: () -> Unit,
    toTheMain: () -> Unit,
    noteViewModel: NoteListViewModel = viewModel(factory = AppViewModelNt.Factory)
) {
    val state by noteViewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header(text = "NOTE LIST")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onClickBack) {
                Text(text = "Back")
            }
            Button(onClick = toTheMain) {
                Text(text = "Home")
            }
        }
        LazyColumn {
            items(items = state.list_notes, key = { it.id }) { note ->
                val dismissState = rememberDismissState(
                    confirmValueChange = {
                        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart){
                            noteViewModel.deleteNoteById(note.id)
                        }
                        true
                    }
                )
                SwipeToDismiss(state = dismissState,
                    background = {
                    val color = when (dismissState.dismissDirection) {
                        DismissDirection.StartToEnd -> Color.Red
                        DismissDirection.EndToStart -> Color.Red
                        null -> Color.Transparent
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text("Deleting", color = Color.White)
                    }
                },
                    dismissContent = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
//                        Text(
//                            text = note.id.toString(),
//                            fontSize = 25.sp,
//                            modifier = Modifier.padding(10.dp)
//                        )
                        Text(
                            text = note.title,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                        Text(
                            text = note.content,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            })}
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
    val sortByPriority = remember { mutableStateOf(false) }
    val hideCompleted = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header(text = "TASK LIST")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onClickBack) {
                Text(text = "Back")
            }
            Button(onClick = toTheMain) {
                Text(text = "Home")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Currently sorted by:", fontSize = 20.sp)
            Button(onClick = { sortByPriority.value = !sortByPriority.value }) {
                Text(text = if (sortByPriority.value) "Priority" else "Newest")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Show Completed:", fontSize = 20.sp)
            Button(onClick = { hideCompleted.value = !hideCompleted.value }) {
                Text(text = if (hideCompleted.value) "Hide" else "Show")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn (
            state = rememberLazyListState()
        ) {
            val filteredTasks = state.list_tasks
                .filter { !hideCompleted.value || !it.done }
                .let { tasks ->
                    if (sortByPriority.value) {
                        tasks.sortedByDescending { it.priority }
                    } else {
                        tasks.sortedByDescending { it.id }
                    }
                }

            items(items = filteredTasks, key = { it.id }) { task ->
                val dismissState = rememberDismissState(
                    confirmValueChange = {
                        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                            taskListViewModel.deleteTaskById(task.id)
                        }
                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color = when (dismissState.dismissDirection) {
                            DismissDirection.StartToEnd -> Color.Red
                            DismissDirection.EndToStart -> Color.Red
                            null -> Color.Transparent
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text("Deleting", color = Color.White)
                        }
                    },
                    dismissContent = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Row {
                                Text(
                                    text = "Title: ${task.title}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Text(
                                    text = "Priority: ${task.priority}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                            Text(text = task.content, fontSize = 15.sp, modifier = Modifier.padding(10.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = task.done,
                                    onCheckedChange = { isChecked ->
                                        taskListViewModel.updateTask(task.copy(done = isChecked))
                                    },
                                    modifier = Modifier.padding(10.dp)
                                )
                                Text(text = "Done")
                            }
                        }
                    }
                )
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
