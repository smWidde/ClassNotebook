package com.example.classnotebook;

import android.util.Log;

import com.example.classnotebook.Note;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class NoteXmlParser {
    private ArrayList<Note> notes;
    public NoteXmlParser(){
        notes = new ArrayList<>();
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public boolean parse(XmlPullParser xpp){
        boolean status = true;
        Note currentNote = null;
        boolean inEntity = false;
        String textValue = "";

        try {
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagName= xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:{
                        if("note".equalsIgnoreCase(tagName)){
                            inEntity = true;
                            currentNote = new Note();
                        }
                        break;
                    }
                    case XmlPullParser.TEXT:{
                        textValue = xpp.getText();
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        if(inEntity) {
                            if ("note".equalsIgnoreCase(tagName)) {
                                inEntity = false;
                                notes.add(currentNote);
                            } else if ("title".equalsIgnoreCase(tagName)) {
                                currentNote.Title = (textValue);
                            } else if ("text".equalsIgnoreCase(tagName)) {
                                currentNote.Text = (textValue);
                            }
                            else if ("year".equalsIgnoreCase(tagName)) {
                                currentNote.Date.set(Calendar.YEAR, Integer.parseInt(textValue));
                            } else if ("month".equalsIgnoreCase(tagName)) {
                                currentNote.Date.set(Calendar.MONTH, Integer.parseInt(textValue));
                            }else if ("day".equalsIgnoreCase(tagName)) {
                                currentNote.Date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(textValue));
                            }
                        }
                        break;
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            status = false;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }
}
