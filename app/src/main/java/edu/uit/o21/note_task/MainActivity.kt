package edu.uit.o21.note_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    Column {
        NoteDetail()
//        TaskDetail()
//        NoteList()
//        TaskList()
    }
}

@Composable
fun NoteDetail(
    modifier: Modifier = Modifier,
    noteViewModel: NoteViewModel = viewModel(factory = AppViewModelNt.NoteFactory)
) {
    val state by noteViewModel.state.collectAsState()
    Column {
        Header(text = "NOTE DETAIL")
        OutlinedTextField(
            value = state.id,
            onValueChange = { noteViewModel.setTitle(it) },
            label = { Text(text = "Title") }
        )
        OutlinedTextField(
            value = state.title,
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

//@Composable
//fun TaskDetail(
//    modifier: Modifier = Modifier,
//    taskViewModel: TaskViewModel = viewModel(factory = AppViewModelNt.TaskFactory)
//) {
//    val state by taskViewModel.state.collectAsState()
//    Column {
//        Header(text = "TASK DETAIL")
//        OutlinedTextField(
//            value = state.title,
//            onValueChange = { taskViewModel.setTitle(it) },
//            label = { Text(text = "Title") }
//        )
//        OutlinedTextField(
//            value = state.content,
//            onValueChange = { taskViewModel.setcontent(it) },
//            label = { Text(text = "Content") } // Change label from "Description" to "Content"
//        )
//        Button(onClick = { taskViewModel.insertTask() }) {
//            Text(text = "Add Task")
//        }
//    }
//}

//@Composable
//fun NoteList() {
//    Column {
//        Header(text = "NOTE LIST")
//    }
//}

//@Composable
//fun TaskList() {
//    Column {
//        Header(text = "TASK LIST")
//    }
//}

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


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NotetaskTheme {
        HomeScreen()
    }
}
