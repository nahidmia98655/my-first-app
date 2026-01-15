package com.example.myfirstapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapp.ui.screens.NoteEditScreen
import com.example.myfirstapp.ui.screens.NoteListScreen

@Composable
fun NoteNavGraph(startDestination: String = Screen.List.route) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.List.route) {
            NoteListScreen(navController = navController)
        }
        composable(Screen.Edit.route) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: -1
            NoteEditScreen(navController = navController, noteId = noteId)
        }
    }
}
