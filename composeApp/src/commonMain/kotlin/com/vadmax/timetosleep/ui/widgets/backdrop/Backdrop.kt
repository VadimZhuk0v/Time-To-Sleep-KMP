package com.vadmax.timetosleep.ui.widgets.backdrop

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.ui.theme.AppColors
import com.vadmax.timetosleep.utils.extentions.clickableNoRipple

@Composable
fun Backdrop(
    modifier: Modifier = Modifier,
    state: BackdropState = rememberBackdropState(),
    toolbar: @Composable () -> Unit,
    backdropContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    var backdropHeight by remember { mutableStateOf(0.dp) }

    val targetContentOffset by remember {
        derivedStateOf {
            if (state.collapsed) {
                0.dp
            } else {
                backdropHeight
            }
        }
    }
    val contentOffset by animateDpAsState(targetValue = targetContentOffset)

    Column(modifier = modifier.background(AppColors.backdropToolbar)) {
        toolbar()
        Box {
            Box(
                modifier = Modifier.onSizeChanged {
                    backdropHeight = with(density) { it.height.toDp() }
                }
            ) {
                backdropContent()
            }
            Box(
                modifier = Modifier
                    .offset(y = contentOffset)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(Color.Black)
                    .clickableNoRipple {
                        state.collapse()
                    }
            ) {
                content()
            }
        }
    }
}