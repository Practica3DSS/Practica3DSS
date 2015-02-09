package com.recypapp.recypapp;

import android.os.AsyncTask;

import com.recypapp.recypapp.Comunications.Comunication;
import com.recypapp.recypapp.Comunications.data.ListTag;
import com.recypapp.recypapp.Comunications.data.Tag;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by silt on 8/02/15.
 */
public class ActualizeTags extends AsyncTask<Void, Void, Boolean> {
    protected Globals g;
    protected ListTag tags;

    public ActualizeTags(Globals g) {
        this.g = g;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try{
            tags = Comunication.GetTags(g.getBaseURL());
            Collections.sort(tags.getList(), new Comparator<Tag>() {
                @Override
                public int compare(Tag o1, Tag o2) {
                    if(o1.getIdTag() < o2.getIdTag())
                        return -1;
                    else if(o1.getIdTag() > o2.getIdTag())
                        return +1;
                    else
                        return 0;
                }
            });

            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
