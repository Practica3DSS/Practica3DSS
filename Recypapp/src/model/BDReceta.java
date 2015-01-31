package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
 
/**
 * Implementa las funciones necesarias para realizar operaciones sobre la base de datos de usuarios.
 * @author silt
 *
 */
public class BDReceta {
	private static final String PERSISTENCE_UNIT_NAME = "Recypapp"; /** Nombre de la unidad de persistencia */
	private static EntityManagerFactory factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); /** Factoria de manejadores de la base de datos. */
    
	/**
	 * Inserta una receta en la base de datos.
	 * @param receta Receta.
	 * @return True si ha tenido éxito, False si no.
	 */
	public static boolean insertar(Receta receta){
		boolean hecho = false;
		EntityManager em = factoria.createEntityManager();
		
		em.getTransaction().begin();
		
		try{
			em.persist(receta);
			em.getTransaction().commit();
			hecho = true;
		}
		catch(IllegalArgumentException | EntityExistsException ie){
			System.out.println("Error al insertar: " + ie.getLocalizedMessage());
				
			hecho = false;
		}
		catch(RollbackException re){
			System.out.println("Error al insertar: Error commit: " + re.getLocalizedMessage());
				
			hecho = false;
		}
		finally{
			em.close();
		}
		
		return hecho;
    }
    
	/**
	 * Actualiza el estado de una receta de la base de datos.
	 * @param receta Receta.
	 * @return True si ha tenido éxito, False si no.
	 */
    public static boolean actualizar(Receta receta){
    	boolean hecho = false;
    	EntityManager em = factoria.createEntityManager();
    	
		em.getTransaction().begin();
			
		try{
			em.merge(receta);
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
		finally{
			em.close();
		}
		
		return hecho;
    }
    
    /**
     * Elimina una receta de la base de datos.
     * @param id Id.
     * @return True si ha tenido éxito, False si no.
     */
    public static boolean eliminar(long id){
    	boolean hecho = false;
    	EntityManager em = factoria.createEntityManager();
    	
    	em.getTransaction().begin();
		
    	Receta receta;
    	
		try{
			receta = em.getReference(Receta.class, id);
            receta.getIdReceta();
            em.remove(receta);
            em.getTransaction().commit();
			hecho = true;
		}
		catch (EntityNotFoundException enfe) {
			System.out.println("Error al eliminar: " + enfe.getLocalizedMessage());
			
			hecho = false;
		}
		catch(IllegalArgumentException ie){
			System.out.println("Error al eliminar: " + ie.getLocalizedMessage());
			
			hecho = false;
		}
		catch(RollbackException re){
			System.out.println("Error al eliminar: Error commit: " + re.getLocalizedMessage());
			
			hecho = false;
		}
		finally{
			em.close();
		}
		
		return hecho;
    }
    
    /**
     * Eliminar una recetas de un usuario de la base de datos.
     * @param id Id.
     * @return True si ha tenido éxito, False si no.
     */
    public static boolean eliminarRecetasDeUsuario(long id){
    	boolean hecho = false;
    	EntityManager em = factoria.createEntityManager();
    	
    	em.getTransaction().begin();
		
		try{
			Query q = em.createQuery("SELECT r FROM Receta r WHERE r.usuario.idUsuario = :idUsuario");
	    	
	    	q.setParameter("idUsuario", id);
	    	
	    	@SuppressWarnings("unchecked")
			List<Receta> lista = (List<Receta>)(q.getResultList());
			
			for(Receta receta : lista){
				em.remove(receta);
			}
			
			em.getTransaction().commit();
			hecho = true;
		}
		catch (EntityNotFoundException enfe) {
			System.out.println("Error al eliminar: " + enfe.getLocalizedMessage());
			
			hecho = false;
		}
		catch(IllegalArgumentException ie){
			System.out.println("Error al eliminar: " + ie.getLocalizedMessage());
			
			hecho = false;
		}
		catch(RollbackException re){
			System.out.println("Error al eliminar: Error commit: " + re.getLocalizedMessage());
			
			hecho = false;
		}
		finally{
			em.close();
		}
		
		return hecho;
    }    
    
    /**
     * Devuelve la lista completa de las recetas de la base de datos.
     * @return Lista de recetas.
     */
    public static List<Receta> obtenerLista(){
    	EntityManager em = factoria.createEntityManager();
    	
    	Query q = em.createQuery("SELECT r FROM Receta r");
    	
    	@SuppressWarnings("unchecked")
		List<Receta> list = (List<Receta>)(q.getResultList());
    	
    	em.close();
    	
		return list;
    }
    
    /**
     * Devuelve la lista completa de las recetas de la base de datos de un Usuario.
     * @return Lista de recetas.
     */
    public static List<Receta> obtenerLista(long idUsuario){
    	EntityManager em = factoria.createEntityManager();
    	
    	Query q = em.createQuery("SELECT r FROM Receta r WHERE r.usuario.idUsuario = :idUsuario");
    	
    	q.setParameter("idUsuario", idUsuario);
    	
    	@SuppressWarnings("unchecked")
		List<Receta> list = (List<Receta>)(q.getResultList());
    	
    	em.close();
    	
		return list;
    }
    
    /**
     * Devuelve la lista completa de las recetas de la base de datos de un Usuario.
     * @return Lista de recetas.
     */
    public static List<Receta> obtenerLista(long idTag, long idUsuario, String name_contains, int cantidad_comensales, 
    		int min_duration, int max_duration){
    	EntityManager em = factoria.createEntityManager();
    	
    	String extra = "";
    	List<String> clause = new LinkedList<String>();
    	List<String> params_name = new LinkedList<String>();
    	List<Object> params = new LinkedList<Object>();
 
    	if(idTag > -1){
    		extra = "inner join r.tags y";
    		clause.add("y.idTag = :idTag");
    		params_name.add("idTag");
    		params.add(idTag);
    	}
    	
    	if(idUsuario > -1){
    		clause.add("r.usuario.idUsuario = :idUsuario");
    		params_name.add("idUsuario");
    		params.add(idUsuario);
    	}
    	
    	if(!name_contains.equals("")){
    		clause.add("r.nombre LIKE :nombre");
    		params_name.add("nombre");
    		params.add(name_contains);
    	}
    	
    	if(cantidad_comensales > 0){
    		clause.add("r.cantidad_comensales = :cantidad_comensales");
    		params_name.add("cantidad_comensales");
    		params.add(cantidad_comensales);
    	}
    	
    	if(min_duration > 0){
    		clause.add("r.duracion >= :min_duration");
    		params_name.add("min_duration");
    		params.add(min_duration);
    	}
    	
    	if(max_duration > 0){
    		clause.add("r.duracion <= :max_duration");
    		params_name.add("max_duration");
    		params.add(max_duration);
    	}
    	
    	if(clause.size() > 0){
    		Iterator<String> clauseIter = clause.iterator();
    		
    		extra += "WHERE (";
    		
    		extra += " " + clauseIter.next() + " ";
    		
    	    while (clauseIter.hasNext()){
    	    	extra += "AND " + clauseIter.next() + " ";
    	    }
    	    
    	    extra += ")";
    	}
    	
    	Query q = em.createQuery("SELECT r FROM Receta r" + extra);
    	
    	Iterator<String> paramsNameIter = params_name.iterator();
    	Iterator<Object> paramsIter = params.iterator();
    	
    	while (paramsNameIter.hasNext()){
        	q.setParameter(paramsNameIter.next(), paramsIter.next());
	    }
    	
    	@SuppressWarnings("unchecked")
		List<Receta> list = (List<Receta>)(q.getResultList());
    	
    	em.close();
    	
		return list;
    }
}