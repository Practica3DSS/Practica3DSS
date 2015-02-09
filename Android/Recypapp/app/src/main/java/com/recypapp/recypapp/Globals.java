package com.recypapp.recypapp;

import android.app.Application;

import com.recypapp.recypapp.Comunications.data.Tag;
import com.recypapp.recypapp.data.Consulta;
import com.recypapp.recypapp.data.Receta;
import com.recypapp.recypapp.data.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silt on 3/02/15.
 */
public class Globals extends Application {
    private static final String DEFAULT_BASE_URL = "http://recypapptomcat-silttest.rhcloud.com";
    private User user;
    private ArrayList<Receta> recetas;
    private List<Tag> tags;
    private Consulta cosulta;
    private Receta receta_e;
    private String baseURL = DEFAULT_BASE_URL;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(ArrayList<Receta> recetas) {
        this.recetas = recetas;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Consulta getCosulta() {
        return cosulta;
    }

    public void setCosulta(Consulta cosulta) {
        this.cosulta = cosulta;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public Receta getReceta_e() {
        return receta_e;
    }

    public void setReceta_e(Receta receta_e) {
        this.receta_e = receta_e;
    }
}
