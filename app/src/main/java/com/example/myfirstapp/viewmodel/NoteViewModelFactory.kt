package com.example.myfirstapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.data.DatabaseProvider
import com.example.myfirstapp.data.NoteRepository

class NoteViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    private val repository = NoteRepository(DatabaseProvider.getDatabase(application).noteDao())

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
