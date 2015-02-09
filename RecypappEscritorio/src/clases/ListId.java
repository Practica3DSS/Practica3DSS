package clases;

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

	public ListId(List<Long> ids) {
		this.id = ids;
	}

	@XmlElement(name = "id")
	public List<Long> getIds() {
		return id;
	}

	public void setIds(List<Long> id) {
		this.id = id;
	}
}
