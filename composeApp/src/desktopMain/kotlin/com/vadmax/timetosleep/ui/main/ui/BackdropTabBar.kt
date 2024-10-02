package com.vadmax.timetosleep.ui.main.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import com.vadmax.timetosleep.ui.widgets.spacer.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.ui.theme.Shapes
import com.vadmax.timetosleep.utils.VoidCallback
import com.vadmax.timetosleep.utils.extentions.clickableNoRipple
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeBackdropTabBar(
    onDownloadClick: VoidCallback,
    isPairTab: Boolean,
    onPairClick: VoidCallback,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
        ) {
            Text(
                text = "Pair",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.clickableNoRipple { onPairClick() },
                color = Color.White,
            )
            Indicator(
                selected = isPairTab,
            )
        }
        Spacer(20.dp)
        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
        ) {
            Text(
                text = "Download",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.clickableNoRipple { onDownloadClick() },
                color = Color.White,
            )
            Indicator(
                selected = isPairTab.not(),
            )
        }
    }
}

@Composable
private fun Indicator(
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = selected,
        enter = scaleIn(
            animationSpec = tween(
                durationMillis = 300,
                easing = EaseOutBack,
            ),
        ),
        exit = scaleOut(),
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(Shapes.shape16)
                .background(Color.White),
        )
    }
}