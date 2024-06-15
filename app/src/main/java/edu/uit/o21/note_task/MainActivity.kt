@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                ) {
                    HomeScreen()
                }
            }
        }
    }
}
@Composable
fun StyledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(180.dp)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF0277BD))
    ) {
        Text(text = text, color = Color.White)
    }
}
@Composable
fun ContentButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(130.dp)
            .height(50.dp)
            .padding(1.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF0277BD))
    ) {
        Text(text = text, color = Color.White)
    }
}
@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               navHostController: NavHostController = rememberNavController()

) {
    Scaffold(
      topBar = {
           Row (modifier = Modifier
               .background(Color.White),
               verticalAlignment = Alignment.CenterVertically){
                TopAppBar(modifier = Modifier
                    .weight(1f)
                    .background(Color.Gray),
                    title = { Text(text = "QUICK NOTE & TASK", fontStyle = FontStyle.Italic, fontFamily = FontFamily.Serif, fontWeight = FontWeight.ExtraBold) }
                )
                 AsyncImage(
                     modifier = Modifier
                         .size(95.dp)
                         .padding(16.dp)
                         .background(Color.DarkGray),
                     contentDescription = null,
                     contentScale = ContentScale.Crop,
                     model = "https://png.pngtree.com/png-vector/20190324/ourmid/pngtree-vector-notes-icon-png-image_862518.jpg",
                 ) } },
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
            .background(Color(0xFFE3F2FD))
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(modifier = Modifier
            .fillMaxWidth(),
            model = "https://assets-global.website-files.com/5f7ece8a7da656e8a25402bc/631f32ee984371cb97df4ce2_How%20to%20take%20notes%20from%20a%20textbook-p-800.png",
            contentDescription = null,
        )
        Row {
            StyledButton(onClick = toNoteDetail, modifier = Modifier,
                text = "Note"
            )
            Spacer(modifier = Modifier.padding(15.dp))
            StyledButton(onClick = toTaskDetail, modifier = Modifier,
                text = "Task"
            )
        }
    }
}
@Composable
fun Header(text: String = "") {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color(0xFF90CAF9))
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = text,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily =  FontFamily.Serif
        )
    }
}
//
//@Composable
//fun NoteDetail(
//    modifier: Modifier = Modifier,
//    onClickBack: () -> Unit,
//    toNoteList: () -> Unit,
//    toTaskDetail: () -> Unit,
//    noteViewModel: NoteViewModel = viewModel(factory = AppViewModelNt.Factory)
//) {
//    val state by noteViewModel.state.collectAsState()
//    Column(
//        modifier = Modifier
//            .background(Color(0xFFE3F2FD))
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Header(text = "NOTE DETAIL")
//        Spacer(modifier = Modifier.height(4.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            ContentButton(onClick = onClickBack,
//                text = "Back")
//            ContentButton(onClick = toNoteList,
//                text = "View Notes"
//            )
//            ContentButton(onClick = toTaskDetail ,
//                text = "to Tasks"
//            )
//        }
//        Spacer(modifier = Modifier.height(4.dp))
//        OutlinedTextField(
//            value = state.title,
//            onValueChange = { noteViewModel.setTitle(it) },
//            label = { Text(text = "Title") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        OutlinedTextField(
//            value = state.content,
//            onValueChange = { noteViewModel.setcontent(it) },
//            label = { Text(text = "Content") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = { noteViewModel.insertNote() },
//            modifier = Modifier
//                .align(Alignment.End)
//                .width(130.dp)
//                .height(50.dp)
//                .padding(1.dp),
//            shape = RoundedCornerShape(8.dp),
//            colors = ButtonDefaults.buttonColors(Color.Black)
//        ) {
//            Text(text = "Add")
//        }
//    }
//}

//@Composable
//fun TaskDetail(
//    modifier: Modifier = Modifier,
//    onClickBack: () -> Unit,
//    toTaskList: () -> Unit,
//    toNoteDetail: () -> Unit,
//    taskViewModel: TaskViewModel = viewModel(factory = AppViewModelNt.Factory)
//) {
//    val state by taskViewModel.state.collectAsState()
//    Column(
//        modifier = Modifier
//            .background(Color(0xFFE3F2FD))
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Header(text = "TASK DETAIL")
//        Spacer(modifier = Modifier.height(4.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            ContentButton(onClick = onClickBack,
//                text = "Back"
//            )
//            ContentButton(onClick = toTaskList,
//                text = "View Tasks"
//            )
//            ContentButton(onClick = toNoteDetail,
//                text = "to Notes"
//            )
//        }
//        Spacer(modifier = Modifier.height(4.dp))
//        OutlinedTextField(
//            value = state.title,
//            onValueChange = { taskViewModel.setTitle(it) },
//            label = { Text(text = "Title") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//        OutlinedTextField(
//            value = state.content,
//            onValueChange = { taskViewModel.setcontent(it) },
//            label = { Text(text = "Content") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//        OutlinedTextField(
//            value = state.priority.toString(),
//            onValueChange = { taskViewModel.setPriority(it.toIntOrNull() ?: 0) },
//            label = { Text(text = "Priority") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = { taskViewModel.insertTask() },
//            modifier = Modifier
//                .align(Alignment.End)
//                .width(130.dp)
//                .height(50.dp)
//                .padding(1.dp),
//            shape = RoundedCornerShape(8.dp),
//            colors = ButtonDefaults.buttonColors(Color.Black)
//        ) {
//            Text(text = "Add", color = Color.White)
//        }
//    }
//}


//@Composable
//fun NoteList(
//    onClickBack: () -> Unit,
//    toTheMain: () -> Unit,
//    noteViewModel: NoteListViewModel = viewModel(factory = AppViewModelNt.Factory)
//) {
//    val state by noteViewModel.state.collectAsState()
//    val currentlyEditingNoteId = remember { mutableStateOf<Int?>(null) }
//    val editingTitle = remember { mutableStateOf("") }
//    val editingContent = remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .background(Color(0xFFE3F2FD))
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Header(text = "NOTE LIST")
//        Spacer(modifier = Modifier.height(4.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            ContentButton(onClick = onClickBack, text = "Back")
//            ContentButton(onClick = toTheMain, text = "Home")
//        }
//        LazyColumn {
//            items(items = state.list_notes, key = { it.id }) { note ->
//                val dismissState = rememberDismissState(
//                    confirmValueChange = {
//                        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
//                            noteViewModel.deleteNoteById(note.id)
//                        }
//                        true
//                    }
//                )
//                SwipeToDismiss(
//                    state = dismissState,
//                    background = {
//                        val color = when (dismissState.dismissDirection) {
//                            DismissDirection.StartToEnd, DismissDirection.EndToStart -> Color.Red
//                            null -> Color.Transparent
//                        }
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(color)
//                                .padding(horizontal = 20.dp),
//                            contentAlignment = Alignment.CenterStart
//                        ) {
//                            Text("Deleting", color = Color(0xFFE3F2FD))
//                        }
//                    },
//                    dismissContent = {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 8.dp)
//                                .border(1.dp, Color.Black),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Column {
//                                if (currentlyEditingNoteId.value == note.id) {
//                                    OutlinedTextField(
//                                        value = editingTitle.value,
//                                        onValueChange = { editingTitle.value = it },
//                                        modifier = Modifier.padding(10.dp)
//                                    )
//                                    OutlinedTextField(
//                                        value = editingContent.value,
//                                        onValueChange = { editingContent.value = it },
//                                        modifier = Modifier.padding(10.dp)
//                                    )
//                                    Button(modifier = Modifier
//                                        .width(110.dp)
//                                        .height(40.dp)
//                                        .padding(1.dp),
//                                        shape = RoundedCornerShape(8.dp),
//                                        colors = ButtonDefaults.buttonColors(Color.Black),
//                                        onClick = {
//                                        noteViewModel.updateNote(note.copy(title = editingTitle.value, content = editingContent.value))
//                                        currentlyEditingNoteId.value = null
//                                    }) {
//                                        Text("Save")
//                                    }
//                                } else {
//                                    Text(
//                                        text = note.title,
//                                        fontSize = 24.sp,
//                                        fontWeight = FontWeight.Bold,
//                                        modifier = Modifier.padding(10.dp)
//                                    )
//                                    Text(
//                                        text = note.content,
//                                        fontSize = 21.sp,
//                                        modifier = Modifier.padding(10.dp)
//                                    )
//                                    Button(
//                                        modifier = Modifier
//                                            .width(110.dp)
//                                            .height(40.dp)
//                                            .padding(1.dp),
//                                        shape = RoundedCornerShape(8.dp),
//                                        colors = ButtonDefaults.buttonColors(Color.Black),
//                                        onClick = {
//                                        currentlyEditingNoteId.value = note.id
//                                        editingTitle.value = note.title
//                                        editingContent.value = note.content
//                                    }) {
//                                        Text("Edit")
//                                    }
//                                }
//                            }
//                        }
//                    }
//                )
//            }
//        }
//    }
//}

//@Composable
//fun TaskList(
//    onClickBack: () -> Unit,
//    toTheMain: () -> Unit,
//    taskListViewModel: TaskListViewModel = viewModel(factory = AppViewModelNt.Factory)
//) {
//    val state by taskListViewModel.state.collectAsState()
//    val sortByPriority = remember { mutableStateOf(false) }
//    val hideCompleted = remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .background(Color(0xFFE3F2FD))
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Header(text = "TASK LIST")
//        Spacer(modifier = Modifier.height(4.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            ContentButton(onClick = onClickBack,
//                text = "Back"
//                )
//            ContentButton(onClick = toTheMain,
//                text = "Home"
//                )
//        }
//        Spacer(modifier = Modifier.height(20.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "Currently sorted by:", fontSize = 20.sp)
//            ContentButton(onClick = { sortByPriority.value = !sortByPriority.value },
//                text = if (sortByPriority.value) "Priority" else "Newest"
//                )
//        }
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "Show Completed:", fontSize = 20.sp)
//            ContentButton(onClick = { hideCompleted.value = !hideCompleted.value },
//                text = if (hideCompleted.value) "Hide" else "Show"
//            )
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        LazyColumn (
//            state = rememberLazyListState()
//        ) {
//            val filteredTasks = state.list_tasks
//                .filter { !hideCompleted.value || !it.done }
//                .let { tasks ->
//                    if (sortByPriority.value) {
//                        tasks.sortedByDescending { it.priority }
//                    } else {
//                        tasks.sortedByDescending { it.id }
//                    }
//                }
//
//            items(items = filteredTasks, key = { it.id }) { task ->
//                val dismissState = rememberDismissState(
//                    confirmValueChange = {
//                        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
//                            taskListViewModel.deleteTaskById(task.id)
//                        }
//                        true
//                    }
//                )
//                SwipeToDismiss(
//                    state = dismissState,
//                    background = {
//                        val color = when (dismissState.dismissDirection) {
//                            DismissDirection.StartToEnd -> Color.Red
//                            DismissDirection.EndToStart -> Color.Red
//                            null -> Color.Transparent
//                        }
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(color)
//                                .padding(horizontal = 20.dp),
//                            contentAlignment = Alignment.CenterStart
//                        ) {
//                            Text("Deleting", color = Color(0xFFE3F2FD))
//                        }
//                    },
//                    dismissContent = {
//                        Column(
//                            modifier = Modifier
//                                .border(1.dp, Color.Black)
//                                .fillMaxWidth()
//                        ) {
//                            Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier
//                                .background(color = Color(0xFF90CAF9))
//                                .fillMaxWidth()){
//                                Text(
//                                    text = "Priority: ${task.priority}",
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 20.sp,
//                                    modifier = Modifier.padding(10.dp)
//                                )
//                                Checkbox(
//                                    checked = task.done,
//                                    onCheckedChange = { isChecked ->
//                                        taskListViewModel.updateTask(task.copy(done = isChecked))
//                                    },
//                                    modifier = Modifier.padding(10.dp)
//                                )
//                                Text(fontWeight = FontWeight.Bold,fontSize = 20.sp,text = "Done")
//                            }
//                            Text(
//                                text = "Title: ${task.title}",
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 20.sp,
//                                modifier = Modifier.padding(10.dp)
//                            )
//                            Text(text = task.content, fontSize = 15.sp, modifier = Modifier.padding(10.dp))
//                        }
//                    }
//                )
//            }
//        }
//    }
//}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NotetaskTheme {
        HomeScreen()
    }
}
