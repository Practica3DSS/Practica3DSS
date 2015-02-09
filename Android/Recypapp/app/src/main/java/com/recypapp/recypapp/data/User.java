package com.recypapp.recypapp.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.recypapp.recypapp.Comunications.data.Imagen;
import com.recypapp.recypapp.Comunications.data.Usuario;

import java.io.ByteArrayOutputStream;

/**
 * Created by silt on 3/02/15.
 */
public class User {
    private long id;
    private String nick;
    private String email;
    private String password;
    private Bitmap img;
    private long img_id;
    private String img_name;
    private String img_type;

    public User() {
        id = 0;
        nick = "Invitado";
        email = "invitado@gmail.com";

        img = null;
        img_id = 0;
        img_name = "";
        img_type = "image/jpg";
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void ConvertUsuarioToUser(Usuario usuario){
        setNick(usuario.getNick());
        setEmail(usuario.getEmail());
        setPassword(usuario.getPassword());
        setId(usuario.getIdUsuario());

        if(usuario.getImagen() != null) {
            setImg_name(usuario.getImagen().getFileName());
            setImg_type(usuario.getImagen().getMimeType());
            setImg_id(usuario.getImagen().getId());


            if (usuario.getImagen().getImageFile() != null) {
                if (usuario.getImagen().getImageFile().length > 0) {
                    img = BitmapFactory.decodeByteArray(usuario.getImagen().getImageFile(), 0,
                            usuario.getImagen().getImageFile().length);
                }
            }
        }
    }

    public Usuario ConvertUserToUsuario(){
        Imagen img_c = new Imagen(img_id, img_type, img_name);

        if(img != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            img_c.setImageFile(byteArray);
        }

        return new Usuario(id, nick, password, email, img_c);
    }
}
