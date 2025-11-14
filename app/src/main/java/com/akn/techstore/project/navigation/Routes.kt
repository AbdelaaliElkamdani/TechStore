package com.akn.techstore.project.navigation

sealed class Routes(val route : String) {
    object Discover : Routes("discover")
    object Detail : Routes("detail/{id}") {
        fun createRoute(id : Int) = "detail/$id"
    }
    object Favorites : Routes("favorites")
    object Cart : Routes("cart")
    object Profile : Routes("profile")
    object EditProfile : Routes("edit")
    object Password : Routes("password")
    object Address : Routes("address")
    object Setting : Routes("settings")
    object Login : Routes("login")
    object Register : Routes("register")
}