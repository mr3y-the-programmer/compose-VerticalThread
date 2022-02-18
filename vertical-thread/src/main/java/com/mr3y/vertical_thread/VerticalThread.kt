package com.mr3y.vertical_thread

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import java.util.UUID

private val Measurable.depth: Int
    get() = (parentData as VerticalThreadItemDepth).depth

@Composable
public fun <T> VerticalThread(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemPadding: PaddingValues = PaddingValues(0.dp),
    decoration: @Composable (item: T) -> Unit,
    itemContent: @Composable VerticalThreadScope.(item: T) -> Unit
) {
    val threadScope = VerticalThreadScopeImpl
    SubcomposeLayout(modifier = modifier.verticalScroll(rememberScrollState())) { parentConstraints ->
        val itemsMeasurables = subcompose(Unit) {
            repeat(items.size) {
                key(it) {
                    threadScope.itemContent(items[it])
                }
            }
        }

        val endPadding = itemPadding.calculateEndPadding(layoutDirection).roundToPx()
        val itemConstraints = parentConstraints.copy(minWidth = 0, minHeight = 0, maxWidth = (parentConstraints.maxWidth - endPadding))
        val itemPlaceablesWithDepth = itemsMeasurables.map { it.measure(itemConstraints) to it.depth }
        layout(parentConstraints.maxWidth, parentConstraints.maxHeight) {
            var decorationOffsetY = 0
            var itemOffsetY = 0
            var parentItemDepth = -1
            var nextDecorationOffsetX = -1
            itemPlaceablesWithDepth.forEachIndexed { itemIndex, (itemPlaceable, depth) ->
                val startPadding = itemPadding.calculateStartPadding(layoutDirection).roundToPx()
                val topPadding = itemPadding.calculateTopPadding().roundToPx()
                val bottomPadding = itemPadding.calculateBottomPadding().roundToPx()
                val decorationRequiredHeight = itemPlaceable.height + topPadding + bottomPadding
                val decorationConstraints = Constraints.fixedHeight(decorationRequiredHeight)
                itemOffsetY += topPadding
                fun drawAscendantsDecorators(parentOffsetX: Int) {
                    var prevDecorationOffsetX = parentOffsetX
                    var prevDepth = -1
                    for (i in itemIndex - 1 downTo 0) {
                        val currDepth = itemPlaceablesWithDepth[i].second
                        if (currDepth < 2) break
                        if (currDepth >= depth || prevDepth == currDepth) continue
                        prevDepth = currDepth
                        val prevDecorationPlaceable = subcompose(UUID.randomUUID()) {
                            decoration(items[i])
                        }.single().measure(decorationConstraints)
                        prevDecorationPlaceable.placeRelative(prevDecorationOffsetX, decorationOffsetY)
                        prevDecorationOffsetX -= startPadding
                    }
                }
                val decorationPlaceable = subcompose(itemPlaceable.hashCode()) {
                    decoration(items[itemIndex])
                }.single().measure(decorationConstraints)
                when {
                    depth > 1 && depth > parentItemDepth -> {
                        drawAscendantsDecorators(nextDecorationOffsetX - startPadding)
                        decorationPlaceable.placeRelative(nextDecorationOffsetX, decorationOffsetY)
                        itemPlaceable.placeRelative(nextDecorationOffsetX + startPadding, itemOffsetY)
                        nextDecorationOffsetX += startPadding
                    }
                    depth != 1 && depth == parentItemDepth -> {
                        drawAscendantsDecorators(nextDecorationOffsetX - (2 * startPadding))
                        decorationPlaceable.placeRelative(nextDecorationOffsetX - startPadding, decorationOffsetY)
                        itemPlaceable.placeRelative(nextDecorationOffsetX, itemOffsetY)
                    }
                    depth != 1 && depth < parentItemDepth -> {
                        var tempDepth = parentItemDepth
                        while (depth <= tempDepth) {
                            nextDecorationOffsetX -= startPadding
                            tempDepth--
                        }
                        drawAscendantsDecorators(nextDecorationOffsetX - startPadding)
                        decorationPlaceable.placeRelative(nextDecorationOffsetX, decorationOffsetY)
                        itemPlaceable.placeRelative(nextDecorationOffsetX + startPadding, itemOffsetY)
                        nextDecorationOffsetX += startPadding
                    }
                    else -> {
                        itemPlaceable.placeRelative(startPadding, itemOffsetY)
                        // reset the offset of the decoration
                        nextDecorationOffsetX = startPadding
                    }
                }
                itemOffsetY += itemPlaceable.height + bottomPadding
                decorationOffsetY += itemPlaceable.height + topPadding + bottomPadding
                parentItemDepth = depth
            }
        }
    }
}
