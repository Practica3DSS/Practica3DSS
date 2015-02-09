package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import clases.ListReceta;
import clases.Receta;
import javax.swing.JPasswordField;

/*
 * Es necesario implementar el interfaz ActionListener, porque al ser un frame dinámico, no se sabe a priori cuántos
 * botones de cada tipo (eliminar, actualizar) va a tener. 
 */
public class RecetasUsuario extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JScrollPane scroll;
	private ArrayList<JTextField> tf;
	private ArrayList<JButton> borrar;
	private JLabel Titulo, Duracion, Comensales;
	private SpringLayout spring;
	private ListReceta listaRecetas;
	private List<Receta> l_Recetas;
	private JPasswordField passwordField;
	private String ruta_base;
	//private List<Usuario> users;

	/**
	 * Create the frame.
	 */
	public RecetasUsuario(String despliegue, ListReceta recetas) {
		ruta_base = despliegue;
		listaRecetas = recetas;
		l_Recetas = listaRecetas.getList();
		setSize(800, 600);
		getContentPane().setLayout(new BorderLayout(0,0));
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(800, 600));
		panel.setOpaque(false);
		scroll = new JScrollPane(panel);
		spring = new SpringLayout();
		panel.setLayout(spring);
		
//		btn_act = new JButton("Actualizar");
//		btn_act.addActionListener(this);
//		btn_act.setActionCommand("actualizar");
		
		//Creo las etiquetas y las coloco en el panel
		Titulo = new JLabel("T\u00EDtulo");
		spring.putConstraint(SpringLayout.NORTH, Titulo, 50, SpringLayout.NORTH, panel);
		spring.putConstraint(SpringLayout.WEST, Titulo, 10, SpringLayout.WEST, panel);
		
		panel.add(Titulo);
		
		Duracion = new JLabel("Duraci\u00F3n (mins)");
		spring.putConstraint(SpringLayout.NORTH, Duracion, 0, SpringLayout.NORTH, Titulo);
		
		panel.add(Duracion);
		
		
		Comensales = new JLabel("Comensales");
		spring.putConstraint(SpringLayout.EAST, Duracion, -26, SpringLayout.WEST, Comensales);
		spring.putConstraint(SpringLayout.WEST, Comensales, 350, SpringLayout.WEST, panel);
		spring.putConstraint(SpringLayout.NORTH, Comensales, 0, SpringLayout.NORTH, Titulo);
	
		panel.add(Comensales);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		spring.putConstraint(SpringLayout.NORTH, lblContrasea, 10, SpringLayout.NORTH, panel);
		spring.putConstraint(SpringLayout.WEST, lblContrasea, 10, SpringLayout.WEST, panel);
		panel.add(lblContrasea);
		
		passwordField = new JPasswordField();
		spring.putConstraint(SpringLayout.NORTH, passwordField, 10, SpringLayout.NORTH, panel);
		spring.putConstraint(SpringLayout.WEST, passwordField, 20, SpringLayout.EAST, lblContrasea);
		spring.putConstraint(SpringLayout.EAST, passwordField, 94, SpringLayout.EAST, lblContrasea);
		panel.add(passwordField);
		//Hasta aquí las tres etiquetas de nombre, apellido y email
		
		//Ahora, la parte en la que se crean los campos de texto y botones para mostrar y eliminar usuarios
		tf = new ArrayList<JTextField>();
		borrar = new ArrayList<JButton>();
		int i=2;//Esta variable indica el número de usuario para poder colocarlos todos en orden.
		//int act=0; //Esta variable action será el actionCommander que devolverá el botón de borrar pulsado. Será el índice del campo 'Nombre' del usuario que se quiere eliminar.
		for(Receta rec : l_Recetas){
			JTextField j1 = new JTextField();
			JTextField j2 = new JTextField();
			JTextField j3 = new JTextField();
			
			j1.setText(rec.getNombre());
			j2.setText(String.valueOf(rec.getDuracion()));
			j3.setText(String.valueOf(rec.getCantidad_comensales()));
			
			j1.setColumns(10);
			j2.setColumns(10);
			j3.setColumns(15);
			
			j1.setEditable(false);
			j2.setEditable(false);
			j3.setEditable(false); //Hago que el campo del email no sea editable para que no se pueda modificar.
			
			j1.setColumns(20);
			spring.putConstraint(SpringLayout.NORTH, j1, i*50, SpringLayout.NORTH, panel);
			spring.putConstraint(SpringLayout.WEST, j1, 10, SpringLayout.WEST, panel);
			panel.add(j1);
			
			j2.setColumns(3);
			spring.putConstraint(SpringLayout.NORTH, j2, i*50, SpringLayout.NORTH, panel);
			spring.putConstraint(SpringLayout.WEST, j2, 270, SpringLayout.WEST, panel);
			panel.add(j2);
			
			j3.setColumns(3);
			spring.putConstraint(SpringLayout.NORTH, j3, i*50, SpringLayout.NORTH, panel);
			spring.putConstraint(SpringLayout.WEST, j3, 350, SpringLayout.WEST, panel);
			panel.add(j3);
			
			//Una vez creados y colocados en el panel los campos de texto, los guardo en el vector 'tf', para poder
			//trabajar con ellos posteriormente
			tf.add(j1);
			tf.add(j2);
			tf.add(j3);
			
			//Creo el botón para eliminar dicho usuario
			JButton del = new JButton("Eliminar");
			spring.putConstraint(SpringLayout.NORTH, del, i*50, SpringLayout.NORTH, panel);
			spring.putConstraint(SpringLayout.WEST, del, 450, SpringLayout.WEST, panel);
			del.addActionListener(this);
			
			del.setActionCommand(Long.toString(rec.getIdReceta()));
			panel.add(del);
			borrar.add(del); //y añado el botón al array de botones del
			
			//Botón para modificar
			//Creo el botón para eliminar dicho usuario
			JButton mod = new JButton("Modificar");
			spring.putConstraint(SpringLayout.NORTH, mod, i*50, SpringLayout.NORTH, panel);
			spring.putConstraint(SpringLayout.WEST, mod, 600, SpringLayout.WEST, panel);
			mod.addActionListener(this);
			
			mod.setActionCommand("M"+Long.toString(rec.getIdReceta()));
			panel.add(mod);
			borrar.add(mod); //y añado el botón al array de botones del
			
			i++;
			
		}
		
		
		scroll.getViewport().setOpaque(false);
		scroll.setOpaque(false);
		getContentPane().add(scroll, BorderLayout.CENTER);
		setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().toString().charAt(0) == 'M'){
			String pass = new String(passwordField.getPassword());
			if (pass.isEmpty()){
				JOptionPane.showMessageDialog(null, "Para modificar una receta debe introducir su contraseña.");
			}
			else{
				Long id= Long.parseLong(e.getActionCommand().toString().substring(1));
				
				//en primer lugar se recupera la receta completa
				ClientConfig config = new ClientConfig();
				Client client = ClientBuilder.newClient(config);
				WebTarget service = client.target(getBaseURI(ruta_base));		
				
				Response r = service.path("retrieve/" +id).request().get();
				Receta receta = r.readEntity(Receta.class);
				
				System.out.println(r.toString());
				
				try{
					this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
					ModificarReceta frame = new ModificarReceta(ruta_base, pass, receta);
					frame.setVisible(true);
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		
		else{
			long id = Long.parseLong(e.getActionCommand().toString());
			
			ClientConfig config = new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			WebTarget service = client.target(getBaseURI(ruta_base));		
			
			Entity<Long> recetaId = Entity.entity(new Long(id), MediaType.APPLICATION_JSON_TYPE);
			Response response = service.path("delete").request().post(recetaId);
			
		   System.out.println(response.toString());
		   
		   
		   if(response.getStatus() == 200){
			    JOptionPane.showMessageDialog(null, "¡Receta Eliminada!");
			    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			    
		    }
		    else{
		    	JOptionPane.showMessageDialog(null, "Lo sentimos. Ha ocurrido un error con el servidor.\n Prueba de nuevo.");
		    }
		}
	}
	
	private static URI getBaseURI(String ruta_base) {
	    return UriBuilder.fromUri(ruta_base + "/Recypapp/rest/receta").build();
	  }
}
