package com.recypapp.recypapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.recypapp.recypapp.Comunications.data.Ingrediente;
import com.recypapp.recypapp.data.Receta;

import java.util.List;


public class EditarIngredientesActivity extends ActionBarActivity implements
        AdapterView.OnItemClickListener, DialogInputBaseURL.OnFinishDialog {
    public static final String ARG_POSITION = "position";
    public static final String ARG_FATHER = "father";
    private ListView listView;
    private List<Ingrediente> ingredientes;
    private int position;
    private boolean father;
    private boolean mode = false;
    ArrayAdapter<Ingrediente> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ingredientes);

        Bundle b = getIntent().getExtras();

        position = b.getInt(ARG_POSITION);
        father = b.getBoolean(ARG_FATHER);

        listView = (ListView) findViewById(R.id.list);

        if(father){
            Receta receta_e = ((Globals) getApplication()).getReceta_e();

            ingredientes = receta_e.getIngredientes();
        }
        else {
            ingredientes = ((Globals) getApplication()).getCosulta().getRecetas()
                    .get(position).getIngredientes();
        }

        adapter = new ArrayAdapter<Ingrediente>(this, android.R.layout.simple_list_item_1,
                ingredientes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar_ingredientes, menu);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(father) {
            MenuItem item = menu.findItem(R.id.action_add);

            item.setVisible(true);
            item = menu.findItem(R.id.action_remove);
            item.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        if(father){
            MenuItem item = menu.findItem(R.id.action_remove);

            if(mode){
                item.setIcon(R.drawable.ic_action_remove_ac);
            }
            else{
                item.setIcon(R.drawable.ic_action_remove);
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

        if(id == R.id.action_add){
            if(mode){
                mode = false;
                this.invalidateOptionsMenu();
            }

            createDialogModIngrediente(-1);

            return true;
        }

        if(id == R.id.action_remove){
            mode = !mode;
            this.invalidateOptionsMenu();

            return true;
        }

        if(id == android.R.id.home) {
            if(father) {
                Intent intent = new Intent(this, EditRecetaActivity.class);
                Bundle b = new Bundle();

                b.putInt(EditRecetaActivity.ARG_POSITION, position); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(this, intent);
                this.startActivity(intent);
            }
            else{
                Intent intent = new Intent(this, VerRecetaActivity.class);
                Bundle b = new Bundle();

                b.putInt(VerRecetaActivity.ARG_POSITION, position); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(this, intent);
                this.startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createDialogModIngrediente(int pos){
        String name = "";
        String cantidad = "";
        String title = "Nuevo ingrediente";

        if(pos > -1){
            Ingrediente i = ingredientes.get(pos);

            name = i.getNombre();
            cantidad = i.getCantidad();
            title = "Editar ingrediente";
        }

        final Dialog dialog = new Dialog(this);
        final int ing_id = pos;

        dialog.setContentView(R.layout.dialog_edit_ingrediente);
        dialog.setTitle(title);

        // set the custom dialog components - text, image and button
        final EditText text = (EditText) dialog.findViewById(R.id.name);

        text.setText(name);

        final EditText text2 = (EditText) dialog.findViewById(R.id.cantidad);

        text2.setText(cantidad);

        Button dialogButton = (Button) dialog.findViewById(R.id.buttonAc);

        dialogButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ing_id > -1){
                    Ingrediente i = ingredientes.get(ing_id);

                    if(!text.getText().toString().equals("") &&
                            !text2.getText().toString().equals("")) {
                        i.setNombre(text.getText().toString());
                        i.setCantidad(text2.getText().toString());
                    }
                }
                else{
                    if(!text.getText().toString().equals("") &&
                            !text2.getText().toString().equals("")) {
                        Ingrediente i = new Ingrediente(0, text.getText().toString(),
                                text2.getText().toString());

                        ingredientes.add(i);
                        adapter.notifyDataSetChanged();
                    }
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mode && father){
            final int in_id = position;

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            alertDialog.setTitle("Confirmar Borrado...");

            // Setting Dialog Message
            alertDialog.setMessage("¿Seguro que desea eliminar el ingrediente?");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.ic_action_remove_ac);

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    ingredientes.remove(in_id);
                    adapter.notifyDataSetChanged();
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
        else if(father){
            createDialogModIngrediente(position);
        }
    }

    @Override
    public void OnFinish() {

    }
}
