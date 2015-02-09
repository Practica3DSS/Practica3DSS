package clases;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * My code is sooooo cool it does not need comments.
 * @author Santiago
 * @author Daniel 
 */
@Entity(name="Imagen")
// @DataCache(timeout=10000) // Open JPA supports a timeout on the class.  Not sud
public class Imagen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String MimeType;

    private String FileName;

    @Lob
    @Basic(fetch=FetchType.EAGER) // this gets ignored anyway, but it is recommended for blobs
    protected  byte[]  imageFile;

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