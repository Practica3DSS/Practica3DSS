package com.recypapp.recypapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.recypapp.recypapp.Comunications.Comunication;
import com.recypapp.recypapp.Comunications.data.ListReceta;
import com.recypapp.recypapp.data.Consulta;
import com.recypapp.recypapp.data.Receta;

import java.util.List;

public class SearchFragment extends android.support.v4.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";

    private Button bTag;
    private EditText name;
    private EditText comensales;
    private EditText dur_min;
    private EditText dur_max;
    private int tagId;
    private RetrieveListQuery mAuthTask = null;
    private ProgressDialog mProgressDialog;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber Parameter 1.
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance(int sectionNumber) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = (View) inflater.inflate(R.layout.fragment_search, container, false);

        bTag = (Button) v.findViewById(R.id.buttonTags);
        name = ((EditText) v.findViewById(R.id.nombreContiene));
        comensales = ((EditText) v.findViewById(R.id.nComensales));
        dur_min = ((EditText) v.findViewById(R.id.dur_min));
        dur_max = ((EditText) v.findViewById(R.id.dur_max));
        tagId = -1;

        bTag.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TagSearchActivity.class);

                getActivity().startActivity(intent);
                SafeConsulta();
            }
        });

        ((Button) v.findViewById(R.id.searchButton)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SafeConsulta();
                mAuthTask = new RetrieveListQuery((Globals)getActivity().getApplication());
                mAuthTask.execute();
            }
        });

        Consulta consulta = ((Globals)getActivity().getApplication()).getCosulta();

        if(consulta != null){
            name.setText(consulta.getNombre());

            if(consulta.getComensales() > 0) {
                comensales.setText(Integer.toString(consulta.getComensales()));
            }

            if((consulta.getH_min() + consulta.getMin_min()) > 0) {
                String offSetH = "";

                if(consulta.getH_min() < 10)
                    offSetH = "0";

                String offSetMin = "";

                if(consulta.getMin_min() < 10)
                    offSetMin = "0";

                dur_min.setText(offSetH + Integer
                        .toString(consulta.getH_min()) + ":" +
                        offSetMin + Integer.toString(consulta.getMin_min()));
            }

            if((consulta.getH_max() + consulta.getMin_max()) > 0) {
                String offSetH = "";

                if(consulta.getH_max() < 10)
                    offSetH = "0";

                String offSetMin = "";

                if(consulta.getMin_max() < 10)
                    offSetMin = "0";

                dur_max.setText(offSetH + Integer
                        .toString(consulta.getH_max()) + ":" +
                        offSetMin + Integer.toString(consulta.getMin_max()));
            }

            tagId = consulta.getTag_pos();

            if(tagId > -1){
                bTag.setText(((Globals) getActivity().getApplication()).getTags()
                        .get(tagId).getNombre());
            }

            ((Globals) getActivity().getApplication()).setCosulta(null);
        }

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

        dur_min.setFilters(timeFilters);
        dur_max.setFilters(timeFilters);

        return v;
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

    private void SafeConsulta(){
        String dur_min_text = dur_min.getText().toString();
        String dur_max_text = dur_max.getText().toString();

        int min_min = 0;
        int min_max = 0;
        int h_min = 0;
        int h_max = 0;

        if(dur_min_text.length() > 0) {
            min_min = ParseTime(dur_min_text);
            h_min = min_min / 60;
            min_min -= h_min * 60;
        }

        if(dur_max_text.length() > 0) {
            min_max = ParseTime(dur_max_text);
            h_max = min_max / 60;
            min_max -= h_max * 60;
        }

        int comensales_int = 0;
        String comensales_text = comensales.getText().toString();

        if(comensales_text.length() > 0)
            comensales_int = Integer.decode(comensales_text);

        Consulta c = new Consulta(name.getText().toString(),
                comensales_int, h_min,
                min_min, h_max, min_max,tagId);

        ((Globals) getActivity().getApplication()).setCosulta(c);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public void ShowDialogError(String error){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
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

    public class RetrieveListQuery extends AsyncTask<Void, Void, Boolean> {
        protected Globals g;
        protected ListReceta recetas;

        public RetrieveListQuery(Globals g) {
            this.g = g;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                long idTag = 0;

                if(g.getCosulta().getTag_pos() > -1)
                    idTag = g.getTags().get(g.getCosulta().getTag_pos()).getIdTag();

                recetas = Comunication.GetRecetasQuery(g.getBaseURL(), idTag, 0,
                    g.getCosulta().getNombre(), g.getCosulta().getComensales(),
                    g.getCosulta().getMin_min() + g.getCosulta().getH_min()*60,
                    g.getCosulta().getMin_max() + g.getCosulta().getH_max()*60);

                return true;
            }
            catch(Exception e){
                Log.e("Main", e.toString());

                return false;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SearchFragment.this.getActivity());
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

            List<Receta> recetas_data = g.getCosulta().getRecetas();

            if (success) {
                if(recetas != null){
                    if(recetas.getList() != null){
                        for(com.recypapp.recypapp.Comunications.data.Receta rc: recetas.getList()){
                            Receta r = new Receta();

                            //r = new Receta(getActivity(), 80, R.drawable.example_receta);
                            r.ConvertRecetaCToReceta(rc);
                            recetas_data.add(r);
                        }
                    }
                }

                Intent intent = new Intent(getActivity(),RecetaSearchActivity.class);

                getActivity().startActivity(intent);
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
