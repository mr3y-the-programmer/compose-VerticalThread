package com.mr3y.vertical_thread

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density

/**
 * Receiver Scope for the children of [VerticalThread]
 */
@LayoutScopeMarker
@Immutable
public interface VerticalThreadScope {
    /**
     * Specify the element's depth within the thread.
     * The first element in any thread has a depth of 1, the direct child of that element has a depth of 2
     * and in turn the direct child of that child has a depth of 3 and so on...
     * For any element, the depth cannot be less than 1.
     */
    @Stable
    public fun Modifier.depth(@IntRange(from = 1) num: Int): Modifier
}

internal object VerticalThreadScopeImpl : VerticalThreadScope {

    @Stable
    override fun Modifier.depth(num: Int) = this.then(VerticalThreadItemDepth(num))
}

internal data class VerticalThreadItemDepth(val depth: Int) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any {
        return (parentData as? VerticalThreadItemDepth) ?: VerticalThreadItemDepth(depth)
    }
}
