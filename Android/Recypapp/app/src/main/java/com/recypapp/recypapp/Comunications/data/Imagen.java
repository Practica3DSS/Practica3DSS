package com.recypapp.recypapp.Comunications.data;

public class Imagen{
    private Long id;
    private String MimeType;
    private String FileName;
    protected byte[] imageFile;

    public Imagen(){

    }

    public Imagen(Long id, String mimeType, String fileName) {
        this.id = id;
        MimeType = mimeType;
        FileName = fileName;
    }

    public String getMimeType() {
        return MimeType;
    }

    public byte[] getImageFile() {
        return imageFile;
    }

    public void setMimeType(String MimeType) {
        this.MimeType = MimeType;
    }

    public void setImageFile(byte[] imageFile) {
        this.imageFile = imageFile;
    }

    public Long getId() {
        return id;
    }

    /**
     * The main id thing used by the stuff.
     * @param id  the id of the thing.
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imagen)) {
            return false;
        }
        Imagen other = (Imagen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Imagen[id=" + id + "]";
    }
    /**
     *
     */
    public String getFileName(){
        return FileName;
    }

    public void setFileName(String fileName){
        FileName = fileName;
    }
}