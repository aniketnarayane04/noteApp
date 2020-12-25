package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    int s1;

    // declare require values
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "SimpleDB";
    private static final String TABLE_NAME = "SimpleTable";

    public Database(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    // declare table column names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_EDATE = "edate";
    private static final String KEY_ETIME = "etime";





    // creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDb = "CREATE TABLE "+TABLE_NAME+" ("+
                KEY_ID+" INTEGER PRIMARY KEY,"+
                KEY_TITLE+" TEXT,"+
                KEY_CONTENT+" TEXT,"+
                KEY_DATE+" TEXT,"+
                KEY_TIME+" TEXT," +
                KEY_EDATE+" TEXT," +
                KEY_ETIME+" TEXT"+" )";
        db.execSQL(createDb);
    }

    // upgrade db if older version exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion)
            return;

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public long addNote(Model_database note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();

        String upper = note.getTitle();
        String fletter = upper.substring(0,1);
        String rletters = upper.substring(1,upper.length());
        fletter = fletter.toUpperCase();
        upper = fletter+rletters;

        v.put(KEY_TITLE,upper);
        v.put(KEY_CONTENT,note.getContent());
        v.put(KEY_DATE,note.getDate());
        v.put(KEY_TIME,note.getTime());

        // inserting data into db
        long ID = db.insert(TABLE_NAME,null,v);
        return  ID;
    }

    public Model_database getNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_DATE,KEY_TIME};
        Cursor cursor=  db.query(TABLE_NAME,query,KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        return new Model_database(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
    }

    public List<Model_database> getAllNotes(){

        List<Model_database> allNotes = new ArrayList<>();
        String query;
        if(s1 == 2){
            query = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+KEY_TITLE+" DESC";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Model_database note = new Model_database();
                    note.setId(Long.parseLong(cursor.getString(0)));
                    note.setTitle(cursor.getString(1));
                    note.setContent(cursor.getString(2));
                    note.setDate(cursor.getString(3));
                    note.setTime(cursor.getString(4));
                    allNotes.add(note);
                }while (cursor.moveToNext());
            }
        }else if(s1 == 1){
            query = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+KEY_TITLE+" ASC";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Model_database note = new Model_database();
                    note.setId(Long.parseLong(cursor.getString(0)));
                    note.setTitle(cursor.getString(1));
                    note.setContent(cursor.getString(2));
                    note.setDate(cursor.getString(3));
                    note.setTime(cursor.getString(4));
                    allNotes.add(note);
                }while (cursor.moveToNext());
            }
        }else if(s1 == 3){
            query = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+KEY_ID+" DESC";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Model_database note = new Model_database();
                    note.setId(Long.parseLong(cursor.getString(0)));
                    note.setTitle(cursor.getString(1));
                    note.setContent(cursor.getString(2));
                    note.setDate(cursor.getString(3));
                    note.setTime(cursor.getString(4));
                    allNotes.add(note);
                }while (cursor.moveToNext());
            }
        }else if(s1 == 4){
            query = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+KEY_ID+" ASC";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Model_database note = new Model_database();
                    note.setId(Long.parseLong(cursor.getString(0)));
                    note.setTitle(cursor.getString(1));
                    note.setContent(cursor.getString(2));
                    note.setDate(cursor.getString(3));
                    note.setTime(cursor.getString(4));
                    allNotes.add(note);
                }while (cursor.moveToNext());
            }
        }



        return allNotes;

    }

    public int editNote(Model_database note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited Title: -> "+ note.getTitle() + "\n ID -> "+note.getId());
        c.put(KEY_TITLE,note.getTitle());
        c.put(KEY_CONTENT,note.getContent());
        c.put(KEY_DATE,note.getDate());
        c.put(KEY_TIME,note.getTime());
        return db.update(TABLE_NAME,c,KEY_ID+"=?",new String[]{String.valueOf(note.getId())});
    }

    void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    void sort(int s){
        s1 = s;
    }

}
