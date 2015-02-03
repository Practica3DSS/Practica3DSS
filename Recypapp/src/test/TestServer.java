package test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

import crud.data.Imagen;

import org.glassfish.jersey.client.ClientConfig;

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
        
        File file = new File("images/mushroom.jpg");
        try
        {
            // Lets open an image file
            myExample.setImageFile(readImageOldWay(file));
        }
        catch (IOException ex)
        {
        }
		
		Entity<Usuario> customerId = Entity.entity(new Usuario(-1, "n1", "pass", "e2", myExample), MediaType.APPLICATION_JSON_TYPE);

        Response response = service.path("insert").request().post(customerId);
		
		System.out.print(response.toString());
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8081/Recypapp/rest/usuario").build();
	}
}
