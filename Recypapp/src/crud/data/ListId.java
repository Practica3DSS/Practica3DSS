package crud.data;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ListId")
public class ListId implements Serializable{
	private static final long serialVersionUID = 1L;

	List<Long> id;

	public ListId() {
	}

	public ListId(List<Long> id) {
		this.id = id;
	}

	@XmlElement
	public List<Long> getId() {
		return id;
	}

	public void setId(List<Long> id) {
		this.id = id;
	}
}
