package com.mr3y.vertical_thread

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.twotone.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Comment(
    val authorName: String,
    val text: String,
    val dateCreated: String,
    val depth: Int
)

@Composable
fun TestComment(comment: Comment, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        var dateOffset by remember { mutableStateOf(0) }
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                modifier = Modifier.alignBy { it.measuredHeight },
                painter = rememberVectorPainter(image = Icons.TwoTone.Person),
                contentDescription = "Avatar of comment's sender"
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(text = comment.authorName, modifier = Modifier)
            Spacer(modifier = Modifier.width(90.dp))
            Text(
                text = comment.dateCreated,
                textAlign = TextAlign.End,
                color = Color.DarkGray.copy(alpha = 0.8f),
                modifier = Modifier.graphicsLayer {
                    translationX = dateOffset.toFloat()
                }
            )
        }
        Text(
            text = comment.text,
            modifier = Modifier.wrapContentWidth(),
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            onTextLayout = { textLayoutResult ->
                dateOffset = textLayoutResult.getLineEnd(0)
            }
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(170.dp))
            Icon(
                painter = rememberVectorPainter(image = Icons.Filled.Edit),
                contentDescription = "Edit Comment",
                tint = Color.LightGray.copy(alpha = 0.9f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = rememberVectorPainter(image = Icons.Filled.Delete),
                contentDescription = "Delete Comment",
                tint = Color.Red.copy(alpha = 0.8f)
            )
        }
    }
}
