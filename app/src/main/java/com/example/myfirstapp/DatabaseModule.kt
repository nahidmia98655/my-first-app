package com.example.myfirstapp.di

import android.content.Context
import androidx.room.Room
import com.example.myfirstapp.data.local.NoteDao
import com.example.myfirstapp.data.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@androidx.annotation.ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao = database.noteDao()
}