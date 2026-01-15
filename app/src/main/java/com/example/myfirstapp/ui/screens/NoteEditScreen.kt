package com.example.myfirstapp.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myfirstapp.data.Note
import com.example.myfirstapp.data.NoteDatabase
import com.example.myfirstapp.data.NoteRepository
import com.example.myfirstapp.viewmodel.NoteViewModel
import com.example.myfirstapp.viewmodel.NoteViewModelFactory

@Composable
fun NoteEditScreen(navController: NavController, noteId: Int) {
    val context = LocalContext.current.applicationContext as Application
    val db = androidx.room.Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        "note_db"
    ).fallbackToDestructiveMigration().build()
    val repository = remember { NoteRepository(db.noteDao()) }
    val viewModel: NoteViewModel = viewModel(factory = NoteViewModelFactory(repository))

    val notes by viewModel.notes.collectAsState()
    val existingNote = notes.find { it.id == noteId }

    var titleState by remember { mutableStateOf(TextFieldValue(existingNote?.title ?: "")) }
    var contentState by remember { mutableStateOf(TextFieldValue(existingNote?.content ?: "")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == -1) "New Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (noteId != -1) {
                        IconButton(onClick = {
                            existingNote?.let { viewModel.deleteNote(it) }
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
                value = titleState,
                onValueChange = { titleState = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = contentState,
                onValueChange = { contentState = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = 20
            )
            Button(
                onClick = {
                    val title = titleState.text.trim()
                    val content = contentState.text.trim()
                    if (title.isNotEmpty() || content.isNotEmpty()) {
                        if (existingNote != null) {
                            viewModel.updateNote(
                                existingNote.copy(
                                    title = title,
                                    content = content,
                                    timestamp = System.currentTimeMillis()
                                )
                            )
                        } else {
                            viewModel.addNote(title, content)
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
