package com.vadmax.timetosleep

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vadmax.timetosleep.ui.main.support.MainRoute
import com.vadmax.timetosleep.ui.main.support.mainScreenComposition
import com.vadmax.timetosleep.ui.theme.AppTheme

@Composable
fun App() {
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