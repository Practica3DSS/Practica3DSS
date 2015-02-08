package test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.Entity;

import org.glassfish.jersey.client.ClientConfig;

import crud.data.Imagen;
import crud.data.Ingrediente;
import crud.data.ListId;
import crud.data.ListIngrediente;
import crud.data.Receta;
import crud.data.UserLoggin;
import crud.data.Usuario;

public class TestServer {
	 /**
     * Use the boring no NIO way to read a file.. Boring
     * @return
     * @throws IOException
     */
    @SuppressWarnings("resource")
	public static byte[] readImageOldWay(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE)
        {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
        {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length)
        {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();

        return bytes;
    }

	public static void main(String[] args) {
		ClientConfig config = new ClientConfig();
	    Client client = ClientBuilder.newClient(config);
		WebTarget service = client.target(getBaseURI());
		
		Imagen myExample = new Imagen();
		
		myExample.setMimeType("image/jpg");
        myExample.setFileName("mushroom");
        myExample.setImageFile(new byte[0]);
        
        
        File file = new File("images/mushroom.jpg");
        /*
        try
        {
            // Lets open an image file
            myExample.setImageFile(readImageOldWay(file));
        }
        catch (IOException ex)
        {
        }
        */
	
        myExample.setId((long)102);
		Entity<Usuario> customerId = Entity.entity(new Usuario(101, "n1", "pass", "email", myExample), MediaType.APPLICATION_JSON_TYPE);

        Response response = service.path("update").request().header("pass", "password").post(customerId);

        
        /*
		ListId tags = new ListId(new ArrayList<Long>());
		ListIngrediente ingredientes = new ListIngrediente(new ArrayList<crud.data.Ingrediente>());
		
		ingredientes.getIngredientes().add(new Ingrediente(0, "Putas", "2"));
		ingredientes.getIngredientes().add(new Ingrediente(0, "Más Putas", "3"));
		ingredientes.getIngredientes().add(new Ingrediente(0, "Más Más Putas", "10"));
		
		tags.getId().add((long) 351);
		
		Entity<Receta> customerId = Entity.entity(new Receta(0, "Orgía", "Pos eso", 200, 1, myExample, 101, "", ingredientes, tags), MediaType.APPLICATION_JSON_TYPE);

        Response response = service.path("insert").request().post(customerId);*/
		
        /*
		Entity<Long> customerId = Entity.entity((long) 501, MediaType.APPLICATION_JSON_TYPE);

        Response response = service.path("delete").request().post(customerId);
        */
		
		/*
		Entity<UserLoggin> customerId = Entity.entity(new UserLoggin("email", "password"), MediaType.APPLICATION_XML_TYPE);

        Response response = service.path("retrieveByEmail").request().post(customerId);
       
        Usuario customer = response.readEntity(Usuario.class);
        */
		
		System.out.print(response.toString());
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8081/Recypapp/rest/usuario").build();
	}
}
