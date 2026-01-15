package com.example.myfirstapp.ui.screens
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myfirstapp.data.Note
import com.example.myfirstapp.data.NoteDatabase
import com.example.myfirstapp.data.NoteRepository
import com.example.myfirstapp.ui.Screen
import com.example.myfirstapp.viewmodel.NoteViewModel
import com.example.myfirstapp.viewmodel.NoteViewModelFactory

@Composable
fun NoteListScreen(navController: NavController) {
    val context = LocalContext.current.applicationContext as Application
    val db = androidx.room.Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        "note_db"
    ).fallbackToDestructiveMigration().build()
    val repository = remember { NoteRepository(db.noteDao()) }
    val viewModel: NoteViewModel = viewModel(factory = NoteViewModelFactory(repository))

    val notes by viewModel.notes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My First App") },
                colors = TopAppBarDefaults.mediumTopAppBarColors()
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.Edit.createRoute()) }) {
                Icon(Icons.Default.Add, contentDescription = "Add note")
            }
        }
    ) { paddingValues ->
        if (notes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No notes yet. Tap + to add.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                items(notes) { note ->
                    NoteItem(
                        note = note,
                        onClick = { navController.navigate(Screen.Edit.createRoute(note.id)) },
                        onDelete = { viewModel.deleteNote(note) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(note: Note, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(note.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    note.content,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = { onDelete() }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
