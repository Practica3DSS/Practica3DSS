package com.recypapp.recypapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.recypapp.recypapp.Comunications.Comunication;
import com.recypapp.recypapp.Comunications.data.Ingrediente;
import com.recypapp.recypapp.Comunications.data.ListReceta;
import com.recypapp.recypapp.Comunications.data.ListTag;
import com.recypapp.recypapp.Comunications.data.Tag;
import com.recypapp.recypapp.data.Receta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class EditRecetaActivity extends ActionBarActivity implements
        DialogInputBaseURL.OnFinishDialog {
    private static final int RESULT_LOAD_IMAGE = 1;
    public static final String ARG_POSITION = "position";
    private String mTitle;
    private int position;
    private List<Ingrediente> ingredientes;
    private List<Long> tags;
    private EditText nombre;
    private EditText description;
    private EditText comensales;
    private EditText duration;
    private ImageView image;
    private Bitmap imageBitMap;
    private UpdateInsertReceta mAuthTask = null;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_receta);

        Bundle b = getIntent().getExtras();

        position = b.getInt(ARG_POSITION);
        nombre = (EditText)findViewById(R.id.recype_name);
        description = (EditText)findViewById(R.id.description);
        comensales = (EditText)findViewById(R.id.nComensales);
        duration = (EditText)findViewById(R.id.duration);
        image = (ImageView)findViewById(R.id.imgView);

        InputFilter timeFilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                if (source.length() == 0) {
                    return null;// deleting, keep original editing
                }
                String result = "";
                result += dest.toString().substring(0, dstart);
                result += source.toString().substring(start, end);
                result += dest.toString().substring(dend, dest.length());

                if (result.length() > 5) {
                    return "";// do not allow this edit
                }
                boolean allowEdit = true;
                char c;
                if (result.length() > 0) {
                    c = result.charAt(0);
                    allowEdit &= (c >= '0' && c <= '9');
                }
                if (result.length() > 1) {
                    c = result.charAt(1);
                    allowEdit &= (c >= '0' && c <= '9');
                }
                if (result.length() > 2) {
                    c = result.charAt(2);
                    allowEdit &= (c == ':');
                }
                if (result.length() > 3) {
                    c = result.charAt(3);
                    allowEdit &= (c >= '0' && c <= '5');
                }
                if (result.length() > 4) {
                    c = result.charAt(4);
                    allowEdit &= (c >= '0' && c <= '9');
                }
                return allowEdit ? null : "";
            }
        };
        InputFilter timeFilters[] = new InputFilter[1];
        timeFilters[0] = timeFilter;

        duration.setFilters(timeFilters);

        Receta receta_e = ((Globals) getApplication()).getReceta_e();

        if (position < 0) {
            mTitle = getString(R.string.title_activity_create_receta);
        } else {
            mTitle = getString(R.string.title_activity_edit_receta);
        }

        if(receta_e != null || position >= 0) {
            Receta receta;

            if(receta_e != null) {
                receta = receta_e;
                ((Globals) getApplication()).setReceta_e(null);
            }
            else
                receta = ((Globals) getApplication()).getRecetas().get(position);

            nombre.setText(receta.getNombre());
            description.setText(receta.getDescription());
            comensales.setText(Integer.toString(receta.getCantidad()));

            String offSetH = "";

            if (receta.getDur_hor() < 10)
                offSetH = "0";

            String offSetMin = "";

            if (receta.getDur_min() < 10)
                offSetMin = "0";

            duration.setText(offSetH + Integer.toString(receta.getDur_hor()) + ":" +
                    offSetMin + Integer.toString(receta.getDur_min()));

            imageBitMap = receta.getImg();

            if (receta.getImg() != null)
                image.setImageBitmap(receta.getImg());

            ingredientes = receta.getIngredientes();
            tags = receta.getTags();
        }
        else{
            ingredientes = new ArrayList<Ingrediente>();
            tags = new ArrayList<Long>();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mTitle);

    }

    public void SelectTags(View v){
        ((Globals)getApplication()).setReceta_e(SafeReceta());

        Intent intent = new Intent(this,  TagMultiSelectActivity.class);
        Bundle b = new Bundle();

        b.putInt(TagMultiSelectActivity.ARG_POSITION, position); //Your id
        intent.putExtras(b); //Put your id to your next Intent

        this.startActivity(intent);
    }

    public void modIngredientes(View v){
        ((Globals)getApplication()).setReceta_e(SafeReceta());

        Intent intent = new Intent(this,  EditarIngredientesActivity.class);
        Bundle b = new Bundle();

        b.putInt(EditarIngredientesActivity.ARG_POSITION, position); //Your id
        b.putBoolean(EditarIngredientesActivity.ARG_FATHER, true);
        intent.putExtras(b); //Put your id to your next Intent

        this.startActivity(intent);
    }

    private int ParseTime(String time){
        if(time.length() < 3)
            return Integer.decode(time) * 60;
        else if(time.length() == 3)
            return Integer.decode(time.substring(0, 2)) * 60;
        else
            return Integer.decode(time.substring(0, 2)) * 60 +
                    Integer.decode(time.substring(3, time.length()));
    }

    public void Confrimar(View v){
        // Reset errors.
        nombre.setError(null);
        description.setError(null);
        comensales.setError(null);
        duration.setError(null);

        // Store values at the time of the login attempt.
        String nombre_text = nombre.getText().toString();
        String description_text = description.getText().toString();
        String comensales_text = comensales.getText().toString();
        String duration_text = duration.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(nombre_text)){
            nombre.setError(getString(R.string.error_field_required));
            focusView = nombre;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(description_text)){
            description.setError(getString(R.string.error_field_required));
            focusView = description;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(comensales_text)) {
            comensales.setError(getString(R.string.error_field_required));
            focusView = comensales;
            cancel = true;
        }

        // Check for a valid nick.
        if (TextUtils.isEmpty(duration_text)) {
            duration.setError(getString(R.string.error_field_required));
            focusView = duration;
            cancel = true;
        }

        if(ingredientes.size() < 1){
            cancel = true;
            ShowDialogError("La receta ha de tener al menos un ingrediente.");
        }

        if(tags.size() < 1){
            cancel = true;
            ShowDialogError("La receta ha de tener al menos un tag.");
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if(focusView != null)
                focusView.requestFocus();

        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            ((Globals)getApplication()).setReceta_e(SafeReceta());

            mAuthTask = new UpdateInsertReceta((Globals) getApplication());
            mAuthTask.execute();
        }
    }

    private Receta SafeReceta(){
        String dur = duration.getText().toString();
        int min = 0;
        int h = 0;

        if(dur.length() > 0) {
            min = ParseTime(dur);
            h = min / 60;
            min -= h * 60;
        }

        int comensales_int = 0;
        String comensales_text = comensales.getText().toString();

        if(comensales_text.length() > 0)
            comensales_int = Integer.decode(comensales_text);

        Receta r = new Receta();

        r.setNombre(nombre.getText().toString());
        r.setDescription(description.getText().toString());
        r.setCantidad(comensales_int);
        r.setDur_min(min);
        r.setDur_hor(h);
        r.setImg(imageBitMap);
        r.setIngredientes(ingredientes);
        r.setTags(tags);

        return r;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_receta, menu);
        return true;
    }

    public void goGallery(View view){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            // String picturePath contains the path of selected Image

            ImageView imageView = (ImageView) findViewById(R.id.imgView);

            imageBitMap = BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
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

            b.putInt(MainActivity.ARG_SECTION, 1); //Your id
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

    public class UpdateInsertReceta extends AsyncTask<Void, Void, Integer> {
        protected Globals g;
        protected com.recypapp.recypapp.Comunications.data.Receta receta_read;
        private ListReceta recetas;
        private ListTag tagsL;

        public UpdateInsertReceta(Globals g) {
            this.g = g;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int response = -1;

            try{
                if(position < 0){
                    if(Comunication.InsertReceta(g.getBaseURL(),
                            g.getReceta_e().ConvertRecetaToRecetaC(g.getUser().getId()))){
                        recetas = Comunication.GetRecetasUsuario(
                                ((Globals) getApplication()).getBaseURL(),g.getUser().getId());

                        response = 1;
                    }
                    else
                        response = 0;
                }
                else{
                    g.getReceta_e().setId(g.getRecetas().get(position).getId());

                    if(Comunication.UpdateReceta(g.getBaseURL(),
                            g.getReceta_e().ConvertRecetaToRecetaC(g.getUser().getId()),
                            g.getUser().getPassword())){
                        recetas = Comunication.GetRecetasUsuario(
                                ((Globals) getApplication()).getBaseURL(),g.getUser().getId());

                        response = 1;
                    }
                    else
                        response = 0;
                }
            }
            catch(Exception e){
                response = 0;
            }

            try{
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
            }
            catch(Exception e){
                response = -1;
            }

            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(EditRecetaActivity.this);
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

                if(recetas != null){
                    if(recetas.getList() != null){
                        for(com.recypapp.recypapp.Comunications.data.Receta rc: recetas.getList()){
                            Receta r = new Receta();

                            r.ConvertRecetaCToReceta(rc);
                            recetas_data.add(r);
                        }
                    }
                }

                ((Globals)getApplication()).setReceta_e(null);

                Intent intent = new Intent(EditRecetaActivity.this,  MainActivity.class);
                Bundle b = new Bundle();

                b.putInt(MainActivity.ARG_SECTION, 1); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else if(success == 0){
                if(position < 0)
                    ShowDialogError("Error al insertar receta.");
                else
                    ShowDialogError("Error al actualizar receta receta. Password incorrecto.");
            }
            else{
                ShowDialogError("Error de conexión.");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            mProgressDialog.dismiss();
            ((Globals)getApplication()).setReceta_e(null);
        }
    }
}
