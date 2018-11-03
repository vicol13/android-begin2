package com.example.vicol.lab2pam.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.vicol.lab2pam.adapter.MainAdapter;
import com.example.vicol.lab2pam.dataBase.MySQLiteDataBase;
import com.example.vicol.lab2pam.R;
import com.example.vicol.lab2pam.domain.Description;
import com.example.vicol.lab2pam.domain.DescriptionAndNoteInterface;

import com.example.vicol.lab2pam.domain.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapter adapter;
    List<DescriptionAndNoteInterface> generalTest;

    MySQLiteDataBase mySQLiteDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mySQLiteDataBase = new MySQLiteDataBase(this);
        recyclerView = (RecyclerView) findViewById(R.id.recView);




        generalTest = mySQLiteDataBase.getList();
        //TODO send it into AsynkTaskLoader
        adapter = new MainAdapter(this,generalTest);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void addNote(View view) {
        Intent intent  = new Intent(this,AddNote.class);
        startActivity(intent);
    }


}
