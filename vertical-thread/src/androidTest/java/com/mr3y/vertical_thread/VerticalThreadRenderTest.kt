package com.mr3y.vertical_thread

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * A screenshot test to verify we are rendering decorators & items correctly
 */
@RunWith(TestParameterInjector::class)
class VerticalThreadRenderTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @TestParameter
    lateinit var layoutDirection: LayoutDirection

    @Test
    fun take_a_static_list_of_items_and_render_them_correctly_in_both_directions() {
        composeRule.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                VerticalThread(
                    items = commentsWithReplies,
                    itemPadding = PaddingValues(
                        start = 16.dp,
                        bottom = 8.dp,
                        top = 8.dp,
                        end = 24.dp
                    ),
                    modifier = Modifier.fillMaxSize(),
                    decoration = {
                        val alpha = when {
                            it.depth > 4 -> 1f
                            it.depth > 3 -> 0.8f
                            it.depth > 2 -> 0.5f
                            else -> 0.3f
                        }
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .background(Color.DarkGray.copy(alpha = alpha))
                        )
                    }
                ) { comment ->
                    TestComment(
                        comment,
                        modifier = Modifier.depth(comment.depth)
                    )
                }
            }
        }
        val prefix = if (layoutDirection == LayoutDirection.Ltr) "LTR" else "RTL"
        compareScreenshot(composeRule, "${prefix}_part_1")
        composeRule.onRoot().onChild().performScrollToNode(hasText("Michelle"))
        compareScreenshot(composeRule, "${prefix}_part_2")
        composeRule.onRoot().onChild().performScrollToNode(hasText("Anne"))
        compareScreenshot(composeRule, "${prefix}_part_3")
    }

    companion object {
        val commentsWithReplies = listOf(
            Comment("Nick", "No It is not what do you think!dkldld1", "2min ago", 1),
            Comment("John", "No It is not what do you think!dkldld11", "1min ago", 2),
            Comment(
                "Nick",
                "No It is not what do you think!dkldld111" +
                    "\nNo It is not what do you think!dkldld111" +
                    "\nNo It is not what do you think!dkldld111",
                "23sec ago",
                3
            ),
            Comment("Ashley", "No It is not what do you think!dkldld2", "5min ago", 1),
            Comment("Bob", "No It is not what do you think!dkldld3", "4min ago", 1),
            Comment("Cleo", "No It is not what do you think!dkldld33", "3min ago", 2),
            Comment("Sam", "No It is not what do you think!dkldld33", "3min ago", 2),
            Comment("Jean", "No It is not what do you think!dkldld33", "2min ago", 2),
            Comment("Frank", "No It is not what do you think!dkldld333", "50sec ago", 3),
            Comment("Micheal", "No It is not what do you think!dkldld333", "40sec ago", 3),
            Comment("Sarah", "No It is not what do you think!dkldld3333", "20sec ago", 4),
            Comment("Ben", "No It is not what do you think!dkldld3333", "18sec ago", 4),
            Comment("Paul", "No It is not what do you think!dkldld3333", "2sec ago", 4),
            Comment("Cleo", "No It is not what do you think!dkldld333333", "1min ago", 5),
            Comment("Michelle", "No It is not what do you think!dkldld33333", "1hour ago", 5),
            Comment("Daniel", "No It is not what do you think!dkldld333333", "33min ago", 6),
            Comment("Nick", "No It is not what do you think!dkldld3333", "30min ago", 5),
            Comment("Anthony", "No It is not what do you think!dkldld33", "2hours ago", 2),
            Comment("Chris", "No It is not what do you think!dkldld333", "53min ago", 1),
            Comment("Anne", "No It is not what do you think!dkldld333333", "4hours ago", 2),
        )
    }
}
