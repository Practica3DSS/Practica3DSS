package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
import clases.Ingrediente;
import clases.ListId;
import clases.ListIngrediente;
import clases.Receta;

public class ModificarReceta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tTitulo;
	private JLabel lblDuracin;
	private JTextField tDuracion;
	private JTextField tPersonas;
	private JTextField tIngrediente;
	private JTextField tCantIngrediente;
	private JEditorPane editorDescripcion;
	List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
	

	/**
	 * Create the frame.
	 */
	public ModificarReceta(String despliegue, String pass, Receta receta) {
		final String ruta_base = despliegue;
		final Receta rec = receta;
		final String password = pass;
		setBounds(100, 100, 528, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblTtulo = new JLabel("*T\u00EDtulo:");
		sl_contentPane.putConstraint(SpringLayout.EAST, lblTtulo, 62, SpringLayout.WEST, contentPane);
		contentPane.add(lblTtulo);
		
		tTitulo = new JTextField();
		tTitulo.setText(rec.getNombre());
		sl_contentPane.putConstraint(SpringLayout.WEST, tTitulo, 19, SpringLayout.EAST, lblTtulo);
		sl_contentPane.putConstraint(SpringLayout.EAST, tTitulo, -249, SpringLayout.EAST, contentPane);
		contentPane.add(tTitulo);
		tTitulo.setColumns(10);
		
		lblDuracin = new JLabel("Duraci\u00F3n:");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblTtulo, -29, SpringLayout.NORTH, lblDuracin);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblDuracin, 20, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblTtulo, 0, SpringLayout.WEST, lblDuracin);
		contentPane.add(lblDuracin);
		
		tDuracion = new JTextField();
		tDuracion.setText(String.valueOf(rec.getDuracion()));
		sl_contentPane.putConstraint(SpringLayout.SOUTH, tTitulo, -25, SpringLayout.NORTH, tDuracion);
		sl_contentPane.putConstraint(SpringLayout.EAST, tDuracion, 0, SpringLayout.EAST, tTitulo);
		sl_contentPane.putConstraint(SpringLayout.WEST, tDuracion, 15, SpringLayout.EAST, lblDuracin);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, tDuracion, 0, SpringLayout.SOUTH, lblDuracin);
		tDuracion.setColumns(10);
		contentPane.add(tDuracion);
		
		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -15, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblDuracin, -39, SpringLayout.NORTH, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, -139, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -29, SpringLayout.SOUTH, contentPane);
		contentPane.add(scrollPane);
		
		editorDescripcion = new JEditorPane();
		scrollPane.setViewportView(editorDescripcion);
		editorDescripcion.setText(rec.getDescripcion());
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = tTitulo.getText();
				String descripcion = editorDescripcion.getText();
				int duracion = Integer.parseInt(tDuracion.getText());
				int comensales = Integer.parseInt(tPersonas.getText());
				ListIngrediente lista_ingredientes = rec.getIngredientes();
				ListId lista_tags = rec.getTags();
				Imagen img = null;
				
				
				/*if(ingredientes.size() == 0){
					 JOptionPane.showMessageDialog(null, "Introduzca al menos un ingrediente.");
				}*/
				if(nombre.isEmpty() || descripcion.isEmpty() || tPersonas.getText().isEmpty()){
					 JOptionPane.showMessageDialog(null, "Los campos marcados con '*' son obligatorios.");
				}
				else{
					ClientConfig config = new ClientConfig();
					Client client = ClientBuilder.newClient(config);
					WebTarget service = client.target(getBaseURI(ruta_base));					
				
					Entity<Receta> recetaId = Entity.entity(new Receta(rec.getIdReceta(), nombre, descripcion, duracion, comensales, img, rec.getUsuario(),
							rec.getUsuario_nick(), lista_ingredientes, lista_tags), MediaType.APPLICATION_JSON_TYPE);
					
					Response response = service.path("update").request().header("pass", password).post(recetaId);
					
				   System.out.println(response.toString());
				   
				   
				   if(response.getStatus() == 200){
					    JOptionPane.showMessageDialog(null, "¡Receta modificada!");
					    Cerrar();
				    }
				    else{
				    	JOptionPane.showMessageDialog(null, "Lo sentimos. Ha ocurrido un error con el servidor.\n Prueba de nuevo.");
				    }
				}
				
			}
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnModificar, 20, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnModificar, 0, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnModificar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cerrar();
			}
		});
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnCancelar, 0, SpringLayout.SOUTH, btnModificar);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCancelar, 0, SpringLayout.EAST, scrollPane);

		contentPane.add(btnCancelar);
		
		JLabel lblDescripcin = new JLabel("*Descripci\u00F3n:");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblDescripcin, 0, SpringLayout.WEST, lblTtulo);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblDescripcin, -6, SpringLayout.NORTH, scrollPane);
		contentPane.add(lblDescripcin);
		
		JPanel panel = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel, 0, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, tTitulo);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel, -14, SpringLayout.NORTH, tTitulo);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel, 197, SpringLayout.WEST, contentPane);
		panel.setBackground(Color.WHITE);
		contentPane.add(panel);
		
		JLabel lblIngredientes = new JLabel("Ingrediente:");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblIngredientes, 106, SpringLayout.EAST, panel);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblIngredientes, 171, SpringLayout.EAST, panel);
		contentPane.add(lblIngredientes);
		
		JTextArea areaIngredientes = new JTextArea();
		contentPane.add(areaIngredientes);
		
		JLabel lblPersonas = new JLabel("*Comensales:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblPersonas, 0, SpringLayout.NORTH, lblDuracin);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPersonas, 50, SpringLayout.EAST, tDuracion);
		contentPane.add(lblPersonas);
		
		tPersonas = new JTextField();
		tPersonas.setText(String.valueOf(rec.getCantidad_comensales()));
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblIngredientes, -135, SpringLayout.NORTH, tPersonas);
		sl_contentPane.putConstraint(SpringLayout.NORTH, tPersonas, -3, SpringLayout.NORTH, lblDuracin);
		sl_contentPane.putConstraint(SpringLayout.WEST, tPersonas, 6, SpringLayout.EAST, lblPersonas);
		tPersonas.setColumns(10);
		contentPane.add(tPersonas);
		
		JLabel lblCantidad = new JLabel("Cantidad:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblCantidad, 13, SpringLayout.SOUTH, lblIngredientes);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblCantidad, 0, SpringLayout.WEST, lblIngredientes);
		contentPane.add(lblCantidad);
		
		tIngrediente = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.WEST, tIngrediente, 3, SpringLayout.EAST, lblIngredientes);
		sl_contentPane.putConstraint(SpringLayout.EAST, tIngrediente, 0, SpringLayout.EAST, scrollPane);
		contentPane.add(tIngrediente);
		tIngrediente.setColumns(10);
		
		tCantIngrediente = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, tCantIngrediente, 0, SpringLayout.NORTH, lblCantidad);
		sl_contentPane.putConstraint(SpringLayout.WEST, tCantIngrediente, 0, SpringLayout.WEST, tIngrediente);
		sl_contentPane.putConstraint(SpringLayout.EAST, tCantIngrediente, 0, SpringLayout.EAST, scrollPane);
		
		
		tCantIngrediente.setColumns(10);
		contentPane.add(tCantIngrediente);
		
		JButton btnAadirIngrediente = new JButton("A\u00F1adir ingrediente");
		btnAadirIngrediente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ingr = tIngrediente.getText();
				String cant = tCantIngrediente.getText();
				
				if(ingr.isEmpty() || cant.isEmpty()){
					JOptionPane.showMessageDialog(null, "Debe incluir tanto el ingrediente como la cantidad.");
				}
				else{
					Ingrediente ingrediente = new Ingrediente(0, ingr, cant);
					ingredientes.add(ingrediente);
					tIngrediente.setText("");
					tCantIngrediente.setText("");
					JOptionPane.showMessageDialog(null, "Ingrediente añadido.");
				}
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnAadirIngrediente, 6, SpringLayout.SOUTH, tCantIngrediente);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnAadirIngrediente, 0, SpringLayout.WEST, tIngrediente);
		contentPane.add(btnAadirIngrediente);
	}
	
	public void Cerrar(){
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	private static URI getBaseURI	(String ruta_base) {
	    return UriBuilder.fromUri(ruta_base + "/Recypapp/rest/receta").build();
	  }
}
