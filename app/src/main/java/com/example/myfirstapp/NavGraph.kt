package com.example.myfirstapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapp.ui.screens.NoteEditScreen
import com.example.myfirstapp.ui.screens.NotesListScreen

sealed class Screen(val route: String) {
    object NotesList : Screen("notes_list")
    object NoteEdit : Screen("note_edit?noteId={noteId}") {
        const val ARG_NOTE_ID = "noteId"
    }
}

@Composable
fun NavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.NotesList.route,
        modifier = modifier
    ) {
        composable(Screen.NotesList.route) {
            NotesListScreen(onAddNote = { navController.navigate(Screen.NoteEdit.route) },
                onEditNote = { noteId ->
                    navController.navigate("${Screen.NoteEdit.route.replace("{noteId}", noteId.toString())}")
                })
        }
        composable(Screen.NoteEdit.route) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString(Screen.NoteEdit.ARG_NOTE_ID)
                ?.toIntOrNull()
            NoteEditScreen(noteId = noteId, onNavigateBack = { navController.popBackStack() })
        }
    }
}
