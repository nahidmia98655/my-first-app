package com.example.myfirstapp.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val dao: NoteDao) {
    fun getAllNotes(): Flow<List<NoteEntity>> = dao.getAllNotes()
    fun getNoteById(id: Long): Flow<NoteEntity?> = dao.getNoteById(id)
    suspend fun insert(note: NoteEntity): Long = dao.insert(note)
    suspend fun update(note: NoteEntity) = dao.update(note)
    suspend fun delete(note: NoteEntity) = dao.delete(note)
}
