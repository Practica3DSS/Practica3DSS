 package com.recypapp.recypapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.recypapp.recypapp.data.Receta;


public class VerRecetaActivity extends ActionBarActivity implements
        DialogInputBaseURL.OnFinishDialog {
    public static final String ARG_POSITION = "position";
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_receta);

        Bundle b = getIntent().getExtras();

        position = b.getInt(ARG_POSITION);

        //Receta r = ((Globals) getApplication()).getRecetas().get(position);
        Receta r = ((Globals) getApplication()).getCosulta().getRecetas().get(position);

        TextView r_name = ((TextView) findViewById(R.id.recype_name));
        TextView u_name = ((TextView) findViewById(R.id.nick));
        TextView comensales = ((TextView) findViewById(R.id.nComensales));
        TextView duration = ((TextView) findViewById(R.id.duration));
        TextView description = ((TextView) findViewById(R.id.description));

        r_name.setText(r_name.getText().toString() + r.getNombre());
        u_name.setText(u_name.getText().toString() + r.getUser_nick());
        comensales.setText(comensales.getText().toString() + Integer.toString(r.getCantidad()));
        duration.setText(duration.getText().toString() + Integer.toString(r.getDur_hor()) +
                " horas y " + Integer.toString(r.getDur_min()) + " minutos");
        description.setText(r.getDescription());

        if(r.getImg() != null)
            ((ImageView) findViewById(R.id.imgView)).setImageBitmap(r.getImg());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ver_receta, menu);
        return true;
    }

    public void modIngredientes(View v){
        Intent intent = new Intent(this,  EditarIngredientesActivity.class);
        Bundle b = new Bundle();

        b.putInt(EditarIngredientesActivity.ARG_POSITION, position); //Your id
        b.putBoolean(EditarIngredientesActivity.ARG_FATHER, false);
        intent.putExtras(b); //Put your id to your next Intent

        this.startActivity(intent);
    }

    public void verTags(View v){
        Intent intent = new Intent(this, TagVerRecetaActivity.class);
        Bundle b = new Bundle();

        b.putInt(TagVerRecetaActivity.ARG_POSITION, position); //Your id
        intent.putExtras(b); //Put your id to your next Intent

        this.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            DialogInputBaseURL d = new DialogInputBaseURL((Globals)getApplication(), this, this);
            d.getAlertDialog().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnFinish() {

    }
}
