package com.recypapp.recypapp;

import android.util.Log;

import com.recypapp.recypapp.Comunications.data.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silt on 8/02/15.
 */
public class Util {
    public static List<Tag> SelecTagWithId(List<Long> ids, List<Tag> tags){
        List<Tag> selected_tags = new ArrayList<Tag>();

        for(int i = 0, j = 0; i < ids.size() && j < tags.size();){
            if(ids.get(i) == tags.get(j).getIdTag()){
                selected_tags.add(tags.get(j));
                ++i;
                ++j;
            }
            else if(ids.get(i) < tags.get(j).getIdTag()){
                ++i;
            }
            else{
                ++j;
            }
        }

        return selected_tags;
    }

    public static List<Boolean> SelecTagWithIdPosition(List<Long> ids, List<Tag> tags){
        List<Boolean> selected_tags = new ArrayList<Boolean>();
        int j = 0;

        for(int i = 0; i < ids.size() && j < tags.size();){
            if(ids.get(i) == tags.get(j).getIdTag()){
                selected_tags.add(true);
                ++i;
                ++j;
            }
            else if(ids.get(i) < tags.get(j).getIdTag()){
                ++i;
            }
            else{
                ++j;
                selected_tags.add(false);
            }
        }

        for(int i = j; i < tags.size(); ++i){
            selected_tags.add(false);
        }

        Log.i("Main", Boolean.toString(tags.size() == selected_tags.size()));

        return selected_tags;
    }
}
