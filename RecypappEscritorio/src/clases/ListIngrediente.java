package clases;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "listIngrediente")
public class ListIngrediente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Ingrediente> ingredientes;

	public ListIngrediente() {

	}
	
	public ListIngrediente(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}
	
	@XmlElement(name = "ingredientes")
	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}
}
