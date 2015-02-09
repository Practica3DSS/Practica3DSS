package com.recypapp.recypapp;

/**
 * Created by silt on 3/02/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;

import com.recypapp.recypapp.data.Receta;
import java.util.List;

public class RecetaListAdapter extends ArrayAdapter {
    private Context context;
    private boolean useList = true;

    public RecetaListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    /**
     * Holder for the list items.
     */
    private class ViewHolder{
        TextView titleText;
        TextView userText;
        TextView peopleText;
        TextView durationText;
        ImageView image;
    }
    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Receta item = (Receta)getItem(position);
        View viewToUse = null;

        // This block exists to inflate the settings list item conditionally based on whether
        // we want to support a grid or list view.
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            if(useList){
                viewToUse = mInflater.inflate(R.layout.recetas_list_item, null);
            }
            else {
                viewToUse = mInflater.inflate(R.layout.recetas_grid_item, null);
            }

            holder = new ViewHolder();
            holder.titleText = (TextView)viewToUse.findViewById(R.id.recype_name);
            holder.userText = (TextView)viewToUse.findViewById(R.id.user_name);
            holder.peopleText = (TextView)viewToUse.findViewById(R.id.cantidad);
            holder.durationText = (TextView)viewToUse.findViewById(R.id.dur);
            holder.image = (ImageView)viewToUse.findViewById(R.id.image);
            viewToUse.setTag(holder);
        }
        else {
            viewToUse = convertView;
            holder = (ViewHolder)
                    viewToUse.getTag();
        }

        holder.titleText.setText(item.getNombre());
        holder.userText.setText(context.getString(R.string.list_user_name) + " " + item.getUser_nick());
        holder.peopleText.setText(context.getString(R.string.list_cantidad) + " " +
                Integer.toString(item.getCantidad()));
        holder.durationText.setText(context.getString(R.string.list_hour) + " " +
                Integer.toString(item.getDur_hor()) + " " + context.getString(R.string.list_min) +
                " " + Integer.toString(item.getDur_min()));
        if(item.getImg() != null)
            holder.image.setImageBitmap(item.getImg());

        return viewToUse;
    }
}