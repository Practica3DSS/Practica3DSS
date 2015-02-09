package com.recypapp.recypapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.recypapp.recypapp.Comunications.Comunication;
import com.recypapp.recypapp.Comunications.data.ListReceta;
import com.recypapp.recypapp.Comunications.data.ListTag;
import com.recypapp.recypapp.Comunications.data.Tag;
import com.recypapp.recypapp.data.Receta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        RecetaFragment.OnFragmentInteractionListener, DialogInputBaseURL.OnFinishDialog {
    public static final String ARG_SECTION = "section";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private ArrayList<Fragment> fragments;

    private int initSection = 0;

    private boolean mode = false;

    private DeleteReceta mAuthTask = null;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();

        initSection = b.getInt(ARG_SECTION);

        super.onCreate(savedInstanceState);
        Globals g = (Globals)getApplication();

        fragments = new ArrayList<Fragment>();

        //fragments.add(PlaceholderFragment.newInstance(1));
        fragments.add(SearchFragment.newInstance(1));
        fragments.add(RecetaFragment.newInstance(2, ((Globals) getApplication()).getRecetas()));

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragments.get(position))
                .commit();
    }

    @Override
    public int InitSection() {
        return initSection;
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                mode = false;
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);

            if(mTitle.equals(getString(R.string.title_section2))) {
                MenuItem item = menu.findItem(R.id.action_add);
                item.setVisible(true);
                item = menu.findItem(R.id.action_remove);
                item.setVisible(true);
            }

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        if(mTitle.equals(getString(R.string.title_section2))){
            MenuItem item = menu.findItem(R.id.action_remove);

            if(!mNavigationDrawerFragment.isDrawerOpen()) {
                if (mode) {
                    item.setIcon(R.drawable.ic_action_remove_ac);
                } else {
                    item.setIcon(R.drawable.ic_action_remove);
                }
            }
        }

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
        else if(id == R.id.action_add){
            if(mode){
                mode = false;
                this.invalidateOptionsMenu();
            }

            //Toast.makeText(this, "Add receta", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, EditRecetaActivity.class);

            Bundle b = new Bundle();
            b.putInt(EditRecetaActivity.ARG_POSITION, -1); //Your id
            intent.putExtras(b); //Put your id to your next Intent

            this.startActivity(intent);
        }else if(id == R.id.action_remove){
            mode = !mode;
            this.invalidateOptionsMenu();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int pos, long id) {
        if(mode){
            final long rec_id = id;

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            alertDialog.setTitle("Confirmar Borrado...");

            // Setting Dialog Message
            alertDialog.setMessage("¿Seguro que desea eliminar la receta?");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.ic_action_remove_ac);

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    mAuthTask = new DeleteReceta((Globals) getApplication(), rec_id);
                    mAuthTask.execute();
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
        else {
            Intent intent = new Intent(this, EditRecetaActivity.class);

            Bundle b = new Bundle();
            b.putInt(EditRecetaActivity.ARG_POSITION, pos); //Your id
            intent.putExtras(b); //Put your id to your next Intent

            this.startActivity(intent);
        }
    }

    @Override
    public void OnFinish() {

    }

    public void ShowDialogError(String error){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(error)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public class DeleteReceta extends AsyncTask<Void, Void, Integer> {
        private Globals g;
        private long r_id;
        private ListReceta recetas;
        private ListTag tagsL;

        public DeleteReceta(Globals g, long id) {
            this.g = g;
            r_id = id;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int response = -1;

            try {
                if (Comunication.DeleteReceta(g.getBaseURL(), r_id)) {
                    recetas = Comunication.GetRecetasUsuario(
                            ((Globals) getApplication()).getBaseURL(), g.getUser().getId());
                    response = 1;
                } else
                    response = 0;
            } catch (Exception e) {
                response = 0;
            }

            try {
                tagsL = Comunication.GetTags(((Globals) getApplication()).getBaseURL());

                Collections.sort(tagsL.getList(), new Comparator<Tag>() {
                    @Override
                    public int compare(Tag o1, Tag o2) {
                        if (o1.getIdTag() < o2.getIdTag())
                            return -1;
                        else if (o1.getIdTag() > o2.getIdTag())
                            return +1;
                        else
                            return 0;
                    }
                });
            } catch (Exception e) {
                response = -1;
            }

            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Ralizando operación");
            // Set progressdialog message
            mProgressDialog.setMessage("Trabajando...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
            mProgressDialog.dismiss();

            if (success == 1) {
                ((Globals) getApplication()).setRecetas(new ArrayList<Receta>());
                ((Globals) getApplication()).setTags(tagsL.getList());
                ArrayList<Receta> recetas_data = ((Globals) getApplication()).getRecetas();

                if (recetas != null) {
                    if (recetas.getList() != null) {
                        for (com.recypapp.recypapp.Comunications.data.Receta rc : recetas.getList()) {
                            Receta r = new Receta();

                            r.ConvertRecetaCToReceta(rc);
                            recetas_data.add(r);
                        }
                    }
                }

                ((RecetaFragment)fragments.get(1)).setRecetas(recetas_data);
            } else if (success == 0) {
                ShowDialogError("Error al eliminar receta.");
            } else {
                ShowDialogError("Error de conexión.");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            mProgressDialog.dismiss();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
