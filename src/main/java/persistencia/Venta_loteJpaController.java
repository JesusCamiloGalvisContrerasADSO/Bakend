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
import logica.Venta_lote;
import logica.Ventas;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author hp
 */
public class Venta_loteJpaController implements Serializable {

    public Venta_loteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta_lote venta_lote) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas venta = venta_lote.getVenta();
            if (venta != null) {
                venta = em.getReference(venta.getClass(), venta.getId_venta());
                venta_lote.setVenta(venta);
            }
            em.persist(venta_lote);
            if (venta != null) {
                venta.getVentaLotes().add(venta_lote);
                venta = em.merge(venta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta_lote venta_lote) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta_lote persistentVenta_lote = em.find(Venta_lote.class, venta_lote.getId_venta_lote());
            Ventas ventaOld = persistentVenta_lote.getVenta();
            Ventas ventaNew = venta_lote.getVenta();
            if (ventaNew != null) {
                ventaNew = em.getReference(ventaNew.getClass(), ventaNew.getId_venta());
                venta_lote.setVenta(ventaNew);
            }
            venta_lote = em.merge(venta_lote);
            if (ventaOld != null && !ventaOld.equals(ventaNew)) {
                ventaOld.getVentaLotes().remove(venta_lote);
                ventaOld = em.merge(ventaOld);
            }
            if (ventaNew != null && !ventaNew.equals(ventaOld)) {
                ventaNew.getVentaLotes().add(venta_lote);
                ventaNew = em.merge(ventaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = venta_lote.getId_venta_lote();
                if (findVenta_lote(id) == null) {
                    throw new NonexistentEntityException("The venta_lote with id " + id + " no longer exists.");
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
            Venta_lote venta_lote;
            try {
                venta_lote = em.getReference(Venta_lote.class, id);
                venta_lote.getId_venta_lote();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta_lote with id " + id + " no longer exists.", enfe);
            }
            Ventas venta = venta_lote.getVenta();
            if (venta != null) {
                venta.getVentaLotes().remove(venta_lote);
                venta = em.merge(venta);
            }
            em.remove(venta_lote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venta_lote> findVenta_loteEntities() {
        return findVenta_loteEntities(true, -1, -1);
    }

    public List<Venta_lote> findVenta_loteEntities(int maxResults, int firstResult) {
        return findVenta_loteEntities(false, maxResults, firstResult);
    }

    private List<Venta_lote> findVenta_loteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta_lote.class));
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

    public Venta_lote findVenta_lote(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta_lote.class, id);
        } finally {
            em.close();
        }
    }

    public int getVenta_loteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta_lote> rt = cq.from(Venta_lote.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
