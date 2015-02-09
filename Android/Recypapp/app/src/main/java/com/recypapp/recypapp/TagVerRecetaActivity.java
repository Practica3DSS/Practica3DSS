package com.recypapp.recypapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import static com.recypapp.recypapp.Util.SelecTagWithId;


public class TagVerRecetaActivity extends ActionBarActivity implements
        TagFragment.OnFragmentInteractionListener, DialogInputBaseURL.OnFinishDialog {
    public static final String ARG_POSITION = "position";
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_ver_receta);

        Bundle b = getIntent().getExtras();

        position = b.getInt(ARG_POSITION);

        Globals g = (Globals)getApplication();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container,
                        TagFragment.newInstance(SelecTagWithId(
                                g.getCosulta().getRecetas().get(position).getTags(), g.getTags())))
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tag_ver_receta, menu);
        return true;
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

        if(id == android.R.id.home) {
            Intent intent = new Intent(this, VerRecetaActivity.class);
            Bundle b = new Bundle();

            b.putInt(VerRecetaActivity.ARG_POSITION, position); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, intent);
            this.startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int id) {

    }

    @Override
    public void OnFinish() {

    }
}
