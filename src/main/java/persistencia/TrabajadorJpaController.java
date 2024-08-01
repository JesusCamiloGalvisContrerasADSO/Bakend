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
import logica.Lotes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import logica.Trabajador;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author hp
 */
public class TrabajadorJpaController implements Serializable {

    public TrabajadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trabajador trabajador) {
        if (trabajador.getListaLotes() == null) {
            trabajador.setListaLotes(new ArrayList<Lotes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Lotes> attachedListaLotes = new ArrayList<Lotes>();
            for (Lotes listaLotesLotesToAttach : trabajador.getListaLotes()) {
                listaLotesLotesToAttach = em.getReference(listaLotesLotesToAttach.getClass(), listaLotesLotesToAttach.getId_lote());
                attachedListaLotes.add(listaLotesLotesToAttach);
            }
            trabajador.setListaLotes(attachedListaLotes);
            em.persist(trabajador);
            for (Lotes listaLotesLotes : trabajador.getListaLotes()) {
                Trabajador oldTrabajadorOfListaLotesLotes = listaLotesLotes.getTrabajador();
                listaLotesLotes.setTrabajador(trabajador);
                listaLotesLotes = em.merge(listaLotesLotes);
                if (oldTrabajadorOfListaLotesLotes != null) {
                    oldTrabajadorOfListaLotesLotes.getListaLotes().remove(listaLotesLotes);
                    oldTrabajadorOfListaLotesLotes = em.merge(oldTrabajadorOfListaLotesLotes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trabajador trabajador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trabajador persistentTrabajador = em.find(Trabajador.class, trabajador.getId());
            List<Lotes> listaLotesOld = persistentTrabajador.getListaLotes();
            List<Lotes> listaLotesNew = trabajador.getListaLotes();
            List<Lotes> attachedListaLotesNew = new ArrayList<Lotes>();
            for (Lotes listaLotesNewLotesToAttach : listaLotesNew) {
                listaLotesNewLotesToAttach = em.getReference(listaLotesNewLotesToAttach.getClass(), listaLotesNewLotesToAttach.getId_lote());
                attachedListaLotesNew.add(listaLotesNewLotesToAttach);
            }
            listaLotesNew = attachedListaLotesNew;
            trabajador.setListaLotes(listaLotesNew);
            trabajador = em.merge(trabajador);
            for (Lotes listaLotesOldLotes : listaLotesOld) {
                if (!listaLotesNew.contains(listaLotesOldLotes)) {
                    listaLotesOldLotes.setTrabajador(null);
                    listaLotesOldLotes = em.merge(listaLotesOldLotes);
                }
            }
            for (Lotes listaLotesNewLotes : listaLotesNew) {
                if (!listaLotesOld.contains(listaLotesNewLotes)) {
                    Trabajador oldTrabajadorOfListaLotesNewLotes = listaLotesNewLotes.getTrabajador();
                    listaLotesNewLotes.setTrabajador(trabajador);
                    listaLotesNewLotes = em.merge(listaLotesNewLotes);
                    if (oldTrabajadorOfListaLotesNewLotes != null && !oldTrabajadorOfListaLotesNewLotes.equals(trabajador)) {
                        oldTrabajadorOfListaLotesNewLotes.getListaLotes().remove(listaLotesNewLotes);
                        oldTrabajadorOfListaLotesNewLotes = em.merge(oldTrabajadorOfListaLotesNewLotes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = trabajador.getId();
                if (findTrabajador(id) == null) {
                    throw new NonexistentEntityException("The trabajador with id " + id + " no longer exists.");
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
            Trabajador trabajador;
            try {
                trabajador = em.getReference(Trabajador.class, id);
                trabajador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trabajador with id " + id + " no longer exists.", enfe);
            }
            List<Lotes> listaLotes = trabajador.getListaLotes();
            for (Lotes listaLotesLotes : listaLotes) {
                listaLotesLotes.setTrabajador(null);
                listaLotesLotes = em.merge(listaLotesLotes);
            }
            em.remove(trabajador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trabajador> findTrabajadorEntities() {
        return findTrabajadorEntities(true, -1, -1);
    }

    public List<Trabajador> findTrabajadorEntities(int maxResults, int firstResult) {
        return findTrabajadorEntities(false, maxResults, firstResult);
    }

    private List<Trabajador> findTrabajadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trabajador.class));
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

    public Trabajador findTrabajador(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trabajador.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrabajadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trabajador> rt = cq.from(Trabajador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
