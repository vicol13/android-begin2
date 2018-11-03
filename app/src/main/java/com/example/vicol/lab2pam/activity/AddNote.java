package com.example.vicol.lab2pam.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.vicol.lab2pam.dataBase.MySQLiteDataBase;
import com.example.vicol.lab2pam.R;
import com.example.vicol.lab2pam.domain.Note;
import com.example.vicol.lab2pam.framents.DatePickerFragment;
import com.example.vicol.lab2pam.framents.TimePickerFragment;

import java.util.Calendar;

public class AddNote extends AppCompatActivity
implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    private MySQLiteDataBase mySQLiteDataBase;
    private int year,month,day;
    private TextView date,time,title,descriprion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        this.date = (TextView) findViewById(R.id.dateText_addNote);
        this.time = (TextView) findViewById(R.id.timeText_addNote);
        this.title = (TextView) findViewById(R.id.title_addNote);
        this.descriprion = (TextView) findViewById(R.id.description_addNote);

        mySQLiteDataBase = new MySQLiteDataBase(this);

        Calendar c = Calendar.getInstance();
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH);
        this.month++;
        this.day = c.get(Calendar.DAY_OF_MONTH);
    }


    public void pickDate(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void pickTime(View view) {
        DialogFragment dialogFragment = new TimePickerFragment();

        dialogFragment.show(getSupportFragmentManager(),"timePicker");
    }


    // validate the date
    // user can't chose a date in past
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        month++;
        if(this.year > year ){
            Toast.makeText(this,"you can't pick this year",Toast.LENGTH_LONG).show();

        }else {

            if(this.month > month && this.year == year){
                Toast.makeText(this,"you can't pick this month",Toast.LENGTH_LONG).show();
            }
            else {
                if (this.day > day && this.month == month){
                    Toast.makeText(this,"you can't pick this day",Toast.LENGTH_LONG).show();
                }
                else{
                    date.setText(day+" / "+month+" / "+year);

                }
            }

        }

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        time.setText(i +":" + i1);
    }



    public void saveNote(View view) {
        Note note = new Note();

        if(date.getText().toString().equalsIgnoreCase("pick the date")){
//            Toast.makeText(this,,Toast.LENGTH_LONG).show();
//            note.se
            //TODO validare notilor inserate : 1.data sa fie aleasa ora sa fie aleasa
        }
        note.setDate(date.getText().toString());
        note.setTime(time.getText().toString());
        note.setTitle(title.getText().toString());
        note.setDescription(descriprion.getText().toString());

        mySQLiteDataBase.insertNoteAndDate(note);
        finish();
//        Toast.makeText(this,mySQLiteDataBase.getAllDates().toString(),Toast.LENGTH_LONG);

    }
}
