package com.vadmax.timetosleep.ui.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vadmax.timetosleep.ui.main.support.MainRoute
import com.vadmax.timetosleep.ui.main.support.mainScreenComposition
import com.vadmax.timetosleep.ui.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    viewModel: AppViewModel = koinViewModel(),
) {
    AppTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = MainRoute.ROUTE
        ) {
            mainScreenComposition()
        }
    }
}