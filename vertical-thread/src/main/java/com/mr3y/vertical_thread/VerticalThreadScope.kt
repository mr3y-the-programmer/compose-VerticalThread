package com.mr3y.vertical_thread

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density

@LayoutScopeMarker
@Immutable
public interface VerticalThreadScope {
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
