package com.example.smartjobreminder.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SmartJobNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Destinations.Home.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){
        composable(Destinations.Home.route){
            HomeScreen(navController)
        }
        composable(Destinations.AddJob.route){
            AddJobScreen(navController)
        }
    }
}

sealed class Destinations(val route: String) {
    object Home : Destinations("home")
    object SignIn : Destinations("signIn")
    object AddJob : Destinations("addJob")


}