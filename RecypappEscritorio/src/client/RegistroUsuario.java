package client;

import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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

import clases.Imagen;
import clases.Usuario;

public class RegistroUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField jUser;
	private JTextField jEmail;
	private JPasswordField jPassword;
	private JButton btnRegistrar;
	private JButton btnCancelar;

	/**
	 * Create the frame.
	 */
	public RegistroUsuario(String despliegue) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final String ruta_base = despliegue;
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblUsuario, 28, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUsuario, 10, SpringLayout.WEST, contentPane);
		contentPane.add(lblUsuario);
		
		JLabel lblEmail = new JLabel("Email:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblEmail, 23, SpringLayout.SOUTH, lblUsuario);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblEmail, 0, SpringLayout.WEST, lblUsuario);
		contentPane.add(lblEmail);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblContrasea, 20, SpringLayout.SOUTH, lblEmail);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblContrasea, 0, SpringLayout.WEST, lblUsuario);
		contentPane.add(lblContrasea);
		
		jUser = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.WEST, jUser, 48, SpringLayout.EAST, lblUsuario);
		sl_contentPane.putConstraint(SpringLayout.EAST, jUser, 151, SpringLayout.EAST, lblUsuario);
		contentPane.add(jUser);
		jUser.setColumns(10);
		
		jEmail = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.SOUTH, jUser, -14, SpringLayout.NORTH, jEmail);
		sl_contentPane.putConstraint(SpringLayout.NORTH, jEmail, -3, SpringLayout.NORTH, lblEmail);
		sl_contentPane.putConstraint(SpringLayout.WEST, jEmail, 0, SpringLayout.WEST, jUser);
		sl_contentPane.putConstraint(SpringLayout.EAST, jEmail, 163, SpringLayout.EAST, lblEmail);
		jEmail.setColumns(10);
		contentPane.add(jEmail);
		
		jPassword = new JPasswordField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, jPassword, 16, SpringLayout.SOUTH, jEmail);
		sl_contentPane.putConstraint(SpringLayout.WEST, jPassword, -103, SpringLayout.EAST, jUser);
		sl_contentPane.putConstraint(SpringLayout.EAST, jPassword, 0, SpringLayout.EAST, jUser);
		contentPane.add(jPassword);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Registrar(ruta_base);
				
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnRegistrar, 54, SpringLayout.SOUTH, jPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnRegistrar, 41, SpringLayout.WEST, contentPane);
		contentPane.add(btnRegistrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cancelar();
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCancelar, 54, SpringLayout.SOUTH, jPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCancelar, 129, SpringLayout.WEST, contentPane);
		contentPane.add(btnCancelar);
		
		Panel Imagen = new Panel();
		Imagen.setBackground(Color.WHITE);
		sl_contentPane.putConstraint(SpringLayout.NORTH, Imagen, 0, SpringLayout.NORTH, lblUsuario);
		sl_contentPane.putConstraint(SpringLayout.WEST, Imagen, -157, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, Imagen, 14, SpringLayout.SOUTH, lblContrasea);
		sl_contentPane.putConstraint(SpringLayout.EAST, Imagen, -29, SpringLayout.EAST, contentPane);
		contentPane.add(Imagen);
		Imagen.setLayout(null);
		
		JButton btnAgregarImagen = new JButton("Agregar Imagen");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnAgregarImagen, 0, SpringLayout.NORTH, btnRegistrar);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnAgregarImagen, -40, SpringLayout.EAST, contentPane);
		btnAgregarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		contentPane.add(btnAgregarImagen);
	}
	
	
	
	public void Registrar(String ruta_base){
		
		String nombre = jUser.getText();
		String email = jEmail.getText();
		String pass = new String(jPassword.getPassword());
		Imagen img = null;
		
		if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()){
			JOptionPane.showMessageDialog(null, "Por favor, rellene \"Nombre\", \"Email\" y \"Contraseña\"");
		}
		else{
			
			ClientConfig config = new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			WebTarget service = client.target(getBaseURI(ruta_base));
			
			Entity<Usuario> customerId = Entity.entity(new Usuario(0, nombre, pass, email, img), MediaType.APPLICATION_JSON_TYPE);
			Response response = service.path("insert").request().post(customerId);
			
		   System.out.println(response.getStatus());
		
		    if(response.getStatus() == 200){
			    JOptionPane.showMessageDialog(null, "¡Enhorabuena!\n ¡Ya formas parte de RecypApp!");
			    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		    }
		    else{
		    	JOptionPane.showMessageDialog(null, "Lo sentimos. Ha ocurrido un error con el servidor.\n Prueba de nuevo.");
		    }
		}
	}
	
	private static URI getBaseURI(String ruta_base) {
	    return UriBuilder.fromUri(ruta_base + "/Recypapp/rest/usuario").build();
	  }
	
	public void Cancelar(){
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}
