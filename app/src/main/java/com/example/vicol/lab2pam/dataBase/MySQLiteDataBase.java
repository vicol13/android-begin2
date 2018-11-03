package com.example.vicol.lab2pam.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.vicol.lab2pam.domain.Description;
import com.example.vicol.lab2pam.domain.DescriptionAndNoteInterface;

import com.example.vicol.lab2pam.domain.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySQLiteDataBase  extends SQLiteOpenHelper {
    private final static String NAME_DB = "note_db";
    private final static String NOTES_TABLE="note";
    private final static String DATES_TABLE="date";
//    private final static String NEW_DATE_TABLE = "new_date";
//    private final static int DB_VERSION = 2 ;

    private Context context;
    private SQLiteDatabase sqLiteDatabase;



    public MySQLiteDataBase (Context context){
        super(context,NAME_DB,null,6);
        this.context = context;
        /**
         *  to know ::
         *              when i change version in super construtor
         *              it will call method 'onUpgrade()' wich will drop whole database
         *              and will call method 'onCreate()' wich will refactor database
         *              //if i need changes just change structure in onCreate and change version in constructor
         *
         *              //TODO refactor in future , to create database utils and remove most of functions from this class
         */


    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL("Create table " + DATES_TABLE + "(" +
                    "_id_date integer primary key autoincrement," +
                    "date text," +
                    "constraint unique_date unique(date) )");



            sqLiteDatabase.execSQL("Create table " + NOTES_TABLE + "(" +
                    "_id_note integer primary key ," +
                    "_id_date integer," +
                    "title text," +
                    "description text," +
                    "icon_path text," +
                    "notification_time text, " +
                    "foreign key (_id_date) references " + DATES_TABLE + "(_id_date)) " );
//                    " constraint uniqu_note unique(_id_note) ");
        }
        catch (Exception e ){
            Log.i("DataBase Eror ",e.getMessage());
            Toast.makeText(context,"creating DB eror",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTES_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATES_TABLE);
        onCreate(sqLiteDatabase);

    }

    /**
     * |
     * |        working with inserting of notes and date
     * |
     * V
     */

    private void insertDate(String recivedDate){
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("insert or ignore into  " + DATES_TABLE+"(date) " +
                " values('"+recivedDate+"')" );
    }

    private void insertNote(Note note ){
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("insert into " + NOTES_TABLE +"(_id_date,title,description,notification_time) " +
                "values ("+note.getDate_id()+",'"+note.getTitle()+"','"+note.getDescription()+"','"+note.getTime()+"' )" );

    }

    //validate date after this insert
    public void insertNoteAndDate(Note note){

        /**
         note pojo came as parameter already validated

         1. check if date exist in database

         if date doesn't exist : it will be inserted   //1.1

         2. fetch date  ID from database
         3. set id inside note pojo
         4. insert note in database
         */



        int id;

        if(checkDate(note.getDate())){ //1

            insertDate(note.getDate()); //1.1

        }

        id = getDateId(note.getDate()); //2
        note.setDate_id(id); // 3
        insertNote(note);  //4

    }

    //  function for validating date if date is not inserted in table with date:: will insert it
    private boolean checkDate(String recivedDate){
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+DATES_TABLE+" where date like '"+ recivedDate+"'",null);

        if(cursor.getCount() == 0 ){

            return true;

        }
        else {

            return  false;
        }


    }

    //return id of date for adding it to pojo
    public  int getDateId(String reciverDate ){
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select _id_date from "+DATES_TABLE+" where date like '"+ reciverDate+"'",null);
        cursor.moveToNext();
        return  Integer.valueOf(cursor.getString(0));  }

    /**
     *  |
     *  |   functions for AsynkTask loader
     *  |
     *  V
     */

    public List<String> getAllDates(){
        List<String> tempList = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select date from "+DATES_TABLE,null);

        while (c.moveToNext()){
            String tempStr = c.getString(0);
            tempList.add(tempStr);
        }

        return tempList;
    }

    public List<Note>getNotesForDate(String reciveDate){
        List<Note> tempList = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select nt.ROWID,title,description,notification_time " +
                " from " + NOTES_TABLE + " as nt" +
                " inner join "+DATES_TABLE+" as dt" +
                " on nt._id_date = dt._id_date " +
                " and date like '"+reciveDate+"'",null);


        Toast.makeText(context,"count " + c.getCount(),Toast.LENGTH_SHORT).show();

        while (c.moveToNext()){
            Note notePojo = new Note();

//                Toast.makeText(context,String.,Toast.LENGTH_LONG).show();
            notePojo.setNote_id(c.getInt(0));
            notePojo.setDate(reciveDate);
            notePojo.setTitle(c.getString(1));
            notePojo.setDescription(c.getString(2));
            notePojo.setTime(c.getString(3));
//            notePojo.setDate(c.getString(4));

            tempList.add(notePojo);

        }

        /**
         *  Select _id_note,title,description,time ,
         *  (select date from dates as d where d.id_date = n _id_date)
         *  from notes as n
         *
         */
        return tempList;
    }

    public void showDate(){

        sqLiteDatabase = getReadableDatabase();
        Cursor cr = sqLiteDatabase.rawQuery("select * from " +DATES_TABLE,null);

        String str1 = " ";
        while (cr.moveToNext()){
            str1 += cr.getString(1) + "\n";
        }
        Toast.makeText(context,str1,Toast.LENGTH_SHORT).show();
    }


    public HashMap<String,List<Note>> getHashMap(){
        /**
         *      return a multi map where key is date
         *      and rest is notes in that date
          */
        List<String> dates = this.getAllDates();
        HashMap<String,List<Note>> a = new HashMap<>();
        for (String date : dates){
            a.put(date,getNotesForDate(date));
        }

        return a;
    }

    public List<DescriptionAndNoteInterface>getList(){
        //convert hash map into generalizet list with title and note
        List<DescriptionAndNoteInterface> temp = new ArrayList<>();
        HashMap<String,List<Note>> tempHashMap = this.getHashMap();


        for(String key : tempHashMap.keySet()){
            DescriptionAndNoteInterface qq = new Description(key);
            temp.add(qq);
            for (Note note : tempHashMap.get(key)){
                DescriptionAndNoteInterface ee = note;
                temp.add(ee);

            }
        }


        return temp;
    }



}

