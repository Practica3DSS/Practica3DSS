package com.recypapp.recypapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.recypapp.recypapp.Comunications.Comunication;
import com.recypapp.recypapp.Comunications.data.ListTag;
import com.recypapp.recypapp.Comunications.data.Tag;
import com.recypapp.recypapp.Comunications.data.UserLoggin;
import com.recypapp.recypapp.Comunications.data.Usuario;
import com.recypapp.recypapp.data.Receta;
import com.recypapp.recypapp.data.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A login screen that offers login via email/password.
 */
public class RegistrationActivity extends ActionBarActivity implements LoaderCallbacks<Cursor>, DialogInputBaseURL.OnFinishDialog {
    private static final int RESULT_LOAD_IMAGE = 1;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mNickView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordAgainView;
    private View mProgressView;
    private View mLoginFormView;
    private Usuario usuario;
    private Bitmap imageBitMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupActionBar();

        // Set up the login form.
        mNickView = (AutoCompleteTextView) findViewById(R.id.nick);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mPasswordAgainView = (EditText) findViewById(R.id.password_again);
        mPasswordAgainView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        imageBitMap = null;
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
        Intent intent = new Intent(this,  MainActivity.class);
        Bundle b = new Bundle();
        b.putInt(MainActivity.ARG_SECTION, -1); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loggin, menu);
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

        return super.onOptionsItemSelected(item);
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
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPasswordAgainView.setError(null);

        // Store values at the time of the login attempt.
        String nick = mNickView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordAgain = mPasswordAgainView.getText().toString();

        boolean cancel = false;
        View focusView = null;

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

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)){
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        else if(!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_no_valid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid nick.
        if (TextUtils.isEmpty(nick)) {
            mNickView.setError(getString(R.string.error_field_required));
            focusView = mNickView;
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
            mAuthTask = new UserLoginTask(email, password, nick);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
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
        List<String> emails = new ArrayList<String>();
        List<String> nicks = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            nicks.add(cursor.getString(ProfileQuery.NICK));
            cursor.moveToNext();
        }

        addNicksEmailsToAutoComplete(emails, nicks);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void OnFinish() {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Nickname.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int NICK = 1;
        int IS_PRIMARY = 2;
    }

    private void addNicksEmailsToAutoComplete(List<String> emailAddressCollection, List<String> nicksCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(RegistrationActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<String>(RegistrationActivity.this,
                        android.R.layout.simple_dropdown_item_1line, nicksCollection);

        mEmailView.setAdapter(adapter);
        mNickView.setAdapter(adapter2);
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

            imageBitMap = BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(imageBitMap);
        }
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
        private final String mNick;
        private final String mEmail;
        private final String mPassword;
        private ListTag tags;

        UserLoginTask(String email, String password, String nick) {
            mEmail = email;
            mPassword = password;
            mNick =  nick;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try{
                User user = new User();

                user.setId(0);
                user.setNick(mNick);
                user.setEmail(mEmail);
                user.setPassword(mPassword);
                user.setImg_id(0);
                user.setImg_name("Test_user");
                user.setImg_type("image/jpg");
                user.setImg(imageBitMap);

                usuario = user.ConvertUserToUsuario();//new Usuario(0, mNick, mPassword, mEmail, new Imagen((long)0, "image/jpg", "Test_user"));

                if(Comunication.InsertUser(((Globals) getApplication()).getBaseURL(), usuario)){
                    usuario = Comunication.GetUserByEmail(((Globals) getApplication()).getBaseURL(),
                            new UserLoggin(mEmail, mPassword));

                    if(usuario.getIdUsuario() > 0){
                        tags = Comunication.GetTags(((Globals) getApplication()).getBaseURL());

                        Collections.sort(tags.getList(), new Comparator<Tag>() {
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

                        return 2;
                    }
                    else
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

            if (success == 2) {
                User user = ((Globals) getApplication()).getUser();

                if(user == null)
                    user = new User();

                user.ConvertUsuarioToUser(usuario);
                user.setPassword(mPassword);

                ((Globals) getApplication()).setUser(user);
                ((Globals) getApplication()).setRecetas(new ArrayList<Receta>());
                ((Globals) getApplication()).setTags(tags.getList());

                GoMain(null);
            } else if(success == 1){
                ShowDialogError("Se registró correctamente, " +
                        "pero no se pudo recuperar la infromación");
            }
            else {
                ShowDialogError("El email empleado ya está en uso.");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}