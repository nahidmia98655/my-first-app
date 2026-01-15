package com.example.myfirstapp.data

import com.example.myfirstapp.data.local.NoteDao
import com.example.myfirstapp.data.local.NoteEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    fun getAllNotes(): Flow<List<NoteEntity>> = noteDao.getAllNotes()

    suspend fun getNoteById(id: Long): NoteEntity? = noteDao.getNoteById(id)

    suspend fun insert(note: NoteEntity): Long = noteDao.insert(note)

    suspend fun update(note: NoteEntity) = noteDao.update(note)

    suspend fun delete(note: NoteEntity) = noteDao.delete(note)
}