package com.vadmax.timetosleep.ui.widgets.backdrop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Stable
class BackdropState(collapsed: Boolean = true) {

    var collapsed: Boolean by mutableStateOf(collapsed)
        private set

    fun expand() {
        collapsed = false
    }

    fun collapse() {
        collapsed = true
    }

    fun switch() {
        collapsed = collapsed.not()
    }

    companion object {
        val Saver: Saver<BackdropState, *> = listSaver(
            save = { listOf(it.collapsed) },
            restore = { BackdropState(it.first()) }
        )
    }
}

@Composable
fun rememberBackdropState(collapsed: Boolean = true) =
    rememberSaveable(saver = BackdropState.Saver) {
        BackdropState(collapsed = collapsed)
    }