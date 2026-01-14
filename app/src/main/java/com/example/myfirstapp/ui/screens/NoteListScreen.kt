package com.example.myfirstapp.ui.screens
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.Icons
import androidx.navigation.compose.*

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myfirstapp.viewmodel.NoteViewModel
import com.example.myfirstapp.data.NoteEntity
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteListScreen(
    navController: NavHostController,
    viewModel: NoteViewModel = viewModel(
        factory = com.example.myfirstapp.viewmodel.NoteViewModelFactory(
            androidx.compose.ui.platform.LocalContext.current.applicationContext as android.app.Application
        )
    )
) {
    val notes by viewModel.allNotes.collectAsState(initial = emptyList())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My First App") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("noteEdit") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
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
                Text("No notes yet. Tap + to add one.")
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(notes) { note ->
                    NoteItem(note = note, onClick = {
                        navController.navigate("noteEdit?noteId=${note.id}")
                    })
                }
            }
        }
    }
}

@Composable
fun NoteItem(note: NoteEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
                    .format(Date(note.timestamp)),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
