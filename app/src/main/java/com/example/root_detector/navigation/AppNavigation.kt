package com.example.root_detector.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.root_detector.presentation.MainScreen
import com.example.root_detector.presentation.MainViewModel
import com.example.root_detector.presentation.DetailImgScreen
import com.example.root_detector.presentation.DetailImgViewModel

@Composable
fun AppNavigation(
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
    detailImgViewModel: DetailImgViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.MainScreen.route) {
        composable(Screens.MainScreen.route) {
            MainScreen(paddingValues, navController, mainViewModel)
        }

        composable(Screens.DetailImgScreen.route, enterTransition = {
            slideIntoContainer(
                animationSpec = tween(350, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        }, exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(350, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }) {
            DetailImgScreen(paddingValues, navController, detailImgViewModel)
        }
    }
}