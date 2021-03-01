package com.example.classnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private PriorityArrayAdapter arr_adap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotesDatabase nd = new NotesDatabase(getBaseContext());
        Bundle extra = getIntent().getExtras();
        if(extra!=null)
        {
            String mode = extra.getString("mode");
            Note note = (Note)extra.get("note");
            if(note!=null&&mode!=null)
            {
                if(mode.equals("a"))
                    nd.addNote(note);
                else if(mode.equals("e"))
                    nd.editNote(note.Id, note);
            }
        }
        ListView lv = findViewById(R.id.list);
        arr_adap = new PriorityArrayAdapter(this, nd.notes);
        lv.setAdapter(arr_adap);
        lv.setOnItemClickListener((parent, view, position, id)->{
            Intent i = new Intent(this, AdderActivity.class);
            Note tmp = nd.notes.get(position);
            Note n = new Note(tmp.Title, tmp.Text, tmp.Date, tmp.Priority);
            n.Id = tmp.Id;
            i.putExtra("note", n);
            startActivity(i);
        });
    }

    public void addNoteClick(View view)
    {
        Intent i = new Intent(this, AdderActivity.class);
        getResources().getXml(R.xml.notes);
        startActivity(i);
    }
}