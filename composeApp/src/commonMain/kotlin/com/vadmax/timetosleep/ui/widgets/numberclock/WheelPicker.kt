package com.vadmax.timetosleep.ui.widgets.numberclock

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlin.math.min

@SuppressWarnings("MagicNumber")
@Composable
fun WheelPicker(
    isVibrationEnable: Boolean,
    itemHeight: Dp,
    itemsCount: Int,
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val itemHeightPx = LocalDensity.current.run { itemHeight.toPx() }
    Box(modifier = modifier.height(itemHeight * 3)) {
        Wheel(
            scrollState,
            itemHeight,
            itemsCount,
            horizontalAlignment,
            itemContent,
        )
    }
    LaunchedEffect(Unit) {
        combine(
            snapshotFlow { scrollState.isScrollInProgress },
            snapshotFlow { scrollState.firstVisibleItemIndex },
            snapshotFlow { scrollState.firstVisibleItemScrollOffset },
        ) { inProgress, index, offset ->
            AutoScrollData(inProgress, index, offset)
        }.debounce(150L).collectLatest {
            if (it.inProgress) {
                return@collectLatest
            }
            val index =
                if (it.offset < itemHeightPx / 2) {
                    it.index
                } else {
                    it.index + 1
                }
            scrollState.animateScrollToItem(index = index)
        }
    }
    LaunchedEffect(isVibrationEnable) {
        var selectedItem = 0
        snapshotFlow { scrollState.firstVisibleItemScrollOffset }
            .collectLatest {
                if (it < itemHeightPx / 2 && selectedItem != scrollState.firstVisibleItemIndex) {
                    selectedItem = scrollState.firstVisibleItemIndex
                }
            }
    }
}

@SuppressWarnings("MagicNumber")
@Composable
private fun Wheel(
    state: LazyListState,
    itemHeight: Dp,
    itemsCount: Int,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val itemHeightPx = LocalDensity.current.run { itemHeight.toPx() }
    LazyColumn(
        state = state,
        horizontalAlignment = horizontalAlignment,
        modifier = Modifier
            .width(60.dp)
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        state.scrollBy(-delta)
                    }
                },
            ),
        content = {
            item("header") {
                Box(modifier = Modifier.height(itemHeight))
            }
            items(
                count = itemsCount,
                key = { index -> index },
            ) { index ->
                val item = state.layoutInfo.visibleItemsInfo.find { it.key == index }
                val offset = item?.offset ?: 0
                val alpha = remember(offset, itemHeightPx) {
                    when {
                        offset < itemHeightPx -> min(offset / itemHeightPx + 0.5F, 1F)

                        offset > itemHeightPx -> itemHeightPx / offset

                        else -> 1F
                    }
                }
                val rotation = remember(offset, itemHeightPx) {
                    when {
                        offset < itemHeightPx -> (1F - alpha) * 100
                        offset > itemHeightPx -> -((1F - alpha) * 100)
                        else -> 0F
                    } * 1.2F
                }
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .alpha(alpha = alpha)
                        .graphicsLayer {
                            this.rotationX = min(rotation, 90F)
                        },
                ) {
                    itemContent(index)
                }
            }
            item("footer") {
                Box(modifier = Modifier.height(itemHeight))
            }
        },
    )
}

private class AutoScrollData(val inProgress: Boolean, val index: Int, val offset: Int)
