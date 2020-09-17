package com.stone.mynotes.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stone.mynotes.R;
import com.stone.mynotes.deligate.NoteClickListener;
import com.stone.mynotes.dto.Note;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{
    Context context;
    List<Note> notes;
    List<Note> noteSearch;
    NoteClickListener listener;


    public void setNotes(List<Note> notes) {
        this.notes = notes;
        noteSearch=notes;
        notifyDataSetChanged();
    }
    public NoteAdapter(Context context,NoteClickListener listener) {
        notes=new ArrayList<>();
        noteSearch=new ArrayList<>();
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_layout,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.setNote(notes.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.noteClickListener(notes.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public void searchNote(String searchKey){
        if (searchKey.trim().equals("")||searchKey.isEmpty()){
            notes=noteSearch;
        }else{
            List<Note> temp=new ArrayList<>();
            for (Note note:noteSearch){
                if (note.getTitle().toLowerCase().contains(searchKey.toLowerCase())
                ||note.getNoteText().toLowerCase().contains(searchKey.toLowerCase())){
                    temp.add(note);
                }
            }
            notes=temp;
        }
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textTitle)
        TextView title;
        @BindView(R.id.textNote)
        TextView txtnote;
        @BindView(R.id.txtDataTime)
        TextView datetime;
        @BindView(R.id.layoutNote)
        LinearLayout layout;
        @BindView(R.id.itemImageNote)
        ImageView imageView;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        void  setNote(Note note){
            title.setText(note.getTitle());
            txtnote.setText(note.getNoteText());
            datetime.setText(note.getDateTime());
            GradientDrawable gradientDrawable= (GradientDrawable) layout.getBackground();
            if (note.getColor()!=null) {
                gradientDrawable.setColor(Color.parseColor(note.getColor()));
            }else {
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }
            if (note.getImagePath() !=null && !note.getImagePath().equals("")){
                imageView.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                imageView.setVisibility(View.VISIBLE);
            }else {
                imageView.setVisibility(View.GONE);
            }
        }
    }
}
