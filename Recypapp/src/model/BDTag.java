package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;

public class BDTag {
	private static final String PERSISTENCE_UNIT_NAME = "Recypapp"; /** Nombre de la unidad de persistencia */
	private static EntityManagerFactory factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); /** Factoria de manejadores de la base de datos. */
    
	/**
	 * 
	 * @param Tag tag
	 * @return hecho. True si se ha insertado correctamente y false en caso contrario
	 */

	public static boolean insertar(Tag tag){
	    EntityManager em = factoria.createEntityManager();
		boolean hecho = false;
		try{
			em.getTransaction().begin();
			em.persist(tag);
			em.getTransaction().commit();
			em.close();
			hecho = true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return hecho;
	}
	
	/**
	 * 
	 * @param tag
	 * @return true si se ha eliminado correctamente, false en caso contrario.
	 */
	public static boolean eliminar(Tag tag){
	  	EntityManager em = factoria.createEntityManager();
		boolean hecho = false;

  	    em.getTransaction().begin();
  	    Query q = em.createQuery("SELECT t FROM Tag t WHERE t.idTag= :idTag");
  	    q.setParameter("idTag", tag.getIdTag());
  	    @SuppressWarnings("unchecked")
		List<Tag> listaTags = q.getResultList();
  	    
  	    for (Tag tag_aux : listaTags){
  	    	if (tag_aux.getIdTag() == tag.getIdTag()){
  	    		em.remove(tag_aux);
  	    		hecho = true;
 
  	    	}
  	    }
  	    em.getTransaction().commit();
  	    em.close();
  	    return hecho;
	}

	
	/**
	 * 
	 * @param tag
	 * @return true si se ha actualizado correctamente, false en caso contrario.
	 */
	public static boolean actualizar(Tag tag){
    	boolean hecho = false;
    	EntityManager em = factoria.createEntityManager();
    	
		em.getTransaction().begin();
			
		try{
			em.merge(tag);
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
	
	/**
	 * 
	 * @return list -> lista con todos los tags guardados en la base de datos.
	 */
	@SuppressWarnings("unchecked")
	public static List<Tag> listarTags(){
		EntityManager em = factoria.createEntityManager();
		
		Query q = em.createQuery("SELECT t FROM Tag t");
		List<Tag> list = (List<Tag>)(q.getResultList());
		
		return list;
		
	}

}
