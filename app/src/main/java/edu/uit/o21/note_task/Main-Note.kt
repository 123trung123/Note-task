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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
fun NoteDetail(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    toNoteList: () -> Unit,
//    toTaskDetail: () -> Unit,
    noteViewModel: NoteViewModel = viewModel(factory = AppViewModelNt.Factory)
) {
    val state by noteViewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
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
            Header(text = "NOTE DETAIL")
            ContentButton(onClick = toNoteList,
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
                Text(text = "Note", fontSize = 22.sp, color = Color.White,textDecoration = TextDecoration.Underline)
            }
            TopNoteTaskButton(
                onClick = toNoteList, text = "List"
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        OutlinedTextField(
            value = state.title,
            onValueChange = { noteViewModel.setTitle(it) },
            label = { Text(text = "Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = state.content,
            onValueChange = { noteViewModel.setcontent(it) },
            label = { Text(text = "Content") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = { noteViewModel.insertNote() },
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
            Text(text = "")
        }
    }
}
@Composable
fun NoteList(
    onClickBack: () -> Unit,
    toTheMain: () -> Unit,
    noteViewModel: NoteListViewModel = viewModel(factory = AppViewModelNt.Factory)
) {
    val state by noteViewModel.state.collectAsState()
    val currentlyEditingNoteId = remember { mutableStateOf<Int?>(null) }
    val editingTitle = remember { mutableStateOf("") }
    val editingContent = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF6074F9)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ContentButton(
                onClick = onClickBack,
                text = "",
                icon = Icons.Default.ArrowBack
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Notes",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        LazyColumn {
            items(items = state.list_notes, key = { it.id }) { note ->
                val dismissState = rememberDismissState(
                    confirmValueChange = {
                        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                            noteViewModel.deleteNoteById(note.id)
                        }
                        true
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color = when (dismissState.dismissDirection) {
                            DismissDirection.StartToEnd, DismissDirection.EndToStart -> Color.Red
                            null -> Color.Transparent
                        }
                        Box(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .fillMaxSize()
                                .background(color)
                                .padding(20.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Icon(
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
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 4.dp)
                                .shadow(6.dp, shape = RoundedCornerShape(8.dp))
                                .background(Color(0xFFFFFFFF)),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                if (currentlyEditingNoteId.value == note.id) {
                                    TextField(
                                        value = editingTitle.value,
                                        onValueChange = { editingTitle.value = it },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                     TextField(
                                        value = editingContent.value,
                                        onValueChange = { editingContent.value = it },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                    Button(modifier = Modifier
                                        .padding(1.dp)
                                        .width(110.dp)
                                        .height(40.dp)
                                        .padding(1.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(Color(0xFF8091FC)),
                                        onClick = {
                                            noteViewModel.updateNote(note.copy(title = editingTitle.value, content = editingContent.value))
                                            currentlyEditingNoteId.value = null
                                        }) {
                                        Text("Save")
                                    }
                                } else {
                                    Text(
                                        text = note.title,
                                        fontSize = 21.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                    )
                                    Text(
                                        text = note.content,
                                        fontSize = 21.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                    )
                                    Button(
                                        modifier = Modifier
                                            .padding(1.dp)
                                            .width(110.dp)
                                            .height(40.dp)
                                            .padding(1.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(Color(0xFF8091FC)),
                                        onClick = {
                                            currentlyEditingNoteId.value = note.id
                                            editingTitle.value = note.title
                                            editingContent.value = note.content
                                        }) {
                                        Text("Edit")
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}