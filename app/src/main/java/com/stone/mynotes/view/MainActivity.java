package com.stone.mynotes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.stone.mynotes.R;
import com.stone.mynotes.adapters.NoteAdapter;
import com.stone.mynotes.deligate.GetNotes;
import com.stone.mynotes.deligate.NoteClickListener;
import com.stone.mynotes.dto.Note;
import com.stone.mynotes.model.AppModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GetNotes, NoteClickListener {


    public static final int REQUEST_CODE_ADD_NOTE=1;
    private AppModel appModel;
    NoteAdapter adapter;
    @BindView(R.id.imageAddNoteMain) ImageView imageAddNoteMain;
    @BindView(R.id.noteRecyclerView) RecyclerView recyclerView;
    @BindView(R.id.inputSearch) EditText inputSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        appModel=AppModel.getInstance(this);
        adapter=new NoteAdapter(this,this);
        appModel.GetAllNote(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.searchNote(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
               // adapter.searchNote(editable.toString());
            }
        });

    }
    @OnClick(R.id.imageAddNoteMain)
    void clickAddNoteMain(){
        //startActivityForResult(new Intent(this,CreateNoteActivity.class),REQUEST_CODE_ADD_NOTE);
        startActivity(CreateNoteActivity.goToCreateNoteActivity(this));

    }

    @Override
    public void success(String message) {

    }

    @Override
    public void successResult(List<Note> notes) {
        adapter.setNotes(notes);

    }

    @Override
    public void fail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        appModel.GetAllNote(this);
    }

    @Override
    public void noteClickListener(Note note,int position) {
        //Toast.makeText(this, position+" id "+note.getId(), Toast.LENGTH_SHORT).show();
        startActivity(CreateNoteActivity.goToCreateNoteWithData(this,note,position));
    }
}