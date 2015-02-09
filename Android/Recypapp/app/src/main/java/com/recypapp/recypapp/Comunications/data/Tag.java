package com.recypapp.recypapp.Comunications.data;

import java.util.Comparator;

public class Tag {
    private static final long serialVersionUID = 1L;
    private long idTag;
    private String nombre;

    public Tag() {
    }

    public Tag(long idTag, String nombre) {
        this.idTag = idTag;
        this.nombre = nombre;
    }

    public long getIdTag() {
        return idTag;
    }

    public void setIdTag(long idTag) {
        this.idTag = idTag;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}