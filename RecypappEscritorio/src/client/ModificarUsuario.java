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

public class ModificarUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tUser;
	private JTextField tEmail;
	private JPasswordField tPassword;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JLabel lblNewLabel;
	private JPasswordField passActual;

	/**
	 * Create the frame.
	 */
	public ModificarUsuario(String despliegue, Usuario user) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final String ruta_base = despliegue;
		final Usuario user_aux = user;
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
		
		tUser = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.EAST, tUser, 184, SpringLayout.EAST, lblUsuario);
		tUser.setText(user.getNick());
		sl_contentPane.putConstraint(SpringLayout.WEST, tUser, 48, SpringLayout.EAST, lblUsuario);
		contentPane.add(tUser);
		tUser.setColumns(10);
		
		tEmail = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.SOUTH, tUser, -19, SpringLayout.NORTH, tEmail);
		sl_contentPane.putConstraint(SpringLayout.EAST, tEmail, 0, SpringLayout.EAST, tUser);
		tEmail.setText(user.getEmail());
		sl_contentPane.putConstraint(SpringLayout.WEST, tEmail, 60, SpringLayout.EAST, lblEmail);
		sl_contentPane.putConstraint(SpringLayout.NORTH, tEmail, -3, SpringLayout.NORTH, lblEmail);
		tEmail.setColumns(10);
		contentPane.add(tEmail);
		
		tPassword = new JPasswordField();
		
		sl_contentPane.putConstraint(SpringLayout.NORTH, tPassword, -3, SpringLayout.NORTH, lblContrasea);
		sl_contentPane.putConstraint(SpringLayout.WEST, tPassword, 28, SpringLayout.EAST, lblContrasea);
		sl_contentPane.putConstraint(SpringLayout.EAST, tPassword, 0, SpringLayout.EAST, tUser);
		contentPane.add(tPassword);
		
		btnGuardar = new JButton("Guardar");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnGuardar, -10, SpringLayout.SOUTH, contentPane);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Actualizar(ruta_base, user_aux);
				
			}
		});
		contentPane.add(btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCancelar, 0, SpringLayout.NORTH, btnGuardar);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCancelar, 24, SpringLayout.EAST, btnGuardar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VolverVistaUsuario(ruta_base, user_aux);
				Cerrar();
			}
		});
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
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnAgregarImagen, 45, SpringLayout.SOUTH, Imagen);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnAgregarImagen, -40, SpringLayout.EAST, contentPane);
		btnAgregarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		contentPane.add(btnAgregarImagen);
		
		lblNewLabel = new JLabel("Contrase\u00F1a actual:");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblNewLabel, -53, SpringLayout.NORTH, btnGuardar);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnGuardar, 0, SpringLayout.EAST, lblNewLabel);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, lblUsuario);
		contentPane.add(lblNewLabel);
		
		passActual = new JPasswordField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, passActual, 33, SpringLayout.SOUTH, tPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, passActual, 12, SpringLayout.EAST, lblNewLabel);
		sl_contentPane.putConstraint(SpringLayout.EAST, passActual, -56, SpringLayout.WEST, btnAgregarImagen);
		contentPane.add(passActual);
	}
	
	
	
	public void Actualizar(String ruta_base, Usuario user){
		String pass = new String(passActual.getPassword());
		if (pass.isEmpty()){
			JOptionPane.showMessageDialog(null, "Introduzca su contraseña actual.");
		}
		else{			
			String nombre = tUser.getText();
			String email = tEmail.getText();
			String pass_nueva = new String(tPassword.getPassword());
			
			if(pass_nueva.isEmpty()){
				pass_nueva = pass;
			}
			
			Imagen img = null;
			
			
			
			if (nombre.isEmpty() || email.isEmpty()){
				JOptionPane.showMessageDialog(null, "Por favor, rellene \"Nombre\", \"Email\"");
			}
			else{
				
				ClientConfig config = new ClientConfig();
				Client client = ClientBuilder.newClient(config);
				WebTarget service = client.target(getBaseURI(ruta_base));

				Entity<Usuario> customerId = Entity.entity(new Usuario(user.getIdUsuario(), nombre, pass_nueva, email, img), 
						MediaType.APPLICATION_JSON_TYPE);
				Response response = service.path("update").request().header("pass", pass).post(customerId);
				
			   System.out.println(response.toString());
			
			    if(response.getStatus() == 200){
				    JOptionPane.showMessageDialog(null, "¡Usuario modificado!");
				    Usuario vuelta = new Usuario(user.getIdUsuario(), nombre,  pass_nueva, email, img);
				    VolverVistaUsuario(ruta_base, vuelta);
				    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			    }
			    else{
			    	JOptionPane.showMessageDialog(null, "Lo sentimos. Ha ocurrido un error con el servidor.\n Prueba de nuevo.");
			    }
			}
		}
	}
	
	private static URI getBaseURI(String ruta_base) {
	    return UriBuilder.fromUri(ruta_base + "/Recypapp/rest/usuario").build();
	  }
	
	public void Cerrar(){
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	public void VolverVistaUsuario(String ruta_base, Usuario user){
		try{
			VistaUsuario frame = new VistaUsuario(ruta_base, user);
			frame.setVisible(true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
