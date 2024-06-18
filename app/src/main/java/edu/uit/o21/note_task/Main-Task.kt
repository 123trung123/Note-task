@file:OptIn(ExperimentalMaterial3Api::class)
package edu.uit.o21.note_task

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun TaskDetail(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    toTaskList: () -> Unit,
//    toNoteDetail: () -> Unit,
    taskViewModel: TaskViewModel = viewModel(factory = AppViewModelNt.Factory)
) {
    val state by taskViewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .background(Color(0xFFF8F8F8))
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(Color(0xFF6074F9)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ContentButton(
                onClick = onClickBack,
                text = "Back",
                icon = Icons.Default.ArrowBack
            )
            Header(text = "TASK DETAIL")
            ContentButton(onClick = toTaskList,
                text = "",
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF6074F9)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(modifier = Modifier
                .width(205.dp)
                .height(60.dp)
                .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color(0xFF6074F9))
            ) {
                Text(text = "Task", fontSize = 22.sp, color = Color.Black,textDecoration = TextDecoration.Underline)
            }
            TopNoteTaskButton(
                onClick = toTaskList, text = "List"
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        OutlinedTextField(
            value = state.title,
            onValueChange = { taskViewModel.setTitle(it) },
            label = { Text(text = "Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = state.content,
            onValueChange = { taskViewModel.setcontent(it) },
            label = { Text(text = "Content") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = state.priority.toString(),
            onValueChange = { taskViewModel.setPriority(it.toIntOrNull() ?: 0) },
            label = { Text(text = "Priority") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = { taskViewModel.insertTask() },
            modifier = Modifier
                .align(Alignment.End)
                .padding(14.dp)
                .width(90.dp)
                .height(90.dp)
                .shadow(14.dp, shape = RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF6074F9))
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            Text(text = "", color = Color.White)
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
            .background(Color(0xFFF8F8F8))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(Color(0xFF6074F9)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ContentButton(
                onClick = onClickBack,
                text = "Back",
                icon = Icons.Default.ArrowBack
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Sorted by:", fontSize = 20.sp, modifier = Modifier.padding(horizontal = 16.dp))
            TaskButton(onClick = { sortByPriority.value = !sortByPriority.value },
                text = if (sortByPriority.value) "Priority" else "Newest"
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Completed:", fontSize = 20.sp, modifier = Modifier.padding(horizontal = 16.dp))
            TaskButton(onClick = { hideCompleted.value = !hideCompleted.value },
                text = if (hideCompleted.value) "Hide" else "Show"
            )
        }
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
                Spacer(modifier = Modifier.height(20.dp))
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
                                .padding(vertical = 4.dp)
                                .fillMaxSize()
                                .background(color)
                                .padding(20.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Icon",
                            tint = Color(0xFFE3F2FD),
                            modifier = Modifier.size(24.dp)
                        )
                            Text("",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE3F2FD))
                        }
                    },
                    dismissContent = {
                        Spacer(modifier = Modifier.height(16.dp))
                        val backgroundColor = if (task.done) Color(0xFFE4E4E4) else Color(0xFFFFFFFF)
                        val titleColor = if (task.done) Color(0xFFD2E7F8) else Color(0xFFD2E7F8)
                        val textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)
                            .padding(vertical = 4.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                            .background(backgroundColor)
                        ) {
                            Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                .background(titleColor)
                                .fillMaxWidth()){
                                Checkbox(
                                    checked = task.done,
                                    onCheckedChange = { isChecked ->
                                        taskListViewModel.updateTask(task.copy(done = isChecked))
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color(0xFF679FFF),
                                        uncheckedColor = Color(0xFF90CAF9)
                                    ),
                                    modifier = Modifier.padding(10.dp)
                                )
                                Text(
                                    text = task.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,
                                    textDecoration = textDecoration,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Text(
                                    text = "Priority: ${task.priority}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 21.sp,
                                    textDecoration = textDecoration,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                            Text(text = task.content,textDecoration = textDecoration, fontSize = 21.sp, modifier = Modifier.padding(10.dp))
                        }
                    }
                )
            }
        }
    }
}