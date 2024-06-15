@file:OptIn(ExperimentalMaterial3Api::class)
package edu.uit.o21.note_task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

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
            .background(Color(0xFFE3F2FD))
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header(text = "NOTE DETAIL")
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ContentButton(onClick = onClickBack,
                text = "Back")
            ContentButton(onClick = toNoteList,
                text = "View Notes"
            )
            ContentButton(onClick = toTaskDetail ,
                text = "to Tasks"
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
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
            modifier = Modifier
                .align(Alignment.End)
                .width(130.dp)
                .height(50.dp)
                .padding(1.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(Color.Black)
        ) {
            Text(text = "Add")
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
            .background(Color(0xFFE3F2FD))
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header(text = "NOTE LIST")
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ContentButton(onClick = onClickBack, text = "Back")
            ContentButton(onClick = toTheMain, text = "Home")
        }
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
                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color = when (dismissState.dismissDirection) {
                            DismissDirection.StartToEnd, DismissDirection.EndToStart -> Color.Red
                            null -> Color.Transparent
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text("Deleting", color = Color(0xFFE3F2FD))
                        }
                    },
                    dismissContent = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .border(1.dp, Color.Black),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                if (currentlyEditingNoteId.value == note.id) {
                                    OutlinedTextField(
                                        value = editingTitle.value,
                                        onValueChange = { editingTitle.value = it },
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    OutlinedTextField(
                                        value = editingContent.value,
                                        onValueChange = { editingContent.value = it },
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    Button(modifier = Modifier
                                        .width(110.dp)
                                        .height(40.dp)
                                        .padding(1.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(Color.Black),
                                        onClick = {
                                            noteViewModel.updateNote(note.copy(title = editingTitle.value, content = editingContent.value))
                                            currentlyEditingNoteId.value = null
                                        }) {
                                        Text("Save")
                                    }
                                } else {
                                    Text(
                                        text = note.title,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    Text(
                                        text = note.content,
                                        fontSize = 21.sp,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    Button(
                                        modifier = Modifier
                                            .width(110.dp)
                                            .height(40.dp)
                                            .padding(1.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(Color.Black),
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