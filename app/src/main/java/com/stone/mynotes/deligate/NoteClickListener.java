package com.stone.mynotes.deligate;

import com.stone.mynotes.dto.Note;

public interface NoteClickListener {
    void noteClickListener(Note note,int position);
}
