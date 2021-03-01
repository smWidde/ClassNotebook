package com.example.classnotebook;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


public class NotesDatabase {
    private int CurrId;
    private int mediaId;
    SQLiteDatabase db;
    public ArrayList<Note> notes;
    public NotesDatabase(Context cw)
    {
        CurrId = 1;
        db = cw.openOrCreateDatabase("notes.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER, Title TEXT, Text TEXT, year INTEGER, month INTEGER, day INTEGER, priority INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS media (note_id INTEGER, uri TEXT);");
        getNotes();
        if(notes.size()>0)
            CurrId = notes.get(notes.size()-1).Id + 1;
    }
    public void editNote(Integer NoteId, Note note)
    {
        String title = note.Title;
        String text = note.Text;
        Integer year = note.Date.get(Calendar.YEAR);
        Integer month = note.Date.get(Calendar.MONTH);
        Integer day = note.Date.get(Calendar.DAY_OF_MONTH);
        Integer priority = note.Priority;
        db.execSQL("UPDATE notes SET title = '"+title+"', text = '"+text+"', year = '"+year+"', month = '"+day+"', day = '"+month+"', priority = '"+priority+"' WHERE id = "+NoteId);
        getNotes();
    }
    public void addNote(Note note)
    {
        String title = note.Title;
        String text = note.Text;
        Integer year = note.Date.get(Calendar.YEAR);
        Integer month = note.Date.get(Calendar.MONTH);
        Integer day = note.Date.get(Calendar.DAY_OF_MONTH);
        Integer priority = note.Priority;
        db.execSQL("INSERT INTO notes VALUES("+CurrId+", '"+title+"', '"+text+"', "+year+", "+month+", "+day+", "+priority+")");
        CurrId++;
        getNotes();
    }
    private ArrayList<Note> getNotes()
    {
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM notes", null);
        while (cursor.moveToNext()){
            Calendar date = Calendar.getInstance();
            date.set(Calendar.YEAR, cursor.getInt(3));
            date.set(Calendar.MONTH, cursor.getInt(4));
            date.set(Calendar.DAY_OF_MONTH, cursor.getInt(5));
            notes.add(new Note(cursor.getString(1), cursor.getString(2), date, cursor.getInt(6)){{Id = cursor.getInt(0);}});
        }
        cursor.close();
        this.notes = notes;
        return notes;
    }
    public void addMedia(int note_id, Uri uri)
    {
        db.execSQL("INSERT INTO media VALUES("+note_id+", '"+uri+"')");
    }
    public ArrayList<Uri> getMediaById(int note_id)
    {
        ArrayList<Uri> media = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM media WHERE note_id = "+note_id+";", null);
        while (cursor.moveToNext()){
            media.add(Uri.parse(cursor.getString(1)));
        }
        cursor.close();
        return media;
    }

}
