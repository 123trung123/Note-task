package edu.uit.o21.note_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.uit.o21.note_task.ui.theme.NotetaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotetaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}
@Composable
fun Home(modifier: Modifier = Modifier,
         navHostController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navHostController,
        startDestination = "ScreenA"
    ) {
        composable(route = "ScreenA") {
            ScreenA(
                onClickBack = { navHostController.navigateUp() },
                onGoToB = { navHostController.navigate("ScreenB") },
                onGoToC = { navHostController.navigate("ScreenC") }
            )
        }
        composable(route = "ScreenB") {
            ScreenB(
                onClickBack = { navHostController.navigateUp() },
                onGoToA = { navHostController.navigate("ScreenA") },
                onGoToC = { navHostController.navigate("ScreenC") },
            )
        }
        composable(route = "ScreenC") {
            ScreenC(
                onClickBack = { navHostController.navigateUp() },
            )
        }
    }
}
@Composable
fun ScreenA(modifier: Modifier = Modifier,
            onClickBack: () -> Unit,
            onGoToB: () -> Unit,
            onGoToC: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Column {
            Text(text = "SCREEN A", fontSize = 40.sp, color = Color.Red)
            Button(onClick = onClickBack) {
                Text(text = "Back")
            }
            Button(onClick = onGoToB) {
                Text(text = "Go to B")
            }
            Button(onClick = onGoToC) {
                Text(text = "Go to C")
            }
        }
    }
}
@Composable
fun ScreenB(modifier: Modifier = Modifier,
            onClickBack: () -> Unit,
            onGoToA: () -> Unit,
            onGoToC: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Column {
            Text(text = "SCREEN B", fontSize = 40.sp, color = Color.Blue)
            Button(onClick = onClickBack) {
                Text(text = "Back")
            }
            Button(onClick = onGoToA) {
                Text(text = "Go to A")
            }
            Button(onClick = onGoToC) {
                Text(text = "Go to C")
            }
        }
    }
}
@Composable
fun ScreenC(modifier: Modifier = Modifier,
            onClickBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Column {
            Text(text = "SCREEN C", fontSize = 40.sp, color = Color.Magenta)
            Button(onClick = onClickBack) {
                Text(text = "Back")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomePreview() {
    NotetaskTheme {
        Home()
    }
}