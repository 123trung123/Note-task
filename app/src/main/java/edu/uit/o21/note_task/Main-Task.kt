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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


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
            .background(Color(0xFFE3F2FD))
            .fillMaxSize()
    ) {
        Header(text = "TASK DETAIL")
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ContentButton(
                onClick = onClickBack,
                text = "Back",
                icon = Icons.Default.ArrowBack
            )
            ContentButton(onClick = toTaskList,
                text = "Tasks",
                icon = Icons.Default.CheckCircle
            )
            ContentButton(onClick = toNoteDetail,
                text = "Notes",
                icon = Icons.Default.List
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = state.title,
            onValueChange = { taskViewModel.setTitle(it) },
            label = { Text(text = "Title") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = state.content,
            onValueChange = { taskViewModel.setcontent(it) },
            label = { Text(text = "Content") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = state.priority.toString(),
            onValueChange = { taskViewModel.setPriority(it.toIntOrNull() ?: 0) },
            label = { Text(text = "Priority") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = { taskViewModel.insertTask() },
            modifier = Modifier
                .align(Alignment.End)
                .width(90.dp)
                .height(90.dp)
                .padding(1.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF0277BD))
        ) {
            Text(text = "Add", color = Color.White)
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
            .background(Color(0xFFE3F2FD))
            .fillMaxSize()
    ) {
        Header(text = "TASK LIST")
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Currently sorted by:", fontSize = 20.sp)
            ContentButton(onClick = { sortByPriority.value = !sortByPriority.value },
                text = if (sortByPriority.value) "Priority" else "Newest"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Show Completed:", fontSize = 20.sp)
            ContentButton(onClick = { hideCompleted.value = !hideCompleted.value },
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
                                .background(color)
                                .padding(20.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text("Deleting",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE3F2FD))
                        }
                    },
                    dismissContent = {
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clip(shape = RoundedCornerShape(12.dp))
                            .background(Color(0xFFBBDEFB))
                            .fillMaxSize()
                        ) {
                            Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                .background(color = Color(0xFF90CAF9))
                                .fillMaxWidth()){
                                Text(
                                    text = task.title,
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
                                Checkbox(
                                    checked = task.done,
                                    onCheckedChange = { isChecked ->
                                        taskListViewModel.updateTask(task.copy(done = isChecked))
                                    },
                                    modifier = Modifier.padding(10.dp)
                                )
                                Text(fontWeight = FontWeight.Bold,fontSize = 20.sp,text ="")
                            }

                            Text(text = task.content, fontSize = 18.sp, modifier = Modifier.padding(10.dp))
                        }
                    }
                )
            }
        }
    }
}