package com.vreader.utils;

import android.content.ContentValues;  
import android.content.Context;  
import android.database.Cursor;  
import android.database.sqlite.SQLiteDatabase;  
import android.database.sqlite.SQLiteOpenHelper;  
public class DBHelperB extends SQLiteOpenHelper {  
    private static final String DB_NAME = "latest.db";  
    private static final String TBL_NAME = "vreader";  
    private static final String CREATE_TBL = " create table "  
            + " vreader(_id integer primary key autoincrement,title text,time text, content text) ";  
      
    private SQLiteDatabase db;  
    public DBHelperB(Context c) {  
        super(c, DB_NAME, null, 1);  
    }  
    
    @Override  
    public void onCreate(SQLiteDatabase db) {  
        this.db = db;  
        db.execSQL(CREATE_TBL);  
    }  
    
    public void insert(ContentValues values) {  
        SQLiteDatabase db = getWritableDatabase();  
        db.insert(TBL_NAME, null, values);  
        db.close();  
    }  
    
    public Cursor query() {  
        SQLiteDatabase db = getWritableDatabase();  
        Cursor c = db.query(TBL_NAME, null, null, null, null, null, null);  
        return c;  
    } 
  
    
    public void del(int id) {  
        if (db == null)  
            db = getWritableDatabase();  
        db.delete(TBL_NAME, "_id=?", new String[] { String.valueOf(id) });  
    }  
    
    public void del() {  
        if (db == null)  
            db = getWritableDatabase();  
        db.delete(TBL_NAME, null, null); 
    } 
    
    public void close() {  
        if (db != null)  
            db.close();  
    }  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
    	
    }  
} 