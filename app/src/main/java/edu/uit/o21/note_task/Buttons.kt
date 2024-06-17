package edu.uit.o21.note_task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopNoteTaskButton(
    onClick: () -> Unit,
    text: String,
    icon: ImageVector? = null,
    contentDescription: String? = null
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(205.dp)
            .height(60.dp)
            .padding(vertical = 4.dp),
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
        Text(text = text, fontSize = 21.sp, color = Color.Gray)
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
            .padding(horizontal = 4.dp)
            .shadow(6.dp, shape = RoundedCornerShape(8.dp)),
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