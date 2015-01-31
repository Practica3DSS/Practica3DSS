package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.sun.istack.internal.NotNull;

/**
 * @author Santiago
 * @author Daniel 
 */

@Entity
public class Receta implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idReceta; /** Id de la Receta en la base de datos. */
	
	@NotNull
	private String nombre;
	
	@NotNull
	private String descripcion;
	
	@NotNull
	private int duracion; /** Duracion en minutos */
	
	@NotNull
	private int cantidad_comensales;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, optional = true)
	private Imagen imagen;

	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ingrediente> ingredientes;
	
	@ManyToMany
	@JoinTable(
			name="REC_TAG",
			joinColumns={@JoinColumn(name="REC_ID", referencedColumnName="idReceta")},
			inverseJoinColumns={@JoinColumn(name="TAG_ID", referencedColumnName="idTag")})
	private List<Tag> tags;

	public Receta() {
	}
	
	public Receta(String nombre, String descripcion, int duracion,
			int cantidad_comensales, Imagen imagen, Usuario usuario,
			List<Ingrediente> ingredientes) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.duracion = duracion;
		this.cantidad_comensales = cantidad_comensales;
		this.imagen = imagen;
		this.usuario = usuario;
		this.ingredientes = ingredientes;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public long getIdReceta() {
		return idReceta;
	}

	public void setIdReceta(long idReceta) {
		this.idReceta = idReceta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public int getCantidad_comensales() {
		return cantidad_comensales;
	}

	public void setCantidad_comensales(int cantidad_comensales) {
		this.cantidad_comensales = cantidad_comensales;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}
}