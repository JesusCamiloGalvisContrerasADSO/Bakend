/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.Animal;
import logica.Peso_animal;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author hp
 */
public class Peso_animalJpaController implements Serializable {

    public Peso_animalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Peso_animal peso_animal) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Animal animal = peso_animal.getAnimal();
            if (animal != null) {
                animal = em.getReference(animal.getClass(), animal.getId_animal());
                peso_animal.setAnimal(animal);
            }
            em.persist(peso_animal);
            if (animal != null) {
                animal.getPesos().add(peso_animal);
                animal = em.merge(animal);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Peso_animal peso_animal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peso_animal persistentPeso_animal = em.find(Peso_animal.class, peso_animal.getId_peso());
            Animal animalOld = persistentPeso_animal.getAnimal();
            Animal animalNew = peso_animal.getAnimal();
            if (animalNew != null) {
                animalNew = em.getReference(animalNew.getClass(), animalNew.getId_animal());
                peso_animal.setAnimal(animalNew);
            }
            peso_animal = em.merge(peso_animal);
            if (animalOld != null && !animalOld.equals(animalNew)) {
                animalOld.getPesos().remove(peso_animal);
                animalOld = em.merge(animalOld);
            }
            if (animalNew != null && !animalNew.equals(animalOld)) {
                animalNew.getPesos().add(peso_animal);
                animalNew = em.merge(animalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = peso_animal.getId_peso();
                if (findPeso_animal(id) == null) {
                    throw new NonexistentEntityException("The peso_animal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peso_animal peso_animal;
            try {
                peso_animal = em.getReference(Peso_animal.class, id);
                peso_animal.getId_peso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The peso_animal with id " + id + " no longer exists.", enfe);
            }
            Animal animal = peso_animal.getAnimal();
            if (animal != null) {
                animal.getPesos().remove(peso_animal);
                animal = em.merge(animal);
            }
            em.remove(peso_animal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Peso_animal> findPeso_animalEntities() {
        return findPeso_animalEntities(true, -1, -1);
    }

    public List<Peso_animal> findPeso_animalEntities(int maxResults, int firstResult) {
        return findPeso_animalEntities(false, maxResults, firstResult);
    }

    private List<Peso_animal> findPeso_animalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Peso_animal.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Peso_animal findPeso_animal(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Peso_animal.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeso_animalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Peso_animal> rt = cq.from(Peso_animal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
