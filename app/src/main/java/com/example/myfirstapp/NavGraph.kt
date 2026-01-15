package com.example.myfirstapp.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myfirstapp.ui.screens.NoteEditScreen
import com.example.myfirstapp.ui.screens.NoteListScreen

sealed class Screen(val route: String) {
    object List : Screen("note_list")
    object Edit : Screen("note_edit?noteId={noteId}") {
        fun createRoute(noteId: Long?) = "note_edit?noteId=${noteId ?: -1}"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.List.route) {
        composable(Screen.List.route) { backStackEntry ->
            val viewModel = hiltViewModel<NoteViewModel>()
            NoteListScreen(
                viewModel = viewModel,
                onAddNote = { navController.navigate(Screen.Edit.createRoute(null)) },
                onEditNote = { noteId -> navController.navigate(Screen.Edit.createRoute(noteId)) }
            )
        }
        composable(Screen.Edit.route) { backStackEntry ->
            val viewModel = hiltViewModel<NoteViewModel>()
            val noteIdArg = backStackEntry.arguments?.getString("noteId")?.toLongOrNull()
            val noteId = if (noteIdArg == -1L) null else noteIdArg
            NoteEditScreen(
                viewModel = viewModel,
                noteId = noteId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}