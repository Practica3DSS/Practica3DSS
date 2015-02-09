package com.recypapp.recypapp.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.recypapp.recypapp.Comunications.data.Imagen;
import com.recypapp.recypapp.Comunications.data.Ingrediente;
import com.recypapp.recypapp.Comunications.data.ListId;
import com.recypapp.recypapp.Comunications.data.ListIngrediente;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by silt on 5/02/15.
 */
public class Receta {
    private long id;
    private String nombre;
    private String description;
    private String user_nick;
    private int cantidad;
    private int dur_hor;
    private int dur_min;
    private Bitmap img;
    private long img_id;
    private String img_name;
    private String img_type;
    private List<Ingrediente> ingredientes;
    private List<Long> tags;

    public Receta(){
        id = 0;
        nombre = "Test";
        description = "Description";
        user_nick = "Invitado";
        cantidad = 10;
        dur_hor = 1;
        dur_min = 15;
        img = null;
        ingredientes = new ArrayList<Ingrediente>();
        tags = new ArrayList<Long>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getDur_hor() {
        return dur_hor;
    }

    public void setDur_hor(int dur_hor) {
        this.dur_hor = dur_hor;
    }

    public int getDur_min() {
        return dur_min;
    }

    public void setDur_min(int dur_min) {
        this.dur_min = dur_min;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public long getImg_id() {
        return img_id;
    }

    public void setImg_id(long img_id) {
        this.img_id = img_id;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public String getImg_type() {
        return img_type;
    }

    public void setImg_type(String img_type) {
        this.img_type = img_type;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<Long> getTags() {
        return tags;
    }

    public void setTags(List<Long> tags) {
        this.tags = tags;
    }

    public void ConvertRecetaCToReceta(com.recypapp.recypapp.Comunications.data.Receta c){
        id = c.getIdReceta();
        nombre = c.getNombre();
        description = c.getDescripcion();
        user_nick = c.getUsuario_nick();
        cantidad = c.getCantidad_comensales();
        dur_hor = c.getDuracion()/60;
        dur_min = c.getDuracion() - dur_hor*60;

        if(c.getImagen() != null) {
            img_id = c.getImagen().getId();
            img_name = c.getImagen().getFileName();
            img_type = c.getImagen().getMimeType();
        }

        tags.clear();

        for(Long t_id : c.getTags().getId()){
            tags.add(t_id);
        }

        Collections.sort(tags, new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                if (o1 < o2)
                    return -1;
                else if (o1 > o2)
                    return 1;
                else
                    return 0;
            }
        });

        ingredientes = c.getIngredientes().getIngredientes();

        if(c.getImagen() != null) {
            if (c.getImagen().getImageFile() != null) {
                if (c.getImagen().getImageFile().length > 0) {
                    img = BitmapFactory.decodeByteArray(c.getImagen().getImageFile(), 0,
                            c.getImagen().getImageFile().length);
                }
            }
        }
    }

    public com.recypapp.recypapp.Comunications.data.Receta ConvertRecetaToRecetaC(long user_id){
        ListId tags_l = new ListId(new ArrayList<Long>());
        ListIngrediente ingredientes_l = new ListIngrediente(ingredientes);

        Imagen img_c = new Imagen(img_id, img_type, img_name);

        if(img != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            img_c.setImageFile(byteArray);
        }

        for(Long t_id: tags){
            tags_l.getId().add(t_id);
        }

        return new com.recypapp.recypapp.Comunications.data.Receta(id, nombre, description,
                dur_hor*60 + dur_min, cantidad, img_c, user_id,
                user_nick, ingredientes_l, tags_l);
    }
}
