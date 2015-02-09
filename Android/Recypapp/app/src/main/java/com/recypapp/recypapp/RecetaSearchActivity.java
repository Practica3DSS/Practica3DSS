package com.recypapp.recypapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class RecetaSearchActivity extends ActionBarActivity implements
        RecetaFragment.OnFragmentInteractionListener, DialogInputBaseURL.OnFinishDialog {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_search);

        Globals g = (Globals)getApplication();

        if(g.getCosulta() != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, RecetaFragment.newInstance(-1,
                            g.getCosulta().getRecetas()))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receta_search, menu);
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
            Intent intent = new Intent(this,  MainActivity.class);
            Bundle b = new Bundle();

            b.putInt(MainActivity.ARG_SECTION, 0); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, intent);
            this.startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int pos, long id) {
        Intent intent = new Intent(this, VerRecetaActivity.class);

        Bundle b = new Bundle();
        b.putInt(VerRecetaActivity.ARG_POSITION, pos); //Your id
        intent.putExtras(b); //Put your id to your next Intent

        this.startActivity(intent);
    }

    @Override
    public void OnFinish() {

    }
}
