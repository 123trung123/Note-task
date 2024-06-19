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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
            NotificationUtil.createChannel(this)
            val intervalMinutes = 48L
            ReminderWorker.scheduleReminder(this, intervalMinutes)
        }
    }
}
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            if (currentRoute == "TheMain"){
           Row (modifier = Modifier
               .height(130.dp)
               .background(color= Color.White),
               verticalAlignment = Alignment.CenterVertically){
               AsyncImage(
                   modifier = Modifier
                       .size(95.dp)
                       .padding(20.dp)
                       .background(Color.DarkGray),
                   contentDescription = null,
                   contentScale = ContentScale.Crop,
                   model = "https://png.pngtree.com/png-vector/20190324/ourmid/pngtree-vector-notes-icon-png-image_862518.jpg",
               )
                TopAppBar(modifier = Modifier,
                    title = { Text(text = "QUICK NOTE & TASK", fontFamily = FontFamily.Serif, fontWeight = FontWeight.ExtraBold) }
                )
           } }},
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
                    )
                }
                composable(route = "TaskList") {
                    TaskList(
                        onClickBack = { navHostController.navigateUp() },
                        toTheMain = { navHostController.navigate("TheMain") }
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(Color(0xFF292E4E)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomBarButton(
                    onClick = { navHostController.navigate("TheMain") },
                    modifier = Modifier.weight(1f),
                    text = "Home",
                    icon = Icons.Default.Home
                )
                BottomBarButton(
                    onClick = { navHostController.navigate("NoteDetail") },
                    modifier = Modifier.weight(1f),
                    text = "Note",
                    icon = Icons.Default.Menu
                )
                BottomBarButton(
                    onClick = { navHostController.navigate("TaskDetail") },
                    modifier = Modifier.weight(1f),
                    text = "Task",
                        icon = Icons.Default.CheckCircle
                )
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
            .background(Color(0xFFFFFFFF))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 35.dp, bottomEnd = 35.dp)),
            model = "https://assets-global.website-files.com/5f7ece8a7da656e8a25402bc/631f32ee984371cb97df4ce2_How%20to%20take%20notes%20from%20a%20textbook-p-800.png",
            contentDescription = null,
        )
        Spacer(modifier = Modifier.padding(25.dp))
        Row {
            StyledButton(onClick = toNoteDetail, modifier = Modifier,
                text = "Take Note"
            )
            Spacer(modifier = Modifier.padding(18.dp))
            StyledButton(onClick = toTaskDetail, modifier = Modifier,
                text = "Add Task"
            )
        }
    }
}
@Composable
fun Header(text: String = "") {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color(0xFF6074F9), shape = RoundedCornerShape(4.dp))
            .padding(vertical = 14.dp)
    ) {
        Text(
            text = text,
            fontSize = 23.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NotetaskTheme {
        HomeScreen()
    }
}
