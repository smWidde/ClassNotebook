package com.example.classnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Note> arr;
    private ArrayAdapter<Note> arr_adap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NoteXmlParser nxp = new NoteXmlParser();

        if(nxp.parse(getResources().getXml(R.xml.notes)))
        {
            arr = nxp.getNotes();
        }
        else
        {
            arr = new ArrayList<>();
        }
        Bundle extra= getIntent().getExtras();
        if(extra!=null)
        {
            Note note = (Note)extra.get("note");
            if(note!=null)
            {
                arr.add(note);
            }
        }
        arr_adap = new ArrayAdapter<Note>(this, R.layout.support_simple_spinner_dropdown_item, arr);
        ListView lv = findViewById(R.id.list);
        lv.setAdapter(arr_adap);
    }
    public void addNoteClick(View view)
    {
        Intent i = new Intent(this, AdderActivity.class);
        getResources().getXml(R.xml.notes);
        startActivity(i);
    }
}