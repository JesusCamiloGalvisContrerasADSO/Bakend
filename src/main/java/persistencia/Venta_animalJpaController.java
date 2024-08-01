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
import logica.Venta_animal;
import logica.Ventas;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author hp
 */
public class Venta_animalJpaController implements Serializable {

    public Venta_animalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta_animal venta_animal) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas venta = venta_animal.getVenta();
            if (venta != null) {
                venta = em.getReference(venta.getClass(), venta.getId_venta());
                venta_animal.setVenta(venta);
            }
            em.persist(venta_animal);
            if (venta != null) {
                venta.getVentaAnimales().add(venta_animal);
                venta = em.merge(venta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta_animal venta_animal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta_animal persistentVenta_animal = em.find(Venta_animal.class, venta_animal.getId_venta_animal());
            Ventas ventaOld = persistentVenta_animal.getVenta();
            Ventas ventaNew = venta_animal.getVenta();
            if (ventaNew != null) {
                ventaNew = em.getReference(ventaNew.getClass(), ventaNew.getId_venta());
                venta_animal.setVenta(ventaNew);
            }
            venta_animal = em.merge(venta_animal);
            if (ventaOld != null && !ventaOld.equals(ventaNew)) {
                ventaOld.getVentaAnimales().remove(venta_animal);
                ventaOld = em.merge(ventaOld);
            }
            if (ventaNew != null && !ventaNew.equals(ventaOld)) {
                ventaNew.getVentaAnimales().add(venta_animal);
                ventaNew = em.merge(ventaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = venta_animal.getId_venta_animal();
                if (findVenta_animal(id) == null) {
                    throw new NonexistentEntityException("The venta_animal with id " + id + " no longer exists.");
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
            Venta_animal venta_animal;
            try {
                venta_animal = em.getReference(Venta_animal.class, id);
                venta_animal.getId_venta_animal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta_animal with id " + id + " no longer exists.", enfe);
            }
            Ventas venta = venta_animal.getVenta();
            if (venta != null) {
                venta.getVentaAnimales().remove(venta_animal);
                venta = em.merge(venta);
            }
            em.remove(venta_animal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venta_animal> findVenta_animalEntities() {
        return findVenta_animalEntities(true, -1, -1);
    }

    public List<Venta_animal> findVenta_animalEntities(int maxResults, int firstResult) {
        return findVenta_animalEntities(false, maxResults, firstResult);
    }

    private List<Venta_animal> findVenta_animalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta_animal.class));
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

    public Venta_animal findVenta_animal(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta_animal.class, id);
        } finally {
            em.close();
        }
    }

    public int getVenta_animalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta_animal> rt = cq.from(Venta_animal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
