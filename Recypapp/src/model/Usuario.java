package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.sun.istack.internal.NotNull;

/**
 * Clase que modela un usuario de lalista de correo.
 * @author Santiago
 * @author Daniel 
 */
@Entity
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idUsuario; /** Id del Usuario en la base de datos. */
	
	@NotNull
	private String nick; /** Nick. */
	
	@NotNull
	private String password; /** Password. */
	
	@Column(unique = true, nullable = false)
	private String email; 

	/** Email. */
	
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, optional = true)
	private Imagen imagen; /** Imagen */
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Receta> recetas;

	/**
	 * Contruye un Usuario vacío.
	 */
	public Usuario() { }
	
	/**
	 * Contruye un Usuario.
	 * @param Nick Nick.
	 * @param password Password.
	 * @param email Email.
	 * @param imagen Imagen.
	 */
	public Usuario(String Nick, String password, String email, Imagen imagen){
		this.nick = Nick;
		this.password = password;
		this.email = email;
		this.imagen = imagen;
	}
	
	/**
	 * Devuelve el id del Usuario.
	 * @return Id.
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Asigna un id al Usuario.
	 * @param idUsuario Nuevo id.
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/**
	 * Devuelve el nick del Usuario.
	 * @return nick.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Asigna un nick al Usuario.
	 * @param nick Nuevo Nick.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Devuelve el apellido del Usuario.
	 * @return Password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Asigna un apellido al Usuario.
	 * @param apellido Nuevo apellido.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Devuelve el email del Usuario.
	 * @return Email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Asigna un email al Usuario.
	 * @param email Nuevo email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}
	
	public List<Receta> getRecetas() {
		return recetas;
	}

	public void setRecetas(List<Receta> recetas) {
		this.recetas = recetas;
	}
}
