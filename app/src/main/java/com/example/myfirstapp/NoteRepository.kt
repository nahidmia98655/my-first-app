package com.example.myfirstapp.repository

import com.example.myfirstapp.data.Note
import com.example.myfirstapp.data.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val dao: NoteDao) {
    val allNotes: Flow<List<Note>> = dao.getAllNotes()

    suspend fun getNoteById(id: Int): Note? = dao.getNoteById(id)
    suspend fun insert(note: Note) = dao.insert(note)
    suspend fun update(note: Note) = dao.update(note)
    suspend fun delete(note: Note) = dao.delete(note)
}
