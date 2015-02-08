package crud.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Respuesta")
public class MensajeRespuesta implements Serializable{
	private static final long serialVersionUID = 1L;
	private String respuesta;
	
	public MensajeRespuesta() {
	}
	
	public MensajeRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	@XmlAttribute
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
}
