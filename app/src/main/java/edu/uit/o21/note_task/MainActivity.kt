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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
            .width(190.dp)
            .height(90.dp)
            .padding(vertical = 10.dp)
            .shadow(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF0277BD))
    ) {
        Text(text = text, fontSize = 21.sp, color = Color.White)
    }
}
@Composable
fun BottomBarButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    contentDescription: String? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(60.dp)
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF263238))
    ) {
        Column( horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()) {
            icon?.let {
                Icon(
                    imageVector = it,
                    tint = Color.White,
                    contentDescription = contentDescription
                )
            }
            Text(text = text, color = Color.White,
                fontSize = 12.sp
            )
        }

    }
}
@Composable
fun ContentButton(
    onClick: () -> Unit,
    text: String,
    icon: ImageVector? = null,
    contentDescription: String? = null
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(105.dp)
            .height(60.dp)
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF90CAF9))
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                tint = Color.Black,
                contentDescription = contentDescription,
                modifier = Modifier.size(128.dp)
            )
        }
        Text(text = text, fontSize = 18.sp, color = Color.Black)
    }
}
@Composable
fun TaskButton(
    onClick: () -> Unit,
    text: String,
    icon: ImageVector? = null,
    contentDescription: String? = null
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(135.dp)
            .height(60.dp)
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF90CAF9))
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                tint = Color.Black,
                contentDescription = contentDescription,
            )
        }
        Text(text = text, fontSize = 18.sp, color = Color.Black)
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
                       .padding(16.dp)
                       .background(Color.DarkGray),
                   contentDescription = null,
                   contentScale = ContentScale.Crop,
                   model = "https://png.pngtree.com/png-vector/20190324/ourmid/pngtree-vector-notes-icon-png-image_862518.jpg",
               )
                TopAppBar(modifier = Modifier
                    .background(Color(0xFF2962FF)),
                    title = { Text(text = "QUICK NOTE & TASK", fontStyle = FontStyle.Italic, fontFamily = FontFamily.Serif, fontWeight = FontWeight.ExtraBold) }
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
//                        toTaskDetail = { navHostController.navigate("TaskDetail") }
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
//                        toNoteDetail = { navHostController.navigate("NoteDetail") }
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
                    .height(66.dp)
                    .fillMaxWidth()
                    .background(Color(0xFF263238)),
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
                    icon = Icons.Default.DateRange
                )
                BottomBarButton(
                    onClick = { navHostController.navigate("TaskDetail") },
                    modifier = Modifier.weight(1f),
                    text = "Task",
                        icon = Icons.Default.Check
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
            .background(Color(0xFFE3F2FD))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(modifier = Modifier
            .fillMaxWidth(),
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
            .background(Color(0xFF90CAF9), shape = RoundedCornerShape(4.dp))
            .padding(vertical = 14.dp)
    ) {
        Text(
            text = text,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            fontFamily =  FontFamily.Serif
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
