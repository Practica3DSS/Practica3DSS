package com.recypapp.recypapp.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silt on 6/02/15.
 */
public class Consulta {
    private String nombre;
    private int comensales;
    private int h_min;
    private int min_min;
    private int h_max;
    private int min_max;
    private int tag_pos;
    private List<Receta> recetas;

    public Consulta(String nombre, int comensales, int h_min, int min_min, int h_max, int min_max, int tag_pos) {
        this.nombre = nombre;
        this.comensales = comensales;
        this.h_min = h_min;
        this.min_min = min_min;
        this.h_max = h_max;
        this.min_max = min_max;
        this.tag_pos = tag_pos;
        recetas = new ArrayList<Receta>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getComensales() {
        return comensales;
    }

    public void setComensales(int comensales) {
        this.comensales = comensales;
    }

    public int getH_min() {
        return h_min;
    }

    public void setH_min(int h_min) {
        this.h_min = h_min;
    }

    public int getMin_min() {
        return min_min;
    }

    public void setMin_min(int min_min) {
        this.min_min = min_min;
    }

    public int getH_max() {
        return h_max;
    }

    public void setH_max(int h_max) {
        this.h_max = h_max;
    }

    public int getMin_max() {
        return min_max;
    }

    public void setMin_max(int min_max) {
        this.min_max = min_max;
    }

    public int getTag_pos() {
        return tag_pos;
    }

    public void setTag_pos(int tag_pos) {
        this.tag_pos = tag_pos;
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }
}
