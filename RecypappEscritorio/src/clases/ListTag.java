package clases;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "listTag")

public class ListTag implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Tag> list;

	public ListTag() {
	}
	
	public ListTag(List<Tag> list) {
		this.list = list;
	}
	
	@XmlElement(name = "tag")
	public List<Tag> getList() {
		return list;
	}

	public void setList(List<Tag> list) {
		this.list = list;
	}
}
