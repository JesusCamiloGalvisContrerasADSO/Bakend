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
import logica.Animal;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author hp
 */
public class AnimalJpaController implements Serializable {

    public AnimalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Animal animal) {
        if (animal.getPesos() == null) {
            animal.setPesos(new ArrayList<Peso_animal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lotes lote = animal.getLote();
            if (lote != null) {
                lote = em.getReference(lote.getClass(), lote.getId_lote());
                animal.setLote(lote);
            }
            List<Peso_animal> attachedPesos = new ArrayList<Peso_animal>();
            for (Peso_animal pesosPeso_animalToAttach : animal.getPesos()) {
                pesosPeso_animalToAttach = em.getReference(pesosPeso_animalToAttach.getClass(), pesosPeso_animalToAttach.getId_peso());
                attachedPesos.add(pesosPeso_animalToAttach);
            }
            animal.setPesos(attachedPesos);
            em.persist(animal);
            if (lote != null) {
                lote.getAnimales().add(animal);
                lote = em.merge(lote);
            }
            for (Peso_animal pesosPeso_animal : animal.getPesos()) {
                Animal oldAnimalOfPesosPeso_animal = pesosPeso_animal.getAnimal();
                pesosPeso_animal.setAnimal(animal);
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

    public void edit(Animal animal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Animal persistentAnimal = em.find(Animal.class, animal.getId_animal());
            Lotes loteOld = persistentAnimal.getLote();
            Lotes loteNew = animal.getLote();
            List<Peso_animal> pesosOld = persistentAnimal.getPesos();
            List<Peso_animal> pesosNew = animal.getPesos();
            if (loteNew != null) {
                loteNew = em.getReference(loteNew.getClass(), loteNew.getId_lote());
                animal.setLote(loteNew);
            }
            List<Peso_animal> attachedPesosNew = new ArrayList<Peso_animal>();
            for (Peso_animal pesosNewPeso_animalToAttach : pesosNew) {
                pesosNewPeso_animalToAttach = em.getReference(pesosNewPeso_animalToAttach.getClass(), pesosNewPeso_animalToAttach.getId_peso());
                attachedPesosNew.add(pesosNewPeso_animalToAttach);
            }
            pesosNew = attachedPesosNew;
            animal.setPesos(pesosNew);
            animal = em.merge(animal);
            if (loteOld != null && !loteOld.equals(loteNew)) {
                loteOld.getAnimales().remove(animal);
                loteOld = em.merge(loteOld);
            }
            if (loteNew != null && !loteNew.equals(loteOld)) {
                loteNew.getAnimales().add(animal);
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
                    Animal oldAnimalOfPesosNewPeso_animal = pesosNewPeso_animal.getAnimal();
                    pesosNewPeso_animal.setAnimal(animal);
                    pesosNewPeso_animal = em.merge(pesosNewPeso_animal);
                    if (oldAnimalOfPesosNewPeso_animal != null && !oldAnimalOfPesosNewPeso_animal.equals(animal)) {
                        oldAnimalOfPesosNewPeso_animal.getPesos().remove(pesosNewPeso_animal);
                        oldAnimalOfPesosNewPeso_animal = em.merge(oldAnimalOfPesosNewPeso_animal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = animal.getId_animal();
                if (findAnimal(id) == null) {
                    throw new NonexistentEntityException("The animal with id " + id + " no longer exists.");
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
            Animal animal;
            try {
                animal = em.getReference(Animal.class, id);
                animal.getId_animal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The animal with id " + id + " no longer exists.", enfe);
            }
            Lotes lote = animal.getLote();
            if (lote != null) {
                lote.getAnimales().remove(animal);
                lote = em.merge(lote);
            }
            List<Peso_animal> pesos = animal.getPesos();
            for (Peso_animal pesosPeso_animal : pesos) {
                pesosPeso_animal.setAnimal(null);
                pesosPeso_animal = em.merge(pesosPeso_animal);
            }
            em.remove(animal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Animal> findAnimalEntities() {
        return findAnimalEntities(true, -1, -1);
    }

    public List<Animal> findAnimalEntities(int maxResults, int firstResult) {
        return findAnimalEntities(false, maxResults, firstResult);
    }

    private List<Animal> findAnimalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Animal.class));
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

    public Animal findAnimal(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Animal.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnimalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Animal> rt = cq.from(Animal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
