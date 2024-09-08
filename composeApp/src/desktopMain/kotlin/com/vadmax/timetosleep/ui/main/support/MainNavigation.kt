package com.vadmax.timetosleep.ui.main.support

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vadmax.timetosleep.ui.main.MainScreen
import org.koin.compose.koinInject

object MainRoute {
    const val ROUTE = "main"
}

fun NavGraphBuilder.mainScreenComposition() {
    composable(route = MainRoute.ROUTE) {
        MainScreen(
            viewModel = koinInject()
        )
    }
}