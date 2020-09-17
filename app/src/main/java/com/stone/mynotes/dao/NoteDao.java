package com.stone.mynotes.dao;

import com.stone.mynotes.dto.Note;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {
    @Query("select * from notes Order by id desc")
    List<Note> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);
}
