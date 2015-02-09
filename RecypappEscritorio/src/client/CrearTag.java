package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import clases.Tag;

public class CrearTag extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tTag;

	public CrearTag(String despliegue) {
		final String ruta_base = despliegue;
		setBounds(100, 100, 301, 218);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblTag = new JLabel("Tag:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblTag, 56, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblTag, -205, SpringLayout.EAST, contentPane);
		contentPane.add(lblTag);
		
		tTag = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, tTag, -3, SpringLayout.NORTH, lblTag);
		sl_contentPane.putConstraint(SpringLayout.WEST, tTag, 6, SpringLayout.EAST, lblTag);
		sl_contentPane.putConstraint(SpringLayout.EAST, tTag, -88, SpringLayout.EAST, contentPane);
		contentPane.add(tTag);
		tTag.setColumns(10);
		
		JButton btnCrearTag = new JButton("Crear Tag");
		btnCrearTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = tTag.getText();
				if (nombre.isEmpty()){
					JOptionPane.showMessageDialog(null, "Introduzca un nombre para el nuevo tag.");
				}
				else{
					ClientConfig config = new ClientConfig();
					Client client = ClientBuilder.newClient(config);
					WebTarget service = client.target(getBaseURI(ruta_base));
					
					Entity<Tag> tagId = Entity.entity(new Tag(0, nombre), MediaType.APPLICATION_JSON_TYPE);
					Response response = service.path("insert").request().post(tagId);
					
				   System.out.println(response.getStatus());
				   
				   if(response.getStatus() == 200){
					   tTag.setText("");
					    JOptionPane.showMessageDialog(null, "Tag añadido.");
					    //Cerrar();
				    }
				    else{
				    	JOptionPane.showMessageDialog(null, "Lo sentimos. Ha ocurrido un error con el servidor.\n Prueba de nuevo.");
				    }
				}
			}
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCrearTag, 40, SpringLayout.WEST, contentPane);
		contentPane.add(btnCrearTag);
		
		JButton btnCancelar = new JButton("Salir");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cerrar();
			}
		});
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnCancelar, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCrearTag, 0, SpringLayout.NORTH, btnCancelar);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCancelar, -52, SpringLayout.EAST, contentPane);
		contentPane.add(btnCancelar);
	}
	
	
	public void Cerrar(){
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	private static URI getBaseURI(String ruta_base) {
	    return UriBuilder.fromUri(ruta_base + "/Recypapp/rest/tag").build();
	  }
}
