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
import logica.Peso_animal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import logica.Historia_clinica;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author hp
 */
public class Historia_clinicaJpaController implements Serializable {

    public Historia_clinicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historia_clinica historia_clinica) {
        if (historia_clinica.getPesos() == null) {
            historia_clinica.setPesos(new ArrayList<Peso_animal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lotes lote = historia_clinica.getLote();
            if (lote != null) {
                lote = em.getReference(lote.getClass(), lote.getId_lote());
                historia_clinica.setLote(lote);
            }
            List<Peso_animal> attachedPesos = new ArrayList<Peso_animal>();
            for (Peso_animal pesosPeso_animalToAttach : historia_clinica.getPesos()) {
                pesosPeso_animalToAttach = em.getReference(pesosPeso_animalToAttach.getClass(), pesosPeso_animalToAttach.getId_peso());
                attachedPesos.add(pesosPeso_animalToAttach);
            }
            historia_clinica.setPesos(attachedPesos);
            em.persist(historia_clinica);
            if (lote != null) {
                lote.getAnimales().add(historia_clinica);
                lote = em.merge(lote);
            }
            for (Peso_animal pesosPeso_animal : historia_clinica.getPesos()) {
                logica.Animal oldAnimalOfPesosPeso_animal = pesosPeso_animal.getAnimal();
                pesosPeso_animal.setAnimal(historia_clinica);
                pesosPeso_animal = em.merge(pesosPeso_animal);
                if (oldAnimalOfPesosPeso_animal != null) {
                    oldAnimalOfPesosPeso_animal.getPesos().remove(pesosPeso_animal);
                    oldAnimalOfPesosPeso_animal = em.merge(oldAnimalOfPesosPeso_animal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historia_clinica historia_clinica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historia_clinica persistentHistoria_clinica = em.find(Historia_clinica.class, historia_clinica.getId_historia());
            Lotes loteOld = persistentHistoria_clinica.getLote();
            Lotes loteNew = historia_clinica.getLote();
            List<Peso_animal> pesosOld = persistentHistoria_clinica.getPesos();
            List<Peso_animal> pesosNew = historia_clinica.getPesos();
            if (loteNew != null) {
                loteNew = em.getReference(loteNew.getClass(), loteNew.getId_lote());
                historia_clinica.setLote(loteNew);
            }
            List<Peso_animal> attachedPesosNew = new ArrayList<Peso_animal>();
            for (Peso_animal pesosNewPeso_animalToAttach : pesosNew) {
                pesosNewPeso_animalToAttach = em.getReference(pesosNewPeso_animalToAttach.getClass(), pesosNewPeso_animalToAttach.getId_peso());
                attachedPesosNew.add(pesosNewPeso_animalToAttach);
            }
            pesosNew = attachedPesosNew;
            historia_clinica.setPesos(pesosNew);
            historia_clinica = em.merge(historia_clinica);
            if (loteOld != null && !loteOld.equals(loteNew)) {
                loteOld.getAnimales().remove(historia_clinica);
                loteOld = em.merge(loteOld);
            }
            if (loteNew != null && !loteNew.equals(loteOld)) {
                loteNew.getAnimales().add(historia_clinica);
                loteNew = em.merge(loteNew);
            }
            for (Peso_animal pesosOldPeso_animal : pesosOld) {
                if (!pesosNew.contains(pesosOldPeso_animal)) {
                    pesosOldPeso_animal.setAnimal(null);
                    pesosOldPeso_animal = em.merge(pesosOldPeso_animal);
                }
            }
            for (Peso_animal pesosNewPeso_animal : pesosNew) {
                if (!pesosOld.contains(pesosNewPeso_animal)) {
                    Historia_clinica oldAnimalOfPesosNewPeso_animal = pesosNewPeso_animal.getAnimal();
                    pesosNewPeso_animal.setAnimal(historia_clinica);
                    pesosNewPeso_animal = em.merge(pesosNewPeso_animal);
                    if (oldAnimalOfPesosNewPeso_animal != null && !oldAnimalOfPesosNewPeso_animal.equals(historia_clinica)) {
                        oldAnimalOfPesosNewPeso_animal.getPesos().remove(pesosNewPeso_animal);
                        oldAnimalOfPesosNewPeso_animal = em.merge(oldAnimalOfPesosNewPeso_animal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = historia_clinica.getId_historia();
                if (findHistoria_clinica(id) == null) {
                    throw new NonexistentEntityException("The historia_clinica with id " + id + " no longer exists.");
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
            Historia_clinica historia_clinica;
            try {
                historia_clinica = em.getReference(Historia_clinica.class, id);
                historia_clinica.getId_historia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historia_clinica with id " + id + " no longer exists.", enfe);
            }
            Lotes lote = historia_clinica.getLote();
            if (lote != null) {
                lote.getAnimales().remove(historia_clinica);
                lote = em.merge(lote);
            }
            List<Peso_animal> pesos = historia_clinica.getPesos();
            for (Peso_animal pesosPeso_animal : pesos) {
                pesosPeso_animal.setAnimal(null);
                pesosPeso_animal = em.merge(pesosPeso_animal);
            }
            em.remove(historia_clinica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historia_clinica> findHistoria_clinicaEntities() {
        return findHistoria_clinicaEntities(true, -1, -1);
    }

    public List<Historia_clinica> findHistoria_clinicaEntities(int maxResults, int firstResult) {
        return findHistoria_clinicaEntities(false, maxResults, firstResult);
    }

    private List<Historia_clinica> findHistoria_clinicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historia_clinica.class));
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

    public Historia_clinica findHistoria_clinica(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historia_clinica.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoria_clinicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historia_clinica> rt = cq.from(Historia_clinica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
