package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Usuario;

public class BDUsuario {
	private static final String PERSISTENCE_UNIT_NAME = "Recypapp";
	  private static EntityManagerFactory factoria;
	
	  
	  /**Inserta un usuario en la base de datos
	   * @param usuario Usuario
	   * @return True si ha tenido exito, False en caso contrario.*/
	  public static boolean insertar(Usuario usuario){
		  factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	  	  EntityManager em = factoria.createEntityManager();
		  private boolean hecho = false;
		  
		  if(!existeEmail(usuario.getEmail())){
	  	    	em.getTransaction().begin();
	  	    	em.persist(usuario);
	  	    	em.getTransaction().commit();
	  	    	em.close();
	  	    	hecho = true;
	  	    }
	 	    else{
	 	    	System.out.println("El usuario ya existe");
	 	    }
		  
		  return hecho;
	  }
	  
	  
	  /**Busca un email en la base de datos
	   * 
	   * @param email 
	   * @return existe si el email buscado existe en la base de datos
	   */
	  
    public static boolean existeEmail(String email) {
    	boolean existe = false;
       factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
 	   EntityManager em = factoria.createEntityManager();
 	   
 	   try{
 		   //Hace la consulta y recibe una lista con todos los usuarios cuyo email coincide (solo debe haber un
 		   //usuario con dicho email. Si la lista no está vacía, devuelve true.
 		   Query q = em.createQuery("SELECT t from Usuario t WHERE t.email = :email");
 		   q.setParameter("email", email);
 		   @SuppressWarnings("unchecked")
		List<Usuario> listaUsuarios = q.getResultList();
 		   for(Usuario user : listaUsuarios){
 			   if(user.getEmail().equals(email)){
 				   existe = true;
 			   }
 		   }
 		   /*
 		    * Si la lista no está vacía tras la consulta, es porque el usuario con el email en custión sí existe.
 		    */
//	 		   if (!listaUsuarios.isEmpty()){
//	 			   existe = true;
//	 		   }
 	   }
 	   catch(Exception e){
 		   e.printStackTrace();
 	   } 	  
 	   return existe;
	    }
    
    
    /** Elimina un usuario de la base de datos.
     *  
     * @param usuario
     * @return True si ha tenido exito, false en caso contrario.
     */
    public static boolean eliminar(Usuario usuario){
    	factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	  	EntityManager em = factoria.createEntityManager();
		private boolean hecho = false;
		

  	    em.getTransaction().begin();
  	    String email = usuario.getEmail();
  	    Query q = em.createQuery("SELECT t FROM Usuario t WHERE t.email = :email");
  	    q.setParameter("email", email);
  	    @SuppressWarnings("unchecked")
		List<Usuario> listaUsuarios = q.getResultList();
  	    for(Usuario user: listaUsuarios){
  	    	if(user.getEmail().equals(email)){
  	    		em.remove(user);
  	    		hecho = true;
  	    	}
  	    }

  	    em.getTransaction().commit();
  	    em.close();
  	    
  	    return hecho;
    }
    
    
    /**Obtener un usuario a partir de su correo
     * 
     * @param email
     * @return Usuario usuario si encuntra un usuario con el email introducido
     */
    
    public static Usuario getUsuarioPorEmail(String email){
    	factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	  	EntityManager em = factoria.createEntityManager();
		Usuario aux = new Usuario(); //Se devolverá si no se encuentra un usuario registracdo con el email que se recibe

  	    em.getTransaction().begin();
  	    Query q = em.createQuery("SELECT t FROM Usuario t WHERE t.email = :email");
  	    List<Usuario> listaUsuarios = q.getResultList();
  	    
  	    if (listaUsuarios.size() == 0){ 	    
  	    	return listaUsuarios[0];
    }
  	    else{
  	    	return aux;
  	    }
    
    }  
}



	
