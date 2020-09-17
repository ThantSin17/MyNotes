package com.stone.mynotes.database;

import com.stone.mynotes.dao.NoteDao;
import com.stone.mynotes.dto.Note;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Note.class,version = 1,exportSchema = false)
public  abstract class NoteDatabase extends RoomDatabase {
   public abstract NoteDao noteDao();
}
