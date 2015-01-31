package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sun.istack.internal.NotNull;

/**
 * @author Santiago
 * @author Daniel 
 */

@Entity
public class Ingrediente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idIngrediente;
	
	@NotNull
	private String nombre;
	
	@NotNull
	private String cantidad;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "idReceta", nullable = false)
	private Receta receta;

	public Ingrediente() {
	}

	public Ingrediente(String nombre, String cantidad) {
		this.nombre = nombre;
		this.cantidad = cantidad;
	}
	
	public Ingrediente(String nombre, String cantidad, Receta receta) {
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.receta = receta;
	}

	public long getIdIngrediente() {
		return idIngrediente;
	}

	public void setIdIngrediente(long idIngrediente) {
		this.idIngrediente = idIngrediente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}
}
