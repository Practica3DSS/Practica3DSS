package com.recypapp.recypapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.recypapp.recypapp.Comunications.Comunication;
import com.recypapp.recypapp.Comunications.data.Usuario;
import com.recypapp.recypapp.data.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class PerfilActivity extends ActionBarActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, DialogInputBaseURL.OnFinishDialog {
    private static final int RESULT_LOAD_IMAGE = 1;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private UserDeleteTask mAuthTaskDel = null;

    // UI references.
    private AutoCompleteTextView mNickView;
    private EditText mPasswordOldView;
    private EditText mPasswordView;
    private EditText mPasswordAgainView;
    private View mProgressView;
    private View mLoginFormView;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        setupActionBar();

        User user = ((Globals) getApplication()).getUser();

        // Set up the login form.
        mNickView = (AutoCompleteTextView) findViewById(R.id.nick);
        populateAutoComplete();
        mNickView.setText(user.getNick());

        ((TextView) findViewById(R.id.email)).setText(user.getEmail());

        mPasswordOldView = (EditText) findViewById(R.id.password_actual);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordAgainView = (EditText) findViewById(R.id.password_again);

        Button mEmailSignInButton = (Button) findViewById(R.id.modificarPerfil);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        if(user.getImg() != null)
            ((CircleImageView)findViewById(R.id.imgView)).setImageBitmap(user.getImg());
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void goGallery(View view){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public void GoMain(View view){
        Intent intent = new Intent(this, MainActivity.class);

        Bundle b = new Bundle();

        b.putInt(MainActivity.ARG_SECTION, -1); //Your id
        intent.putExtras(b); //Put your id to your next Intent

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

    public void GoLogin(View view){
        ((Globals) getApplication()).setUser(null);

        Intent intent = new Intent(this, LoginActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

    public void Delete(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirmar Baja...");

        // Setting Dialog Message
        alertDialog.setMessage("¿Seguro que desea darse de baja del servicio?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_action_remove_ac);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                boolean cancel = false;
                View focusView = null;
                String passwordOld = mPasswordOldView.getText().toString();

                // Reset errors.
                mNickView.setError(null);
                mPasswordOldView.setError(null);
                mPasswordView.setError(null);
                mPasswordAgainView.setError(null);

                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(passwordOld)){
                    mPasswordOldView.setError(getString(R.string.error_field_required));
                    focusView = mPasswordOldView;
                    cancel = true;
                }
                else if(!isPasswordValid(passwordOld)) {
                    mPasswordOldView.setError(getString(R.string.error_no_valid_password));
                    focusView = mPasswordOldView;
                    cancel = true;
                }

                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                } else {
                    // Show a progress spinner, and kick off a background task to
                    // perform the user login attempt.
                    showProgress(true);
                    mAuthTaskDel = new UserDeleteTask(passwordOld);
                    mAuthTaskDel.execute();
                }
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

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mNickView.setError(null);
        mPasswordOldView.setError(null);
        mPasswordView.setError(null);
        mPasswordAgainView.setError(null);

        // Store values at the time of the login attempt.
        String nick = mNickView.getText().toString();
        String passwordOld = mPasswordOldView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordAgain = mPasswordAgainView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password)){
            // Check for a valid password, if the user entered one.
            if (TextUtils.isEmpty(passwordAgain)){
                mPasswordAgainView.setError(getString(R.string.error_field_required));
                focusView = mPasswordAgainView;
                cancel = true;
            }
            else if (!password.equals(passwordAgain)){
                mPasswordView.setError(getString(R.string.error_incorrect_password_again));
                mPasswordAgainView.setError(getString(R.string.error_incorrect_password_again));
                focusView = mPasswordAgainView;
                cancel = true;
            }

            if(!isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_no_valid_password));
                focusView = mPasswordView;
                cancel = true;
            }
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passwordOld)){
            mPasswordOldView.setError(getString(R.string.error_field_required));
            focusView = mPasswordOldView;
            cancel = true;
        }
        else if(!isPasswordValid(passwordOld)) {
            mPasswordOldView.setError(getString(R.string.error_no_valid_password));
            focusView = mPasswordOldView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(password, nick, passwordOld);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 0;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> nicks = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            nicks.add(cursor.getString(ProfileQuery.NAME));
            cursor.moveToNext();
        }

        addNicksToAutoComplete(nicks);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void OnFinish() {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Nickname.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int NAME = 0;
        int IS_PRIMARY = 1;
    }

    private void addNicksToAutoComplete(List<String> nicksCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(PerfilActivity.this,
                        android.R.layout.simple_dropdown_item_1line, nicksCollection);

        mNickView.setAdapter(adapter);
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

            CircleImageView imageView = (CircleImageView) findViewById(R.id.imgView);
            User user = ((Globals) getApplication()).getUser();

            user.setImg(BitmapFactory.decodeFile(picturePath));
            imageView.setImageBitmap(user.getImg());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
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
            Intent intent = new Intent(this, MainActivity.class);
            Bundle b = new Bundle();

            b.putInt(MainActivity.ARG_SECTION, -1); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, intent);
            this.startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {
        private String mNick;
        private final String mPasswordOld;
        private String mPassword;

        UserLoginTask(String password, String nick, String passwordOld) {
            mPassword = password;
            mNick = nick;
            mPasswordOld = passwordOld;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try{
                User user = ((Globals) getApplication()).getUser();

                if(mNick.equals("")){
                    mNick = user.getNick();
                }

                if(mPassword.equals("")){
                    mPassword = user.getPassword();
                }

                user.setNick(mNick);
                user.setPassword(mPassword);
                usuario = user.ConvertUserToUsuario();//new Usuario(user.getId(), mNick, mPassword, user.getEmail(),
                        //new Imagen(user.getImg_id(), user.getImg_type(), user.getImg_name()));

                if(Comunication.UpdateUser(((Globals) getApplication()).getBaseURL(), usuario,
                        mPasswordOld)){
                    return 1;
                }
                else {
                    return 0;
                }
            }
            catch(Exception e){
                return -1;
            }
        }

        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
            showProgress(false);

            if (success == 1) {
                User user = ((Globals) getApplication()).getUser();

                user.setNick(usuario.getNick());
                user.setEmail(usuario.getEmail());
                user.setPassword(usuario.getPassword());
                user.setId(usuario.getIdUsuario());
                user.setImg_name(usuario.getImagen().getFileName());
                user.setImg_type(usuario.getImagen().getMimeType());
                user.setImg_id(usuario.getImagen().getId());

                ((Globals) getApplication()).setUser(user);

                GoMain(null);
            }
            else {
                mPasswordOldView.setError(getString(R.string.error_incorrect_password));
                mPasswordOldView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserDeleteTask extends AsyncTask<Void, Void, Integer> {
        private String mPassword;

        UserDeleteTask(String password) {
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                if (mPassword.equals(((Globals) getApplication()).getUser().getPassword())) {

                    Comunication.DeleteUser(((Globals) getApplication()).getBaseURL(),
                            ((Globals) getApplication()).getUser().getId());

                    return 1;
                }
                else
                    return 0;
            }
            catch(Exception e){
                return -1;
            }
        }

        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTaskDel = null;
            showProgress(false);

            if (success == 1) {
                GoLogin(null);
            }
            else if(success == 0){
                mPasswordOldView.setError(getString(R.string.error_incorrect_password));
                mPasswordOldView.requestFocus();
            }
            else{
                ShowDialogError("Error de conexión.");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTaskDel = null;
            showProgress(false);
        }
    }
}