package com.stone.mynotes.deligate;

import com.stone.mynotes.dto.Note;

import java.util.List;

public interface GetNotes {
    void success(String message);
    void successResult(List<Note> notes);
    void fail(String message);
}
