package com.example.myfirstapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstapp.data.NoteEntity
import com.example.myfirstapp.data.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes: StateFlow<List<NoteEntity>> = repository.getAllNotes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getNoteById(id: Long) = repository.getNoteById(id)

    fun insertNote(title: String, content: String) {
        viewModelScope.launch {
            val note = NoteEntity(title = title, content = content)
            repository.insert(note)
        }
    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch {
            val updated = NoteEntity(
                id = id,
                title = title,
                content = content,
                timestamp = System.currentTimeMillis()
            )
            repository.update(updated)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            // Fetch the note to delete (simplified by creating a stub with the id)
            val note = NoteEntity(id = id, title = "", content = "", timestamp = 0)
            repository.delete(note)
        }
    }
}
