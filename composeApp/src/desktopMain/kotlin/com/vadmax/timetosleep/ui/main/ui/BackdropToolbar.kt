package com.vadmax.timetosleep.ui.main.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.ui.theme.AppColors
import com.vadmax.timetosleep.utils.VoidCallback
import com.vadmax.timetosleep.utils.extentions.clickableNoRipple
import org.jetbrains.compose.resources.painterResource
import timetosleepkmp.composeapp.generated.resources.Res
import timetosleepkmp.composeapp.generated.resources.ic_android

@Composable
fun BackdropToolbar(
    connected: Boolean,
    switchBackdropSate: VoidCallback,
) {
    val (text, color) = remember(connected) {
        if (connected) {
            "Device connected" to Color.Green
        } else {
            "Device disconnected" to AppColors.disconnected
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = switchBackdropSate) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(Res.drawable.ic_android),
                contentDescription = "Android icon",
                tint = color,
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = color,
            modifier = Modifier.clickableNoRipple { switchBackdropSate() }
        )
    }
}