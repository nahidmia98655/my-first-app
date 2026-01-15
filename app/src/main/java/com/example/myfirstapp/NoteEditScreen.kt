package com.example.myfirstapp.ui.screens
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.Icons

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfirstapp.data.Note
import com.example.myfirstapp.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    noteId: Int?,
    onNavigateBack: () -> Unit,
    viewModel: NotesViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current
    var titleState by remember { mutableStateOf(TextFieldValue("")) }
    var contentState by remember { mutableStateOf(TextFieldValue("")) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(noteId) {
        if (noteId != null) {
            isLoading = true
            val note = viewModel.notes.value.find { it.id == noteId }
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
                title = { Text(if (noteId == null) "New Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (noteId == null) {
                            viewModel.addNote(titleState.text, contentState.text)
                        } else {
                            val updated = Note(
                                id = noteId,
                                title = titleState.text,
                                content = contentState.text,
                                timestamp = System.currentTimeMillis()
                            )
                            viewModel.updateNote(updated)
                        }
                        onNavigateBack()
                    }) {
                        Icon(Icons.Default.Save, contentDescription = "Save")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
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
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        if (noteId == null) {
                            viewModel.addNote(titleState.text, contentState.text)
                        } else {
                            val updated = Note(
                                id = noteId,
                                title = titleState.text,
                                content = contentState.text,
                                timestamp = System.currentTimeMillis()
                            )
                            viewModel.updateNote(updated)
                        }
                        onNavigateBack()
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Save")
                }
            }
        }
    }
}
