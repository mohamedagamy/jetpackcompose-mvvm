package com.example.photoweather.ui.navigation

sealed class Screen(val route: String) {
    object Cars : Screen("cars")
    object Images : Screen("images/{item}") {
        fun createRoute(image: String) = "images/$image"
    }
}