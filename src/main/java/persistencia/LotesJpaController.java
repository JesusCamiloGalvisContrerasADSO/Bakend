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
import logica.Trabajador;
import logica.Administrador;
import logica.Animal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import logica.Lotes;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author hp
 */
public class LotesJpaController implements Serializable {

    public LotesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lotes lotes) {
        if (lotes.getAnimales() == null) {
            lotes.setAnimales(new ArrayList<Animal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trabajador trabajador = lotes.getTrabajador();
            if (trabajador != null) {
                trabajador = em.getReference(trabajador.getClass(), trabajador.getId());
                lotes.setTrabajador(trabajador);
            }
            Administrador administrador = lotes.getAdministrador();
            if (administrador != null) {
                administrador = em.getReference(administrador.getClass(), administrador.getId());
                lotes.setAdministrador(administrador);
            }
            List<Animal> attachedAnimales = new ArrayList<Animal>();
            for (Animal animalesAnimalToAttach : lotes.getAnimales()) {
                animalesAnimalToAttach = em.getReference(animalesAnimalToAttach.getClass(), animalesAnimalToAttach.getId_animal());
                attachedAnimales.add(animalesAnimalToAttach);
            }
            lotes.setAnimales(attachedAnimales);
            em.persist(lotes);
            if (trabajador != null) {
                trabajador.getListaLotes().add(lotes);
                trabajador = em.merge(trabajador);
            }
            if (administrador != null) {
                administrador.getListaLotes().add(lotes);
                administrador = em.merge(administrador);
            }
            for (Animal animalesAnimal : lotes.getAnimales()) {
                Lotes oldLoteOfAnimalesAnimal = animalesAnimal.getLote();
                animalesAnimal.setLote(lotes);
                animalesAnimal = em.merge(animalesAnimal);
                if (oldLoteOfAnimalesAnimal != null) {
                    oldLoteOfAnimalesAnimal.getAnimales().remove(animalesAnimal);
                    oldLoteOfAnimalesAnimal = em.merge(oldLoteOfAnimalesAnimal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lotes lotes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lotes persistentLotes = em.find(Lotes.class, lotes.getId_lote());
            Trabajador trabajadorOld = persistentLotes.getTrabajador();
            Trabajador trabajadorNew = lotes.getTrabajador();
            Administrador administradorOld = persistentLotes.getAdministrador();
            Administrador administradorNew = lotes.getAdministrador();
            List<Animal> animalesOld = persistentLotes.getAnimales();
            List<Animal> animalesNew = lotes.getAnimales();
            if (trabajadorNew != null) {
                trabajadorNew = em.getReference(trabajadorNew.getClass(), trabajadorNew.getId());
                lotes.setTrabajador(trabajadorNew);
            }
            if (administradorNew != null) {
                administradorNew = em.getReference(administradorNew.getClass(), administradorNew.getId());
                lotes.setAdministrador(administradorNew);
            }
            List<Animal> attachedAnimalesNew = new ArrayList<Animal>();
            for (Animal animalesNewAnimalToAttach : animalesNew) {
                animalesNewAnimalToAttach = em.getReference(animalesNewAnimalToAttach.getClass(), animalesNewAnimalToAttach.getId_animal());
                attachedAnimalesNew.add(animalesNewAnimalToAttach);
            }
            animalesNew = attachedAnimalesNew;
            lotes.setAnimales(animalesNew);
            lotes = em.merge(lotes);
            if (trabajadorOld != null && !trabajadorOld.equals(trabajadorNew)) {
                trabajadorOld.getListaLotes().remove(lotes);
                trabajadorOld = em.merge(trabajadorOld);
            }
            if (trabajadorNew != null && !trabajadorNew.equals(trabajadorOld)) {
                trabajadorNew.getListaLotes().add(lotes);
                trabajadorNew = em.merge(trabajadorNew);
            }
            if (administradorOld != null && !administradorOld.equals(administradorNew)) {
                administradorOld.getListaLotes().remove(lotes);
                administradorOld = em.merge(administradorOld);
            }
            if (administradorNew != null && !administradorNew.equals(administradorOld)) {
                administradorNew.getListaLotes().add(lotes);
                administradorNew = em.merge(administradorNew);
            }
            for (Animal animalesOldAnimal : animalesOld) {
                if (!animalesNew.contains(animalesOldAnimal)) {
                    animalesOldAnimal.setLote(null);
                    animalesOldAnimal = em.merge(animalesOldAnimal);
                }
            }
            for (Animal animalesNewAnimal : animalesNew) {
                if (!animalesOld.contains(animalesNewAnimal)) {
                    Lotes oldLoteOfAnimalesNewAnimal = animalesNewAnimal.getLote();
                    animalesNewAnimal.setLote(lotes);
                    animalesNewAnimal = em.merge(animalesNewAnimal);
                    if (oldLoteOfAnimalesNewAnimal != null && !oldLoteOfAnimalesNewAnimal.equals(lotes)) {
                        oldLoteOfAnimalesNewAnimal.getAnimales().remove(animalesNewAnimal);
                        oldLoteOfAnimalesNewAnimal = em.merge(oldLoteOfAnimalesNewAnimal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = lotes.getId_lote();
                if (findLotes(id) == null) {
                    throw new NonexistentEntityException("The lotes with id " + id + " no longer exists.");
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
            Lotes lotes;
            try {
                lotes = em.getReference(Lotes.class, id);
                lotes.getId_lote();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lotes with id " + id + " no longer exists.", enfe);
            }
            Trabajador trabajador = lotes.getTrabajador();
            if (trabajador != null) {
                trabajador.getListaLotes().remove(lotes);
                trabajador = em.merge(trabajador);
            }
            Administrador administrador = lotes.getAdministrador();
            if (administrador != null) {
                administrador.getListaLotes().remove(lotes);
                administrador = em.merge(administrador);
            }
            List<Animal> animales = lotes.getAnimales();
            for (Animal animalesAnimal : animales) {
                animalesAnimal.setLote(null);
                animalesAnimal = em.merge(animalesAnimal);
            }
            em.remove(lotes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lotes> findLotesEntities() {
        return findLotesEntities(true, -1, -1);
    }

    public List<Lotes> findLotesEntities(int maxResults, int firstResult) {
        return findLotesEntities(false, maxResults, firstResult);
    }

    private List<Lotes> findLotesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lotes.class));
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

    public Lotes findLotes(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lotes.class, id);
        } finally {
            em.close();
        }
    }

    public int getLotesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lotes> rt = cq.from(Lotes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
