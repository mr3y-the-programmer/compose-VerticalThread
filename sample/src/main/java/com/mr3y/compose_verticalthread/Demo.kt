package com.mr3y.compose_verticalthread

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.twotone.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mr3y.vertical_thread.VerticalThread

@Composable
@Preview(backgroundColor = 0xFFFFFF, widthDp = 640, heightDp = 800, showBackground = true)
fun ThreadPreviewLTR() {
    val commentsWithReplies by remember {
        mutableStateOf(
            listOf(
                Comment("Nick", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "2min ago", 1),
                Comment("John", "Lorem ipsum dolor sit amet", "1min ago", 2),
                Comment(
                    "Nick",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse venenatis aliquet ex, id pulvinar odio rutrum sed. Donec in tempor ex. In egestas aliquet ante sollicitudin finibus.",
                    "23sec ago",
                    3
                ),
                Comment("Ashley", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse venenatis aliquet ex,", "5min ago", 1),
                Comment("Bob", "Lorem ipsum dolor sit amet", "4min ago", 1),
                Comment("Cleo", "Lorem ipsum dolor sit amet", "3min ago", 2),
                Comment("Sam", "Lorem ipsum dolor sit amet", "3min ago", 2),
                Comment("Jean", "Lorem ipsum dolor sit amet", "2min ago", 2),
                Comment("Frank", "Lorem ipsum dolor sit amet", "50sec ago", 3),
                Comment("Micheal", "Lorem ipsum dolor sit amet", "40sec ago", 3),
                Comment("Sarah", "Lorem ipsum dolor sit amet", "20sec ago", 4),
                Comment("Ben", "Lorem ipsum dolor sit amet", "18sec ago", 4),
                Comment("Paul", "Lorem ipsum dolor sit amet", "2sec ago", 4),
                Comment("Cleo", "Lorem ipsum dolor sit amet", "1min ago", 5),
                Comment("Michelle", "Lorem ipsum dolor sit amet", "1hour ago", 5),
                Comment("Daniel", "Lorem ipsum dolor sit amet", "33min ago", 6),
                Comment("Nick", "Lorem ipsum dolor sit amet", "30min ago", 5),
                Comment("Anthony", "Lorem ipsum dolor sit amet", "2hours ago", 2),
                Comment("Chris", "Lorem ipsum dolor sit amet", "53min ago", 1),
                Comment("Anne", "Lorem ipsum dolor sit amet", "4hours ago", 2),
            )
        )
    }
    VerticalThread(
        items = commentsWithReplies,
        itemPadding = PaddingValues(start = 16.dp, bottom = 8.dp, top = 8.dp, end = 24.dp),
        modifier = Modifier.fillMaxSize(),
        decoration = {
            val alpha = when {
                it.depth > 4 -> 1f
                it.depth > 3 -> 0.5f
                it.depth > 2 -> 0.4f
                else -> 0.3f
            }
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .background(Color.DarkGray.copy(alpha = alpha))
            )
        }
    ) { comment ->
        Item(
            comment,
            modifier = Modifier
                .depth(comment.depth)
                .requiredWidth(248.dp)
                .wrapContentHeight()
        )
    }
}

@Composable
fun Item(comment: Comment, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.Yellow.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 4.dp)
                .wrapContentWidth()
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                modifier = Modifier.size(20.dp),
                painter = rememberVectorPainter(image = Icons.TwoTone.Person),
                contentDescription = "Avatar of comment's sender"
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(text = comment.authorName, modifier = Modifier.padding(top = 0.5.dp))
            Spacer(modifier = Modifier.width(90.dp))
            Column(modifier = Modifier.weight(0.5f)) {
                Text(
                    text = comment.dateCreated,
                    textAlign = TextAlign.End,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .align(Alignment.End)
                )
            }
        }
        Text(
            text = comment.text,
            modifier = Modifier
                .padding(start = 12.dp)
                .wrapContentWidth(),
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
        )
        Row(
            modifier = Modifier
                .padding(bottom = 6.dp)
                .wrapContentWidth()
        ) {
            Column(modifier = Modifier.weight(0.85f)) {
                Icon(
                    modifier = Modifier
                        .size(28.dp)
                        .align(Alignment.End),
                    painter = painterResource(id = R.drawable.comment_vector),
                    contentDescription = "Reply",
                    tint = Color.DarkGray.copy(alpha = 0.9f)
                )
            }
            Column(modifier = Modifier.weight(0.15f)) {
                Icon(
                    modifier = Modifier.align(Alignment.End),
                    painter = rememberVectorPainter(image = Icons.Outlined.Delete),
                    contentDescription = "Delete Comment",
                    tint = Color.Red
                )
            }
        }
    }
}

data class Comment(
    val authorName: String,
    val text: String,
    val dateCreated: String,
    val depth: Int
)
