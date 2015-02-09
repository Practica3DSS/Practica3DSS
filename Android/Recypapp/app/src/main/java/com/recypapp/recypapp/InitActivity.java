package com.recypapp.recypapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;


public class InitActivity extends Activity implements DialogInputBaseURL.OnFinishDialog
{
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ShowDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_init, menu);
        return true;
    }

    public void ShowDialog(){
        DialogInputBaseURL d = new DialogInputBaseURL((Globals)getApplication(), this, this);
        d.getAlertDialog().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnFinish() {
        final Context este = this;

        new Thread(new Runnable() {
            public void run() {
                for(progressStatus = 0; progressStatus < 100; progressStatus++) {
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });

                    try {
                        // Sleep for 200 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                handler.post(new Runnable() {
                    public void run() {
                        //Toast.makeText(este, "END", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(este, LoginActivity.class);
                        Intent intent = new Intent(este,  LoginActivity.class);
                        //Bundle b = new Bundle();
                        //b.putInt(MainActivity.ARG_SECTION, -1); //Your id
                        //intent.putExtras(b); //Put your id to your next Intent
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        este.startActivity(intent);
                    }
                });

                try {
                    // Sleep for 200 milliseconds.
                    //Just to display the progress slowly
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    class Task implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i <= 100; i++) {
                final int value = i;

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                progressBar.setProgress(value);
            }
        }
    }*/
}
