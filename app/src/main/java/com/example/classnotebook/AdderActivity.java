package com.example.classnotebook;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Calendar;

public class AdderActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static final int PICK_IMAGE = 1;
    private Calendar cal;
    private TextView date;
    private EditText title;
    private EditText text;
    private String mode;
    private Spinner spin;
    private @Nullable Integer id;
    private static boolean READ_EXTERNAL_STORAGE_GRANTED = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.adder_layout);
        cal = Calendar.getInstance();
        date = findViewById(R.id.textViewDate);
        title = findViewById(R.id.titleEditText);
        text = findViewById(R.id.editTextNote);
        spin = findViewById(R.id.spinner);
        mode = "a";
        Bundle extra = getIntent().getExtras();
        if(extra!=null)
        {
            Note note = null;
            id = extra.getInt("id");
            if(id!=0)
            {
                NotesDatabase nd = new NotesDatabase(getBaseContext());
                note = nd.notes.get(id-1);
            }
            note = (Note)extra.get("note");
            if(note!=null)
            {
                title.setText(note.Title);
                text.setText(note.Text);
                cal.set(Calendar.YEAR, note.Date.get(Calendar.YEAR));
                cal.set(Calendar.MONTH, note.Date.get(Calendar.MONTH));
                cal.set(Calendar.DAY_OF_MONTH, note.Date.get(Calendar.DAY_OF_MONTH));
                spin.setSelection(note.Priority);
                id = note.Id;
                mode = "e";
            }
        }
        SetTime();
        int hasReadContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(hasReadContactPermission == PackageManager.PERMISSION_GRANTED){
            READ_EXTERNAL_STORAGE_GRANTED = true;
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_CONTACTS);
        }
    }
    DatePickerDialog.OnDateSetListener dp = (view, year, month, day) -> {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        SetTime();
    };
    private void SetTime()
    {
        String text = DateUtils.formatDateTime(this, cal.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE);
        date.setText(text);
    }
    public void ChangeDateClick(View view)
    {
        new DatePickerDialog(this, dp, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }
    public void AddClick(View view)
    {
        Note n = new Note(title.getText().toString(), text.getText().toString(), cal, spin.getSelectedItemPosition());
        if(mode.equals("e"))
            n.Id = id;
        new OkDialogFragment(()->{
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("note", n);
            i.putExtra("mode", mode);
            startActivity(i);
        }).show(getFragmentManager(), "ok");
    }
    public void ClearClick(View view)
    {
        cal = Calendar.getInstance();
        SetTime();
        title.setText("");
        text.setText("Write here!");
    }
    public void CancelClick(View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void AddMedia(View view)
    {
        NotesDatabase nd = new NotesDatabase(getBaseContext());
        Intent takePhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        try {
            startActivityForResult(takePhotoIntent, PICK_IMAGE);
        } catch (ActivityNotFoundException ex){
            ex.printStackTrace();
        }
    }
    public void ShowMedia(View view)
    {
        Intent i = new Intent(this, GalleryActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            Uri uri = data.getData();
            NotesDatabase nd = new NotesDatabase(getBaseContext());
            nd.addMedia(id, uri);
        }
    }
}
