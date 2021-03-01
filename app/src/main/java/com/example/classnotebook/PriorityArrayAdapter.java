package com.example.classnotebook;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class PriorityArrayAdapter extends ArrayAdapter<Note> {
    private final Activity context;
    private final List<Note> list;
    public PriorityArrayAdapter(Activity context, List<Note> list)
    {
        super(context,R.layout.my_base_listview_layout, list);
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.my_base_listview_layout, null, true);
        Note n = list.get(position);
        TextView tv = rowView.findViewById(R.id.listView_textView);
        tv.setText(n.Title);
        switch (n.Priority)
        {
            case 0:
            {
                tv.setTextColor(0xFF3ca832);
                break;
            }
            case 1:
            {
                tv.setTextColor(0xFFa8a832);
                break;
            }
            case 2:
            {
                tv.setTextColor(0xFFa83232);
                break;
            }
        }
        return rowView;
    }

}
