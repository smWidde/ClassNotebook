package com.example.classnotebook;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AdderActivity extends AppCompatActivity {
    private Calendar cal;
    private TextView date;
    private EditText title;
    private EditText text;

    public AdderActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.adder_layout);
        cal = Calendar.getInstance();
        date = findViewById(R.id.textViewDate);
        title = findViewById(R.id.titleEditText);
        text = findViewById(R.id.editTextNote);
        SetTime();
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
        Note n = new Note(title.getText().toString(), text.getText().toString(), cal);
        new OkDialogFragment(()->{
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("note", n);
            startActivity(i);
        }).show(getFragmentManager(), "ok");
    }
    public void ClearCLick(View view)
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
}
