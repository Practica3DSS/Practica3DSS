package clases;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "listReceta")

public class ListReceta implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Receta> list;

	public ListReceta() {
	}
	
	public ListReceta(List<Receta> list) {
		this.list = list;
	}
	
	@XmlElement(name = "receta")
	public List<Receta> getList() {
		return list;
	}

	public void setList(List<Receta> list) {
		this.list = list;
	}
}
