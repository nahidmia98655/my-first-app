package com.example.myfirstapp.ui.screens
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.Icons
import androidx.navigation.compose.*

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myfirstapp.viewmodel.NoteViewModel

@Composable
fun NoteEditScreen(
    navController: NavHostController,
    noteId: Long?,
    viewModel: NoteViewModel = viewModel(
        factory = com.example.myfirstapp.viewmodel.NoteViewModelFactory(
            LocalContext.current.applicationContext as android.app.Application
        )
    )
) {
    val existingNoteFlow = noteId?.let { viewModel.getNoteById(it) }
    val existingNote by existingNoteFlow?.collectAsState(initial = null) ?: mutableStateOf(null)
    var title by remember { mutableStateOf(TextFieldValue(existingNote?.title ?: "")) }
    var content by remember { mutableStateOf(TextFieldValue(existingNote?.content ?: "")) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (noteId == null) "New Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (noteId != null) {
                        IconButton(onClick = {
                            viewModel.deleteNote(noteId)
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = 10
            )
            Button(
                onClick = {
                    if (title.text.isNotBlank() && content.text.isNotBlank()) {
                        if (noteId == null) {
                            viewModel.insertNote(title.text, content.text)
                        } else {
                            viewModel.updateNote(noteId, title.text, content.text)
                        }
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save")
            }
        }
    }
}
