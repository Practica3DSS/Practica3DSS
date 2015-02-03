package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;

public class BDUsuario {
	private static final String PERSISTENCE_UNIT_NAME = "Recypapp";
	  private static EntityManagerFactory factoria;
	
	  
	  /**Inserta un usuario en la base de datos
	   * @param usuario Usuario
	   * @return True si ha tenido exito, False en caso contrario.*/
	  public static boolean insertar(Usuario usuario){
		  factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	  	  EntityManager em = factoria.createEntityManager();
		  boolean hecho = false;
		  
		  usuario.setIdUsuario(0);
		  
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
 		   //usuario con dicho email. Si la lista no est� vac�a, devuelve true.
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
 		    * Si la lista no est� vac�a tras la consulta, es porque el usuario con el email en custi�n s� existe.
 		    */
//	 		   if (!listaUsuarios.isEmpty()){
//	 			   existe = true;
//	 		   }
 	   }
 	   catch(Exception e){
 		   e.printStackTrace();
 	   } 	  
 	   
 	   em.close();
 	   
 	   return existe;
	    }
    
    
    /** Elimina un usuario de la base de datos.
     *  
     * @param id
     * @return True si ha tenido exito, false en caso contrario.
     */
    public static boolean eliminar(long id){
    	factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	  	EntityManager em = factoria.createEntityManager();
		boolean hecho = false;

  	    em.getTransaction().begin();
  	    Query q = em.createQuery("SELECT t FROM Usuario t WHERE t.idUsuario = :idUsuario");
  	    q.setParameter("idUsuario", id);
  	    @SuppressWarnings("unchecked")
		List<Usuario> listaUsuarios = q.getResultList();
  	    for(Usuario user: listaUsuarios){
  	    	if(user.getIdUsuario() == id){
  	    		Query q2 = em.createQuery("SELECT r FROM Receta r WHERE r.usuario.idUsuario = :idUsuario");
  		    	
  		    	q2.setParameter("idUsuario", user.getIdUsuario());
  		    	
  		    	@SuppressWarnings("unchecked")
  				List<Receta> lista = (List<Receta>)(q2.getResultList());
  				
  				for(Receta receta : lista){
  					em.remove(receta);
  				}
  	    		
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
    
    @SuppressWarnings("unchecked")
	public static Usuario getUsuarioPorEmail(String email){
    	factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	  	EntityManager em = factoria.createEntityManager();
	  	Usuario aux = null;	  	
	  	
  	    em.getTransaction().begin();
  	    Query q = em.createQuery("SELECT t FROM Usuario t WHERE t.email = :email");
		List<Usuario> listaUsuarios = q.getResultList();
  	    
  	    if (listaUsuarios.size() == 1){ 	    
  	    	Query q2 = em.createQuery("SELECT r FROM Receta r WHERE r.usuario.idUsuario = :idUsuario");
  	    	
  	    	aux = listaUsuarios.get(0);
  		    q2.setParameter("idUsuario", aux.getIdUsuario());
  		    aux.setRecetas((List<Receta>)q2.getResultList());
	    }
  	    
  	    em.close();

		return aux;
    }
    
    /**Obtener un usuario sin sus recetas a partir de su correo
     * 
     * @param email
     * @return Usuario usuario si encuntra un usuario con el email introducido
     */
    
    @SuppressWarnings("unchecked")
	public static Usuario getUsuarioPorEmailSinRecetas(String email){
    	factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	  	EntityManager em = factoria.createEntityManager();
	  	Usuario aux = null;	  	
	  	
  	    em.getTransaction().begin();
  	    Query q = em.createQuery("SELECT t FROM Usuario t WHERE t.email = :email");
		List<Usuario> listaUsuarios = q.getResultList();
  	    
  	    if (listaUsuarios.size() == 1){ 	    
  	    	aux = listaUsuarios.get(0);
	    }
  	    
  	    em.close();

		return aux;
    }
    
    /**Obtener un usuario a partir de su id
     * 
     * @param id
     * @return Usuario usuario si encuntra un usuario con el id introducido
     */
    
    @SuppressWarnings("unchecked")
	public static Usuario getUsuarioPorId(long id){
    	factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	  	EntityManager em = factoria.createEntityManager();
  	    Usuario user = em.getReference(Usuario.class, id);
  	    Query q2 = em.createQuery("SELECT r FROM Receta r WHERE r.usuario.idUsuario = :idUsuario");
    	
	    q2.setParameter("idUsuario", user.getIdUsuario());
	    user.setRecetas((List<Receta>)q2.getResultList());
  	    
  	    em.close();
  	    
  	    return user;
    }
    
    /**Obtener un usuario a partir de su id
     * 
     * @param id
     * @return Usuario usuario sin sus recetas si encuentra un usuario con el id introducido
     */
    
	public static Usuario getUsuarioPorIdSinRecetas(long id){
    	factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	  	EntityManager em = factoria.createEntityManager();
  	    Usuario user = em.getReference(Usuario.class, id);
  	    
  	    em.close();
  	    
  	    return user;
    }
    
    /**
     * Devuelve la lista completa de usuarios de la base de datos.
     * @return Lista de usuarios.
     */
    @SuppressWarnings("unchecked")
	public static List<Usuario> obtenerLista(){
    	factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    	EntityManager em = factoria.createEntityManager();
    	
    	Query q = em.createQuery("SELECT u FROM Usuario u");
		List<Usuario> list = (List<Usuario>)(q.getResultList());
    	
    	for(Usuario u : list){
    		Query q2 = em.createQuery("SELECT r FROM Receta r WHERE r.usuario.idUsuario = :idUsuario");
		    	
		    q2.setParameter("idUsuario", u.getIdUsuario());
    		u.setRecetas((List<Receta>)q2.getResultList());
    	}
    	
    	em.close();
    	
		return list;
    }
    
    /**
     * Devuelve la lista completa de usuarios sin las recetas de la base de datos.
     * @return Lista de usuarios.
     */
    @SuppressWarnings("unchecked")
	public static List<Usuario> obtenerListaSinRecetas(){
    	factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    	EntityManager em = factoria.createEntityManager();
    	
    	Query q = em.createQuery("SELECT u FROM Usuario u");
		List<Usuario> list = (List<Usuario>)(q.getResultList());
    	
    	em.close();
    	
		return list;
    }
    
    public static boolean actualizar(Usuario usuario){
    	boolean hecho = false;
    	EntityManager em = factoria.createEntityManager();
    	
		em.getTransaction().begin();
			
		try{
			em.merge(usuario);
			em.getTransaction().commit();
			hecho = true;
		}
		catch(IllegalArgumentException ie){
			System.out.println("Error al actualizar: " + ie.getLocalizedMessage());
			
			hecho = false;
		}
		catch(RollbackException re){
			System.out.println("Error al actualizar: Error commit: " + re.getLocalizedMessage());
			
			hecho = false;
		}
		
		em.close();
		return hecho;
    }
}



	
