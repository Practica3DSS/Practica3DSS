package com.recypapp.recypapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import com.recypapp.recypapp.Comunications.data.Tag;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.recypapp.recypapp.Util.SelecTagWithIdPosition;

public class TagMultiSelectActivity extends ActionBarActivity implements
        DialogInputBaseURL.OnFinishDialog{
    public static final String ARG_POSITION = "position";
    private ListView listView;
    private int position;
    ArrayAdapter<Tag> adapter;

    private ActualizeTagsSearch mAuthTask = null;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_multi_select);

        Bundle b = getIntent().getExtras();

        position = b.getInt(ARG_POSITION);

        listView = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<Tag>(this,
                android.R.layout.simple_list_item_multiple_choice, new ArrayList<Tag>());
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        mAuthTask = new ActualizeTagsSearch((Globals)getApplication());

        mAuthTask.execute();
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

    public void PopulateList(){
        adapter = new ArrayAdapter<Tag>(this,
                android.R.layout.simple_list_item_multiple_choice, ((Globals)getApplication()).getTags());
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        List<Boolean> positions = SelecTagWithIdPosition(((Globals) getApplication()).getReceta_e()
                .getTags(), ((Globals) getApplication()).getTags());

        for(int i = 0; i < positions.size(); ++i){
            listView.setItemChecked(i, positions.get(i));
        }
    }

    public void Aceptar(View view){
        List<Long> ids = new ArrayList<Long>();
        SparseBooleanArray checked = listView.getCheckedItemPositions();

        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);

            if (checked.valueAt(i))
                ids.add(adapter.getItem(position).getIdTag());
        }

        ((Globals)getApplication()).getReceta_e().setTags(ids);

        Intent intent = new Intent(this, EditRecetaActivity.class);
        Bundle b = new Bundle();

        b.putInt(EditRecetaActivity.ARG_POSITION, position); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NavUtils.navigateUpTo(this, intent);
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tag_multi_select, menu);
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
            Intent intent = new Intent(this, EditRecetaActivity.class);
            Bundle b = new Bundle();

            b.putInt(EditRecetaActivity.ARG_POSITION, position); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, intent);
            this.startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnFinish() {

    }

    class ActualizeTagsSearch extends ActualizeTags{
        public ActualizeTagsSearch(Globals g) {
            super(g);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(TagMultiSelectActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Actualizando base de datos");
            // Set progressdialog message
            mProgressDialog.setMessage("Cargando...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            mProgressDialog.dismiss();

            if (success) {
                g.setTags(tags.getList());

                PopulateList();
            }
            else{
                ShowDialogError("Error de conexión.");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            mProgressDialog.dismiss();
        }
    }
}
