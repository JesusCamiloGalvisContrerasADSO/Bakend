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
import logica.Administrador;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author hp
 */
public class AdministradorJpaController implements Serializable {

    public AdministradorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Administrador administrador) {
        if (administrador.getListaLotes() == null) {
            administrador.setListaLotes(new ArrayList<Lotes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Lotes> attachedListaLotes = new ArrayList<Lotes>();
            for (Lotes listaLotesLotesToAttach : administrador.getListaLotes()) {
                listaLotesLotesToAttach = em.getReference(listaLotesLotesToAttach.getClass(), listaLotesLotesToAttach.getId_lote());
                attachedListaLotes.add(listaLotesLotesToAttach);
            }
            administrador.setListaLotes(attachedListaLotes);
            em.persist(administrador);
            for (Lotes listaLotesLotes : administrador.getListaLotes()) {
                Administrador oldAdministradorOfListaLotesLotes = listaLotesLotes.getAdministrador();
                listaLotesLotes.setAdministrador(administrador);
                listaLotesLotes = em.merge(listaLotesLotes);
                if (oldAdministradorOfListaLotesLotes != null) {
                    oldAdministradorOfListaLotesLotes.getListaLotes().remove(listaLotesLotes);
                    oldAdministradorOfListaLotesLotes = em.merge(oldAdministradorOfListaLotesLotes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Administrador administrador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador persistentAdministrador = em.find(Administrador.class, administrador.getId());
            List<Lotes> listaLotesOld = persistentAdministrador.getListaLotes();
            List<Lotes> listaLotesNew = administrador.getListaLotes();
            List<Lotes> attachedListaLotesNew = new ArrayList<Lotes>();
            for (Lotes listaLotesNewLotesToAttach : listaLotesNew) {
                listaLotesNewLotesToAttach = em.getReference(listaLotesNewLotesToAttach.getClass(), listaLotesNewLotesToAttach.getId_lote());
                attachedListaLotesNew.add(listaLotesNewLotesToAttach);
            }
            listaLotesNew = attachedListaLotesNew;
            administrador.setListaLotes(listaLotesNew);
            administrador = em.merge(administrador);
            for (Lotes listaLotesOldLotes : listaLotesOld) {
                if (!listaLotesNew.contains(listaLotesOldLotes)) {
                    listaLotesOldLotes.setAdministrador(null);
                    listaLotesOldLotes = em.merge(listaLotesOldLotes);
                }
            }
            for (Lotes listaLotesNewLotes : listaLotesNew) {
                if (!listaLotesOld.contains(listaLotesNewLotes)) {
                    Administrador oldAdministradorOfListaLotesNewLotes = listaLotesNewLotes.getAdministrador();
                    listaLotesNewLotes.setAdministrador(administrador);
                    listaLotesNewLotes = em.merge(listaLotesNewLotes);
                    if (oldAdministradorOfListaLotesNewLotes != null && !oldAdministradorOfListaLotesNewLotes.equals(administrador)) {
                        oldAdministradorOfListaLotesNewLotes.getListaLotes().remove(listaLotesNewLotes);
                        oldAdministradorOfListaLotesNewLotes = em.merge(oldAdministradorOfListaLotesNewLotes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = administrador.getId();
                if (findAdministrador(id) == null) {
                    throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.");
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
            Administrador administrador;
            try {
                administrador = em.getReference(Administrador.class, id);
                administrador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.", enfe);
            }
            List<Lotes> listaLotes = administrador.getListaLotes();
            for (Lotes listaLotesLotes : listaLotes) {
                listaLotesLotes.setAdministrador(null);
                listaLotesLotes = em.merge(listaLotesLotes);
            }
            em.remove(administrador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Administrador> findAdministradorEntities() {
        return findAdministradorEntities(true, -1, -1);
    }

    public List<Administrador> findAdministradorEntities(int maxResults, int firstResult) {
        return findAdministradorEntities(false, maxResults, firstResult);
    }

    private List<Administrador> findAdministradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Administrador.class));
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

    public Administrador findAdministrador(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Administrador.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdministradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Administrador> rt = cq.from(Administrador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
