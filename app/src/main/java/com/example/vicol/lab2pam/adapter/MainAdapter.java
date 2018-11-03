package com.example.vicol.lab2pam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vicol.lab2pam.R;
import com.example.vicol.lab2pam.domain.Description;
import com.example.vicol.lab2pam.domain.DescriptionAndNoteInterface;
import com.example.vicol.lab2pam.domain.Note;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DATE_VIEW = 1;
    private static final int NOTE_VIEW = 2;
//    private HashMap<String , List<Note>> hashMap;
//    private ArrayList<String> titleList;
    private Context context;
    private List<DescriptionAndNoteInterface> list;

//    public MainAdapter(Context context , HashMap<String, List<Note>> hashMap) {
//        this.hashMap = hashMap;
//        this.titleList =  new ArrayList<>(hashMap.keySet());
//        this.context = context;
//    }

    public MainAdapter(Context context,List<DescriptionAndNoteInterface> list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

    RecyclerView.ViewHolder viewHolder = null;
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

    switch (viewType){
        case NOTE_VIEW:{
            View noteView = inflater.inflate(R.layout.note,viewGroup,false);
            viewHolder = new NoteViewHolder(noteView);

        }break;
        case DATE_VIEW:{
            View dateView = inflater.inflate(R.layout.date,viewGroup,false);
            viewHolder = new TileViewHolder(dateView);
        }break;
    }

    return viewHolder;
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.date,viewGroup,false);
//        return new MainAdapter.TileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,int position) {
       final int a = position;
        switch (viewHolder.getItemViewType()){
            case NOTE_VIEW:{

                NoteViewHolder noteViewHolder = (NoteViewHolder) viewHolder;
                Note note = (Note)list.get(position);
                noteViewHolder.titleTextView.setText(note.getTitle());
                noteViewHolder.timeTextView.setText(note.getTime());
                noteViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(context,"position :: " + a,Toast.LENGTH_LONG).show();
                        //TODO redirect to another view for edit ... refactor add_note view for editing
                    }
                });

            }break;

            case DATE_VIEW:{
                TileViewHolder tileViewHolder = (TileViewHolder)viewHolder;
                Description  description = (Description)list.get(position);
                tileViewHolder.textView.setText(description.getDate());

            }break;

        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull TileViewHolder tileViewHolder, int i) {
//        tileViewHolder.textView.setText(titleList.get(i));
//    }


    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof Description){
            return DATE_VIEW;
        }else if (list.get(position) instanceof Note){
            return NOTE_VIEW;
        }
        throw new RuntimeException("get item view type");
    }

    @Override
    public int getItemCount() {
        return list.size();
//        return hashMap.size();
    }


      public static class TileViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
            public TileViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.dateTextView_dateLay);
            }
        }


        public static class NoteViewHolder extends RecyclerView.ViewHolder{
            TextView titleTextView,timeTextView;
            ConstraintLayout constraintLayout;
            public NoteViewHolder(@NonNull View itemView) {
                super(itemView);
                constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.container_note);
                timeTextView = (TextView) itemView.findViewById(R.id.timeTextView_note);
                titleTextView= (TextView) itemView.findViewById(R.id.titleTextView_note);

            }
        }

}
