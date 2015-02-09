package client;

import java.io.File;
import java.io.FileFilter;

import javax.swing.DefaultListModel;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class busquedaImagen {

     /** Ruta donde se encuentran las imagenes */
    //
    //private String appPath = System.getProperties().getProperty("user.dir");
    //public File directorio = new File( appPath + "\\imagenes\\" ); 
    //
    public File directorio = new File( "e:\\imagenes\\" );    

    /** Si la extensión es .JPG retorna TRUE, caso contrario FALSE */
    private FileFilter filefilter = new FileFilter() {        
        @Override
        public boolean accept(File file) {           
            return file.getName().endsWith(".jpg");
        }
    };

    /**
 * Busca las imagenes que se encuentran en un directorio
 * @param parametro String que corresponde al nombre del archivo a buscar
 * @return DefaultListModel
 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public DefaultListModel buscar( String parametro )
    {
        DefaultListModel defaultListModel = new DefaultListModel ();
        // Si es un directorio valido
        if ( directorio.isDirectory() ) {   
            // obtenemos su contenido
            File[] ficheros = directorio.listFiles( filefilter );             
            //y lo llenamos en un DefaultListModel
            for ( File fichero : ficheros ) 
            {
                if( fichero.getName().toUpperCase().indexOf( parametro.toUpperCase() ) >= 0 )
                {                        
                    defaultListModel.addElement( fichero.getName() );
                }                  
            }
        }        
        return defaultListModel;
    }

}//-->fin clase