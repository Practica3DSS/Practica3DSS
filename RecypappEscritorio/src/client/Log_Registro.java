package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import clases.UserLoggin;
import clases.Usuario;

public class Log_Registro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tUser;
	private JPasswordField tPassword;
	private static final String EMAIL_REGEX = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})$";

	/**
	 * Create the frame.
	 */
	public Log_Registro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 263, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblNewLabel = new JLabel("RecypApp");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblNewLabel, 80, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblNewLabel, -15, SpringLayout.EAST, contentPane);
		contentPane.add(lblNewLabel);
		
		tUser = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.EAST, tUser, -24, SpringLayout.EAST, contentPane);
		contentPane.add(tUser);
		tUser.setColumns(10);
		
		JButton btnLogIn = new JButton("Acceder");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String email = tUser.getText();
				String pass = new String(tPassword.getPassword());

				if (email.isEmpty() || pass.isEmpty()){
					JOptionPane.showMessageDialog(null, "Por favor, introduzca correo y contraseña.");
				}
				else{
					Pattern pat = Pattern.compile(EMAIL_REGEX);
					Matcher mat = pat.matcher(email);
					
					if(mat.matches()){
						//String ruta_base = txtLocalhost.getText();
						String ruta_base = "http://recypapptomcat-silttest.rhcloud.com";
						if (ruta_base.isEmpty()){
							JOptionPane.showMessageDialog(null, "Por favor, introduzca la ruta base de despliegue del servidor.\n"
									+ "Por ejemplo, http://localhost:8080 si despliega tomcat en el puerto 8080.");
						}
						else{
							ClientConfig config = new ClientConfig();
							Client client = ClientBuilder.newClient(config);
							WebTarget service = client.target(getBaseURI(ruta_base));
			
							Entity<UserLoggin> customerId = Entity.entity(new UserLoggin(email, pass), MediaType.APPLICATION_JSON_TYPE);
							
							Response response = service.path("retrieveByEmail").request().post(customerId);
		
							Usuario customer = response.readEntity(Usuario.class);
							
							System.out.print(response.toString() + "\n" + customer.getEmail());
							try {
								VistaUsuario frame = new VistaUsuario(ruta_base, customer);
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
				}
					else{
						JOptionPane.showMessageDialog(null, "Por favor, introduzca correo válida");
					}
				
				}
			}
		});
		contentPane.add(btnLogIn);
		
		JButton btnSingIn = new JButton("Registrar");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnSingIn, 132, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnSingIn, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnLogIn, 0, SpringLayout.NORTH, btnSingIn);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnLogIn, -16, SpringLayout.WEST, btnSingIn);
		btnSingIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//String ruta_base = txtLocalhost.getText();
					String ruta_base = "http://recypapptomcat-silttest.rhcloud.com";
					RegistroUsuario frame = new RegistroUsuario(ruta_base);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btnSingIn);
		
		tPassword = new JPasswordField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, tPassword, 9, SpringLayout.SOUTH, tUser);
		sl_contentPane.putConstraint(SpringLayout.WEST, tPassword, 85, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, tPassword, -24, SpringLayout.EAST, contentPane);
		contentPane.add(tPassword);
		
		JLabel lblUser = new JLabel("Email:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, tUser, 0, SpringLayout.NORTH, lblUser);
		sl_contentPane.putConstraint(SpringLayout.WEST, tUser, 24, SpringLayout.EAST, lblUser);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblUser, 24, SpringLayout.SOUTH, lblNewLabel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblUser, 44, SpringLayout.SOUTH, lblNewLabel);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUser, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblUser, 61, SpringLayout.WEST, contentPane);
		contentPane.add(lblUser);
		
		JLabel lblPassword = new JLabel("Contrase\u00F1a:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblPassword, 14, SpringLayout.SOUTH, lblUser);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblPassword, -107, SpringLayout.NORTH, btnLogIn);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblPassword, -6, SpringLayout.WEST, tPassword);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPassword, 0, SpringLayout.WEST, lblUser);
		contentPane.add(lblPassword);
	}
	
	
	private static URI getBaseURI(String ruta_base) {
	    return UriBuilder.fromUri(ruta_base + "/Recypapp/rest/usuario").build();
	  }
}
