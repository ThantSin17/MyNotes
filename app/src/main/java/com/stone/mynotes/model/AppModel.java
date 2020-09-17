package com.stone.mynotes.model;

import android.content.Context;

import com.stone.mynotes.database.NoteDatabase;
import com.stone.mynotes.deligate.GetNotes;
import com.stone.mynotes.deligate.SaveNotes;
import com.stone.mynotes.dto.Note;

import java.util.List;

import androidx.room.Room;

public class AppModel {
    private static AppModel Instance;
    private static NoteDatabase db;
    public static AppModel getInstance(Context context) {
        if (Instance==null){
            Instance=new AppModel();
            db= Room.databaseBuilder(context,NoteDatabase.class,"note_db").allowMainThreadQueries().build();
        }
        return Instance;
    }
    public static void InsertNote(Note note, SaveNotes saveNotes){
        try {
            db.noteDao().insertNote(note);
            saveNotes.success("success");
        }catch (Exception e){
            saveNotes.fail("fail "+e.toString());
        }

    }
    public static void UpdateNote(Note note,SaveNotes saveNotes){
        try {
            db.noteDao().updateNote(note);
            saveNotes.success("success");
        }catch (Exception e){
            saveNotes.fail("fail "+e.toString());
        }

    }
    public static void GetAllNote(GetNotes getNotes){
        try {
            getNotes.successResult(db.noteDao().getAllNotes());
            getNotes.success("success");
        }catch (Exception e){
            getNotes.fail("fail "+e.toString());
        }

    }
}
