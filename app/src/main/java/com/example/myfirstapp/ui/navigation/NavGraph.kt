package com.example.myfirstapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapp.ui.screens.NoteEditScreen
import com.example.myfirstapp.ui.screens.NoteListScreen

@Composable
fun NavGraph(startDestination: String = "noteList") {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable("noteList") {
            NoteListScreen(navController = navController)
        }
        composable("noteEdit?noteId={noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toLongOrNull()
            NoteEditScreen(navController = navController, noteId = noteId)
        }
    }
}
