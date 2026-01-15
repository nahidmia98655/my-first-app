package com.example.myfirstapp.ui

sealed class Screen(val route: String) {
    object List : Screen("list")
    object Edit : Screen("edit/{noteId}") {
        fun createRoute(noteId: Int = -1) = "edit/$noteId"
    }
}
