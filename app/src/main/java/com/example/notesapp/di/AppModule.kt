package com.example.notesapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.notesapp.data.NoteDao
import com.example.notesapp.data.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNoteDB(@ApplicationContext appContext: Context):NoteDatabase {
        return Room.databaseBuilder(appContext,
            NoteDatabase::class.java, "noteDB").build()
    }

    @Provides
    @Singleton
    fun providesNoteDao(database: NoteDatabase) = database.getNoteDao()

}