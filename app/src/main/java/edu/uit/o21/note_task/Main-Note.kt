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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
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
import androidx.compose.ui.draw.clip
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
//    toTaskDetail: () -> Unit,
    noteViewModel: NoteViewModel = viewModel(factory = AppViewModelNt.Factory)
) {
    val state by noteViewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .background(Color(0xFFE3F2FD))
            .fillMaxSize()
    ) {
        Header(text = "NOTE DETAIL")
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
            ContentButton(onClick = toNoteList,
                text = "Notes",
                icon = Icons.Default.List
            )
//            ContentButton(onClick = toTaskDetail ,
//
//                icon = Icons.Default.CheckCircle,
//                        text = "Tasks",
//            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = state.title,
            onValueChange = { noteViewModel.setTitle(it) },
            label = { Text(text = "Title") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = state.content,
            onValueChange = { noteViewModel.setcontent(it) },
            label = { Text(text = "Content") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = { noteViewModel.insertNote() },
            modifier = Modifier
                .align(Alignment.End)
                .padding(8.dp)
                .width(90.dp)
                .height(90.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF0277BD))
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
    ) {
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
                                .fillMaxSize()
                                .background(color),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text("Deleting",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE3F2FD))
                        }
                    },
                    dismissContent = {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clip(shape = RoundedCornerShape(12.dp))
                                .background(Color(0xFFBBDEFB))
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                if (currentlyEditingNoteId.value == note.id) {
                                    OutlinedTextField(
                                        value = editingTitle.value,
                                        onValueChange = { editingTitle.value = it },
                                        modifier = Modifier.padding(10.dp)
                                                .fillMaxWidth()
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
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                    )
                                    Text(
                                        text = note.content,
                                        fontSize = 24.sp,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
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