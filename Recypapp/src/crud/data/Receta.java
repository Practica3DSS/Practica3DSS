package crud.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import crud.data.Imagen;

@XmlRootElement(name = "receta")

public class Receta  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long idReceta; /** Id de la Receta en la base de datos. */
	
	private String nombre;
	
	private String descripcion;
	
	private int duracion; /** Duracion en minutos */
	
	private int cantidad_comensales;
	
	private Imagen imagen;

	private long usuario;
	
	private String usuario_nick;
	
	private ListIngrediente ingredientes;
	
	private ListId tags;

	public Receta() {
	}
	
	public Receta(long idReceta, String nombre, String descripcion, int duracion,
			int cantidad_comensales, Imagen imagen, long usuario, String usuario_nick,
			ListIngrediente ingredientes, ListId tags) {
		this.idReceta = idReceta;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.duracion = duracion;
		this.cantidad_comensales = cantidad_comensales;
		this.imagen = imagen;
		this.usuario = usuario;
		this.usuario_nick = usuario_nick;
		this.ingredientes = ingredientes;
		this.tags = tags;
	}

	@XmlAttribute
	public long getUsuario() {
		return usuario;
	}

	public void setUsuario(long usuario) {
		this.usuario = usuario;
	}

	@XmlAttribute
	public String getUsuario_nick() {
		return usuario_nick;
	}

	public void setUsuario_nick(String usuario_nick) {
		this.usuario_nick = usuario_nick;
	}
	
	@XmlAttribute
	public long getIdReceta() {
		return idReceta;
	}

	public void setIdReceta(long idReceta) {
		this.idReceta = idReceta;
	}

	@XmlAttribute
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlAttribute
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@XmlAttribute
	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	@XmlAttribute
	public int getCantidad_comensales() {
		return cantidad_comensales;
	}

	public void setCantidad_comensales(int cantidad_comensales) {
		this.cantidad_comensales = cantidad_comensales;
	}

	@XmlElement
	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	@XmlElement
	public ListId getTags() {
		return tags;
	}

	public void setTags(ListId tags) {
		this.tags = tags;
	}
	
	@XmlElement
	public ListIngrediente getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(ListIngrediente ingredientes) {
		this.ingredientes = ingredientes;
	}
}