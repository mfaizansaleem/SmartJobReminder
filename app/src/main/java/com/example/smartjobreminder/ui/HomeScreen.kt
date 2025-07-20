package com.example.smartjobreminder.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smartjobreminder.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {

    Scaffold(
        content = {
            Column(
                modifier = Modifier.padding(it)
            ) {

            }
        }
    )


}