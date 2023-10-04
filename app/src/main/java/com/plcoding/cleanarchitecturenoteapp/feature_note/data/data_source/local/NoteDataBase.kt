package com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.models.Note

@Database(entities = [Note::class], version = 3, exportSchema = false)
abstract class NoteDataBase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}
