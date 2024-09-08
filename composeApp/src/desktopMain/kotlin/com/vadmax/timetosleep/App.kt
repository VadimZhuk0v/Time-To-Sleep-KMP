package com.vadmax.timetosleep

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.vadmax.timetosleep.ui.main.support.MainRoute
import com.vadmax.timetosleep.ui.main.support.mainScreenComposition
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = MainRoute.ROUTE
        ) {
            mainScreenComposition()
        }
    }
}