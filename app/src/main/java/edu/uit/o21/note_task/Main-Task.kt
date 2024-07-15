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

//Task Detail shows all of the detail of the tasks where it is added into the list
// It includes a header, input fields for task properties, and a save button.
@Composable
fun TaskDetail(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,  // Action to navigate back to the previous screen
    toTaskList: () -> Unit,   // Action to navigate to the task list
//    toNoteDetail: () -> Unit,
    taskViewModel: TaskViewModel = viewModel(factory = AppViewModelNt.Factory)
) {
    val state by taskViewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .background(Color(0xFFF8F8F8))
            .fillMaxSize()
    ) {
        // Row for the header section with back button and title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(Color(0xFF6074F9)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Button to go back to the previous screen
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
        // Row for task and list buttons
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
                Text(text = "Task", fontSize = 22.sp, color = Color.White,textDecoration = TextDecoration.Underline)
            }
            TopNoteTaskButton(
                onClick = toTaskList, text = "List"
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        // Input field for the task title
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
        // Button to save the task
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
//Task List shows the whole list of all tasks
@Composable
fun TaskList(
    onClickBack: () -> Unit,  // Action to navigate back to the previous screen
    toTheMain: () -> Unit,    // Action to navigate to the main screen
    taskListViewModel: TaskListViewModel = viewModel(factory = AppViewModelNt.Factory)
) {
    val state by taskListViewModel.state.collectAsState()
    val sortByPriority = remember { mutableStateOf(false) }  // State to toggle sorting by priority
    val hideCompleted = remember { mutableStateOf(false) }   // State to toggle hiding completed tasks

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        // Row for the header section with back button and title
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
        Text(
            text = "Tasks",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.padding(horizontal = 15.dp).padding(vertical = 15.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        // Row for sorting tasks by priority or newest, changes the priority
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
        // Row for hiding and showing and hiding checked tasks, filtering out the finished tasks
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
        // LazyColumn to display the list of tasks
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
            // State for swipe-to-dismiss functionality
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
                //Swipe content to delete swipe
                //sometime the code bugs out showing API is used for the future just reload the code.
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
                    //Code to delete unused task or task that has been done and user no longer needs
                    dismissContent = {
                        Spacer(modifier = Modifier.height(16.dp))
                        //changes color based on normal  or deleting
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
                                        //Update task status when checkbox is checked/unchecked
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
                            // Text for task content
                            Text(text = task.content,textDecoration = textDecoration, fontSize = 21.sp, modifier = Modifier.padding(10.dp))
                        }
                    }
                )
            }
        }
    }
}
