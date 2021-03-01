package com.example.classnotebook;

import androidx.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;

public class Note implements Serializable {
    public int Id;
    public String Title;
    public Calendar Date;
    public String Text;
    public Integer Priority;
    public Note()
    {
        Date = Calendar.getInstance();
        Id=0;
    }
    public Note(String Title, String Text, Calendar Date, Integer Priority)
    {
        this.Date = Calendar.getInstance();
        this.Date.set(Calendar.YEAR, Date.get(Calendar.YEAR));
        this.Date.set(Calendar.MONTH, Date.get(Calendar.MONTH));
        this.Date.set(Calendar.DAY_OF_MONTH, Date.get(Calendar.DAY_OF_MONTH));
        this.Title = Title;
        this.Text = Text;
        this.Priority = Priority;
        Id=0;
    }
    public Note(byte[] bytes)
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            Note n = (Note)o;
            this.Title = n.Title;
            this.Date = Calendar.getInstance();
            this.Date.set(Calendar.YEAR, n.Date.get(Calendar.YEAR));
            this.Date.set(Calendar.MONTH, n.Date.get(Calendar.MONTH));
            this.Date.set(Calendar.DAY_OF_MONTH, n.Date.get(Calendar.DAY_OF_MONTH));
            this.Text = n.Text;
            this.Priority = n.Priority;
            this.Id = n.Id;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
    }
    public byte[] getBytes()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            byte[] yourBytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
            }
        }
        return bos.toByteArray();
    }
    @NonNull
    @Override
    public String toString() {
        return Title;
    }
    public String toXML()
    {
        String text = "";
        text +="<note>"
                +"<title>"+
                Title+
                "</title>"
                +"<text>"+
                Text+
                "</text>"
                +"<year>"+
                Date.get(Calendar.YEAR)+
                "</year>"
                +"<month>"+
                Date.get(Calendar.MONTH)+
                "</month>"
                +"<day>"+
                Date.get(Calendar.DAY_OF_MONTH)+
                "</day>"
                +"</note>";
        return text;
    }

}
