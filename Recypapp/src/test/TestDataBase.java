package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import model.BDReceta;
import model.BDTag;
import model.BDUsuario;
import model.Imagen;
import model.Ingrediente;
import model.Receta;
import model.Tag;
import model.Usuario;

public class TestDataBase {
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
        File file = null;
        Imagen myExample = new Imagen();
        Imagen myExample2 = new Imagen();
        myExample.setMimeType("image/jpg");
        myExample.setFileName("mushroom");
        myExample2.setMimeType("image/jpg");
        myExample2.setFileName("mushroom");
        file = new File("images/mushroom.jpg");
        try
        {
            // Lets open an image file
            myExample.setImageFile(readImageOldWay(file));
            myExample2.setImageFile(readImageOldWay(file));
        }
        catch (IOException ex)
        {
        }

        //BDImagen.insertar(myExample);
        //BDImagen.insertar(myExample2);

        
        /*
        Usuario user = new Usuario("Nick", "password", "email", myExample);
		
		//Insertar receta
		List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
		
		ingredientes.add(new Ingrediente("Test", "1"));
		
		Receta receta  = new Receta("nombre", "descripcion", 120,
				10, myExample2, user, ingredientes);
		
		ingredientes.get(0).setReceta(receta);
		
		BDUsuario.insertar(user);
		BDReceta.insertar(receta);
		
		//Actualizar receta
		receta.setNombre("nombre2");
		receta.getIngredientes().add(new Ingrediente("Test", "1"));
		receta.getIngredientes().get(1).setReceta(receta);
		
		BDReceta.actualizar(receta);

		user = BDUsuario.getUsuarioPorId(user.getIdUsuario());
		
		System.out.println(user.getRecetas().size());
		
		//Eliminar
		BDUsuario.eliminar(user.getIdUsuario());
		*/
        
        BDTag.insertar(new Tag("Test"));
	}

}
