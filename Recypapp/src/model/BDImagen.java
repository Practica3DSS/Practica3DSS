package model;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 *
 * @author Paul Snow
 */
public class BDImagen {
    private static final String PERSISTENCE_UNIT_NAME = "Recypapp"; /** Nombre de la unidad de persistencia */
    private static EntityManagerFactory factoria = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); /** Factoria de manejadores de la base de datos. */

    /**
     *
     * @param imageExample
     */
    public static boolean insertar(Imagen imageExample) {
        EntityManager em = null;
        boolean hecho = false;
        
        try {
            em = factoria.createEntityManager();
            em.getTransaction().begin();
            em.persist(imageExample);
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
        finally {
            if (em != null) {
                em.close();
            }
        }
        
        return hecho;
    }
    
    /**
     *
     * @param imageExample
     */
    public static boolean actualizar(Imagen imageExample){
        EntityManager em = null;
        boolean hecho = false;
        
        try {
            em = factoria.createEntityManager();
            em.getTransaction().begin();
            imageExample = em.merge(imageExample);
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
        finally {
            if (em != null) {
                em.close();
            }
        }
        
        return hecho;
    }
    
    /**
     *
     * @param id
     */
    public static boolean eliminar(Long id){
        EntityManager em = null;
        boolean hecho = false;
        
        try {
        	Imagen imageExample;
        	
            em = factoria.createEntityManager();
            em.getTransaction().begin();
            imageExample = em.getReference(Imagen.class, id);
            imageExample.getId();
            em.remove(imageExample);
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
        finally {
            if (em != null) {
                em.close();
            }
        }
        
        return hecho;
    }
    
    /**
     *
     * @return
     */
    public static List<Imagen> findImagenEntities() {
        return findImagenEntities(true, -1, -1);
    }
    
    /**
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public static List<Imagen> findImagenEntities(int maxResults, int firstResult) {
        return findImagenEntities(false, maxResults, firstResult);
    }
    
    /**
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    @SuppressWarnings("unchecked")
    private static List<Imagen> findImagenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = factoria.createEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Imagen as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     *
     * @param id
     * @return
     */
    public static Imagen findImagen(Long id) {
        EntityManager em = factoria.createEntityManager();
        try {
            return em.find(Imagen.class, id);
            /**
             *  Getting fresh results from database
             * To get fresh data from database there are several ways. First way is using refresh operations. Other ways are explained in the following sections.
             * There are two kinds of refresh operations â€“ portable EntityManager.refresh() and TopLink-specific refresh hint.
             * 1. EntityManager.refresh()
             * Use find and refresh combination like below. This is a simple and portable way to get fresh data.
             * Employee e = em.find(Employee.class, id);
             * try {
             *   em.refresh(e);
             * } catch(EntityNotFoundException ex){
             *   e = null;
             * }
             */
        } finally {
            em.close();
        }
    }
    
    /**
     *
     * @return
     */
    public static int getImagenCount() {
        EntityManager em = factoria.createEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Imagen as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public static int deleteAll() {
        EntityManager em = null;

        int returnInt = 0;

        try {
            em = factoria.createEntityManager();
            em.getTransaction().begin();

            try {
                returnInt = em.createNamedQuery("Imagen.deleteAll").executeUpdate();
            } catch (EntityNotFoundException enfe) {
            	
            }
            em.getTransaction().commit();
            em.flush();

        } finally {
            if (em != null) {
                em.close();
            }
        }

        return returnInt;

    }
}
