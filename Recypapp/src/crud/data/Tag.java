package crud.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tag")

public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;
	private long idTag;
	private String nombre;

	public Tag() {
	}
	
	public Tag(long idTag, String nombre) {
		this.idTag = idTag;
		this.nombre = nombre;
	}
	
	public Tag(String nombre){
		this.nombre = nombre;
	}
	
	@XmlAttribute
	public long getIdTag() {
		return idTag;
	}

	public void setIdTag(long idTag) {
		this.idTag = idTag;
	}

	@XmlAttribute
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
}
