package com.recypapp.recypapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by silt on 8/02/15.
 */
public class DialogInputBaseURL {
    private Globals g;
    private AlertDialog alertDialog;
    OnFinishDialog caller;

    DialogInputBaseURL(Globals new_g, Context context, OnFinishDialog new_caller){
        g = new_g;
        caller = new_caller;

        /* Alert Dialog Code Start*/
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Cambiar Dirección del servidor"); //Set Alert dialog title here
        alert.setMessage("Introduzca la dirección del servidor aquí."); //Message here

        // Set an EditText view to get user input
        final EditText input = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setText(g.getBaseURL());
        alert.setView(input);

        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                g.setBaseURL(input.getEditableText().toString());
                caller.OnFinish();
            } // End of onClick(DialogInterface dialog, int whichButton)
        }); //End of alert.setPositiveButton
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();
                caller.OnFinish();
            }
        }); //End of alert.setNegativeButton;
        alertDialog = alert.create();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                caller.OnFinish();
            }
        });
       /* Alert Dialog Code End*/
    }

    public interface OnFinishDialog {
        public void OnFinish();
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }
}
