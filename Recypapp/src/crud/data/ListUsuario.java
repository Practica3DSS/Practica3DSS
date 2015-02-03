package crud.data;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "listUsuario")

public class ListUsuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Usuario> list;

	public ListUsuario() {
	}
	
	public ListUsuario(List<Usuario> list) {
		this.list = list;
	}
	
	@XmlElement(name = "usuario")
	public List<Usuario> getList() {
		return list;
	}

	public void setList(List<Usuario> list) {
		this.list = list;
	}
}
