package com.example.classnotebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    private Integer index;
    private ArrayList<Uri> images;
    private TextView count_tv;
    private ImageView main_pic;
    private ImageView left_pic;
    private ImageView center_pic;
    private ImageView right_pic;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);

        images = new ArrayList<>();
        Bundle extra = getIntent().getExtras();
        id = extra.getInt("id");
        NotesDatabase nd = new NotesDatabase(getBaseContext());
        images = nd.getMediaById(id);
        index = 0;
        count_tv = findViewById(R.id.textView2);
        main_pic = findViewById(R.id.imageView6);
        left_pic = findViewById(R.id.leftImage);
        center_pic = findViewById(R.id.centerImage);
        right_pic = findViewById(R.id.rightImage);
        MakeChanges();
    }
    public void forwardImage(View v)
    {
        index = (index+1)%images.size();
        MakeChanges();
    }
    public void backImage(View v)
    {
        index -= 1;
        if(index<0)
            index+=images.size();
        MakeChanges();
    }
    private void MakeChanges()
    {
        count_tv.setText(((Integer)(index+1)).toString()+"/"+images.size());
        main_pic.setImageURI(images.get(index));
        left_pic.setImageURI(images.get(index-1+((index<1)?1:0)*images.size()));
        center_pic.setImageURI(images.get(index));
        right_pic.setImageURI(images.get((index+1)%images.size()));
    }
    public void getBack(View view)
    {
        Intent i = new Intent(this, AdderActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }
}