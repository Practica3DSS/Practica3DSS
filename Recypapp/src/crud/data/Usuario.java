package crud.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import crud.data.Imagen;

@XmlRootElement(name = "usuario")

public class Usuario  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long idUsuario; /** Id del Usuario en la base de datos. */
	private String nick; /** Nick. */
	private String password; /** Password. */
	private String email; 
	private Imagen imagen; /** Imagen */
	
	/**
	 * Contruye un Usuario vacío.
	 */
	public Usuario() { }
	
	/**
	 * Contruye un Usuario.
	 * @param idUsuario id.
	 * @param Nick Nick.
	 * @param password Password.
	 * @param email Email.
	 * @param imagen Imagen.
	 */	
	public Usuario(long idUsuario, String nick, String password, String email,
			Imagen imagen) {
		this.idUsuario = idUsuario;
		this.nick = nick;
		this.password = password;
		this.email = email;
		this.imagen = imagen;
	}

	/**
	 * Devuelve el id del Usuario.
	 * @return Id.
	 */
	@XmlAttribute
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
	@XmlAttribute
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
	@XmlAttribute
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
	@XmlAttribute
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
	
	@XmlElement
	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}
}

