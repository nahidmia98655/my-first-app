package com.example.myfirstapp.ui.screens
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.Icons

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.myfirstapp.data.local.NoteEntity
import com.example.myfirstapp.ui.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    viewModel: NoteViewModel,
    noteId: Long?,
    onNavigateBack: () -> Unit
) {
    var titleState by remember { mutableStateOf(TextFieldValue("")) }
    var contentState by remember { mutableStateOf(TextFieldValue("")) }
    var isLoading by remember { mutableStateOf(false) }

    // Load note if editing
    LaunchedEffect(noteId) {
        if (noteId != null) {
            isLoading = true
            val note = viewModel.repository.getNoteById(noteId)
            note?.let {
                titleState = TextFieldValue(it.title)
                contentState = TextFieldValue(it.content)
            }
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (noteId == null) "Add Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (noteId != null) {
                        IconButton(onClick = {
                            viewModel.deleteNote(NoteEntity(id = noteId, title = titleState.text, content = contentState.text))
                            onNavigateBack()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (noteId == null) {
                    viewModel.addNote(titleState.text, contentState.text)
                } else {
                    viewModel.updateNote(noteId, titleState.text, contentState.text)
                }
                onNavigateBack()
            }) {
                Icon(Icons.Default.Save, contentDescription = "Save")
            }
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = titleState,
                    onValueChange = { titleState = it },
                    label = { Text("Title") },
                    singleLine = true,
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
            }
        }
    }
}