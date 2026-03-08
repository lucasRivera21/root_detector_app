package com.lucasdev.root_detector.navigation

sealed class Screens(val route: String) {
    object MainScreen : Screens("main_screen")
    object DetailImgScreen : Screens("detail_img_screen")
}