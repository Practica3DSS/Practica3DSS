package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

import clases.ListReceta;
import clases.Usuario;

public class VistaUsuario extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public VistaUsuario(String despliegue, Usuario user) {
		final Usuario usuario = user;
		final String ruta_base = despliegue;
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel, 108, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel, 122, SpringLayout.WEST, contentPane);
		contentPane.add(panel);
		
		JLabel lblNick = new JLabel("Nick");
		lblNick.setText(user.getNick());
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblNick, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblNick, 5, SpringLayout.EAST, panel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblNick, 37, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblNick, 102, SpringLayout.EAST, panel);
		contentPane.add(lblNick);
		
		JLabel lblEmail = new JLabel("e-mail");
		lblEmail.setText(user.getEmail());
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblEmail, 6, SpringLayout.SOUTH, lblNick);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblEmail, 6, SpringLayout.EAST, panel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblEmail, 33, SpringLayout.SOUTH, lblNick);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblEmail, 103, SpringLayout.EAST, panel);
		contentPane.add(lblEmail);
		
		JButton btnMisRecetas = new JButton("Mis recetas");
		btnMisRecetas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ListReceta recetas = listaRecetasUser(ruta_base, usuario);
				try{
					RecetasUsuario frame = new RecetasUsuario(ruta_base, recetas);
					frame.setVisible(true);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnMisRecetas, 59, SpringLayout.SOUTH, panel);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnMisRecetas, 0, SpringLayout.WEST, panel);
		contentPane.add(btnMisRecetas);
		
		JButton btnAnadirReceta = new JButton("A\u00F1adir receta");
		btnAnadirReceta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					NuevaReceta frame = new NuevaReceta(ruta_base, usuario);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnAnadirReceta, 18, SpringLayout.SOUTH, btnMisRecetas);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnAnadirReceta, 0, SpringLayout.WEST, panel);
		contentPane.add(btnAnadirReceta);
		
		JButton btnModificarPerfil = new JButton("Modificar Perfil");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnModificarPerfil, 188, SpringLayout.EAST, btnMisRecetas);
		btnModificarPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ModificarUsuario frame = new ModificarUsuario(ruta_base, usuario);
					frame.setVisible(true);
					Cerrar();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnModificarPerfil);
		
		JButton btnAnadirTag = new JButton("A\u00F1adir Tag");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnAnadirTag, 2, SpringLayout.NORTH, lblNick);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnAnadirTag, 0, SpringLayout.WEST, btnModificarPerfil);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnAnadirTag, 0, SpringLayout.EAST, btnModificarPerfil);
		btnAnadirTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					CrearTag frame = new CrearTag(ruta_base);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btnAnadirTag);
		
		JButton btnEliminarCuenta = new JButton("Eliminar Cuenta");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnEliminarCuenta, 178, SpringLayout.EAST, btnAnadirReceta);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnEliminarCuenta, -21, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnModificarPerfil, -23, SpringLayout.NORTH, btnEliminarCuenta);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnModificarPerfil, 0, SpringLayout.EAST, btnEliminarCuenta);
		btnEliminarCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Long id = usuario.getIdUsuario();
				EliminarUsuario(ruta_base, id);
			}
		});
		btnEliminarCuenta.setForeground(Color.RED);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnEliminarCuenta, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnEliminarCuenta);
	}
	
	public void Cerrar(){
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public ListReceta listaRecetasUser(String ruta_base, Usuario user){
		//List<Receta> recetas = new ArrayList<Receta> ();
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget service = client.target(getBaseURI(ruta_base));
		
		
		//Antes de crear e insertar la receta, hay que incluir al menos un tag.
		Response r = service.path("retrieveByUser/" + user.getIdUsuario()).request().get();
		ListReceta lista_recetas = r.readEntity(ListReceta.class);
		
		System.out.println(r.toString());
		
		return lista_recetas;
	}
	
	public void EliminarUsuario(String ruta_base, Long id){
		
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget service = client.target(getBaseURIUser(ruta_base));		
		
		Entity<Long> recetaId = Entity.entity(new Long(id), MediaType.APPLICATION_JSON_TYPE);
		Response response = service.path("delete").request().post(recetaId);
		
	   System.out.println(response.toString());
	   
	   
	   if(response.getStatus() == 200){
		    JOptionPane.showMessageDialog(null, "Tu cuenta ha sido eliminada.");
		    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		    
	    }
	    else{
	    	JOptionPane.showMessageDialog(null, "Lo sentimos. Ha ocurrido un error con el servidor.\n Prueba de nuevo.");
	    }
	
	}
	
	private static URI getBaseURI(String ruta_base) {
	    return UriBuilder.fromUri(ruta_base + "/Recypapp/rest/receta").build();
	  }
	private static URI getBaseURIUser(String ruta_base) {
	    return UriBuilder.fromUri(ruta_base + "/Recypapp/rest/usuario").build();
	  }
}
