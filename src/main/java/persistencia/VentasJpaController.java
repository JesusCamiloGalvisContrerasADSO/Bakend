/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.Venta_animal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import logica.Venta_lote;
import logica.Ventas;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author hp
 */
public class VentasJpaController implements Serializable {

    public VentasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ventas ventas) {
        if (ventas.getVentaAnimales() == null) {
            ventas.setVentaAnimales(new ArrayList<Venta_animal>());
        }
        if (ventas.getVentaLotes() == null) {
            ventas.setVentaLotes(new ArrayList<Venta_lote>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Venta_animal> attachedVentaAnimales = new ArrayList<Venta_animal>();
            for (Venta_animal ventaAnimalesVenta_animalToAttach : ventas.getVentaAnimales()) {
                ventaAnimalesVenta_animalToAttach = em.getReference(ventaAnimalesVenta_animalToAttach.getClass(), ventaAnimalesVenta_animalToAttach.getId_venta_animal());
                attachedVentaAnimales.add(ventaAnimalesVenta_animalToAttach);
            }
            ventas.setVentaAnimales(attachedVentaAnimales);
            List<Venta_lote> attachedVentaLotes = new ArrayList<Venta_lote>();
            for (Venta_lote ventaLotesVenta_loteToAttach : ventas.getVentaLotes()) {
                ventaLotesVenta_loteToAttach = em.getReference(ventaLotesVenta_loteToAttach.getClass(), ventaLotesVenta_loteToAttach.getId_venta_lote());
                attachedVentaLotes.add(ventaLotesVenta_loteToAttach);
            }
            ventas.setVentaLotes(attachedVentaLotes);
            em.persist(ventas);
            for (Venta_animal ventaAnimalesVenta_animal : ventas.getVentaAnimales()) {
                Ventas oldVentaOfVentaAnimalesVenta_animal = ventaAnimalesVenta_animal.getVenta();
                ventaAnimalesVenta_animal.setVenta(ventas);
                ventaAnimalesVenta_animal = em.merge(ventaAnimalesVenta_animal);
                if (oldVentaOfVentaAnimalesVenta_animal != null) {
                    oldVentaOfVentaAnimalesVenta_animal.getVentaAnimales().remove(ventaAnimalesVenta_animal);
                    oldVentaOfVentaAnimalesVenta_animal = em.merge(oldVentaOfVentaAnimalesVenta_animal);
                }
            }
            for (Venta_lote ventaLotesVenta_lote : ventas.getVentaLotes()) {
                Ventas oldVentaOfVentaLotesVenta_lote = ventaLotesVenta_lote.getVenta();
                ventaLotesVenta_lote.setVenta(ventas);
                ventaLotesVenta_lote = em.merge(ventaLotesVenta_lote);
                if (oldVentaOfVentaLotesVenta_lote != null) {
                    oldVentaOfVentaLotesVenta_lote.getVentaLotes().remove(ventaLotesVenta_lote);
                    oldVentaOfVentaLotesVenta_lote = em.merge(oldVentaOfVentaLotesVenta_lote);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ventas ventas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas persistentVentas = em.find(Ventas.class, ventas.getId_venta());
            List<Venta_animal> ventaAnimalesOld = persistentVentas.getVentaAnimales();
            List<Venta_animal> ventaAnimalesNew = ventas.getVentaAnimales();
            List<Venta_lote> ventaLotesOld = persistentVentas.getVentaLotes();
            List<Venta_lote> ventaLotesNew = ventas.getVentaLotes();
            List<Venta_animal> attachedVentaAnimalesNew = new ArrayList<Venta_animal>();
            for (Venta_animal ventaAnimalesNewVenta_animalToAttach : ventaAnimalesNew) {
                ventaAnimalesNewVenta_animalToAttach = em.getReference(ventaAnimalesNewVenta_animalToAttach.getClass(), ventaAnimalesNewVenta_animalToAttach.getId_venta_animal());
                attachedVentaAnimalesNew.add(ventaAnimalesNewVenta_animalToAttach);
            }
            ventaAnimalesNew = attachedVentaAnimalesNew;
            ventas.setVentaAnimales(ventaAnimalesNew);
            List<Venta_lote> attachedVentaLotesNew = new ArrayList<Venta_lote>();
            for (Venta_lote ventaLotesNewVenta_loteToAttach : ventaLotesNew) {
                ventaLotesNewVenta_loteToAttach = em.getReference(ventaLotesNewVenta_loteToAttach.getClass(), ventaLotesNewVenta_loteToAttach.getId_venta_lote());
                attachedVentaLotesNew.add(ventaLotesNewVenta_loteToAttach);
            }
            ventaLotesNew = attachedVentaLotesNew;
            ventas.setVentaLotes(ventaLotesNew);
            ventas = em.merge(ventas);
            for (Venta_animal ventaAnimalesOldVenta_animal : ventaAnimalesOld) {
                if (!ventaAnimalesNew.contains(ventaAnimalesOldVenta_animal)) {
                    ventaAnimalesOldVenta_animal.setVenta(null);
                    ventaAnimalesOldVenta_animal = em.merge(ventaAnimalesOldVenta_animal);
                }
            }
            for (Venta_animal ventaAnimalesNewVenta_animal : ventaAnimalesNew) {
                if (!ventaAnimalesOld.contains(ventaAnimalesNewVenta_animal)) {
                    Ventas oldVentaOfVentaAnimalesNewVenta_animal = ventaAnimalesNewVenta_animal.getVenta();
                    ventaAnimalesNewVenta_animal.setVenta(ventas);
                    ventaAnimalesNewVenta_animal = em.merge(ventaAnimalesNewVenta_animal);
                    if (oldVentaOfVentaAnimalesNewVenta_animal != null && !oldVentaOfVentaAnimalesNewVenta_animal.equals(ventas)) {
                        oldVentaOfVentaAnimalesNewVenta_animal.getVentaAnimales().remove(ventaAnimalesNewVenta_animal);
                        oldVentaOfVentaAnimalesNewVenta_animal = em.merge(oldVentaOfVentaAnimalesNewVenta_animal);
                    }
                }
            }
            for (Venta_lote ventaLotesOldVenta_lote : ventaLotesOld) {
                if (!ventaLotesNew.contains(ventaLotesOldVenta_lote)) {
                    ventaLotesOldVenta_lote.setVenta(null);
                    ventaLotesOldVenta_lote = em.merge(ventaLotesOldVenta_lote);
                }
            }
            for (Venta_lote ventaLotesNewVenta_lote : ventaLotesNew) {
                if (!ventaLotesOld.contains(ventaLotesNewVenta_lote)) {
                    Ventas oldVentaOfVentaLotesNewVenta_lote = ventaLotesNewVenta_lote.getVenta();
                    ventaLotesNewVenta_lote.setVenta(ventas);
                    ventaLotesNewVenta_lote = em.merge(ventaLotesNewVenta_lote);
                    if (oldVentaOfVentaLotesNewVenta_lote != null && !oldVentaOfVentaLotesNewVenta_lote.equals(ventas)) {
                        oldVentaOfVentaLotesNewVenta_lote.getVentaLotes().remove(ventaLotesNewVenta_lote);
                        oldVentaOfVentaLotesNewVenta_lote = em.merge(oldVentaOfVentaLotesNewVenta_lote);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = ventas.getId_venta();
                if (findVentas(id) == null) {
                    throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.");
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
            Ventas ventas;
            try {
                ventas = em.getReference(Ventas.class, id);
                ventas.getId_venta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.", enfe);
            }
            List<Venta_animal> ventaAnimales = ventas.getVentaAnimales();
            for (Venta_animal ventaAnimalesVenta_animal : ventaAnimales) {
                ventaAnimalesVenta_animal.setVenta(null);
                ventaAnimalesVenta_animal = em.merge(ventaAnimalesVenta_animal);
            }
            List<Venta_lote> ventaLotes = ventas.getVentaLotes();
            for (Venta_lote ventaLotesVenta_lote : ventaLotes) {
                ventaLotesVenta_lote.setVenta(null);
                ventaLotesVenta_lote = em.merge(ventaLotesVenta_lote);
            }
            em.remove(ventas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ventas> findVentasEntities() {
        return findVentasEntities(true, -1, -1);
    }

    public List<Ventas> findVentasEntities(int maxResults, int firstResult) {
        return findVentasEntities(false, maxResults, firstResult);
    }

    private List<Ventas> findVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ventas.class));
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

    public Ventas findVentas(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ventas.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ventas> rt = cq.from(Ventas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
