package crud.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ingrediente")
public class Ingrediente implements Serializable {
	private static final long serialVersionUID = 1L;

	private long idIngrediente;
	
	private String nombre;
	
	private String cantidad;

	public Ingrediente() {
	}

	public Ingrediente(long id, String nombre, String cantidad) {
		this.idIngrediente = id;
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	@XmlAttribute
	public long getIdIngrediente() {
		return idIngrediente;
	}

	public void setIdIngrediente(long idIngrediente) {
		this.idIngrediente = idIngrediente;
	}

	@XmlAttribute
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlAttribute
	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
}
