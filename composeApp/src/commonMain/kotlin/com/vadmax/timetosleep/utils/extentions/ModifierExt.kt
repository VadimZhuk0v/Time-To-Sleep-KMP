package com.vadmax.timetosleep.utils.extentions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.clickableNoRipple(block: () -> Unit) = this.composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
    ) {
        block()
    }
}

fun Modifier.notClickable() = clickableNoRipple {}