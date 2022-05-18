package dev.zwazel.chatbots.classes.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;

import java.io.Serializable;

/**
 * Utility class for DAO layer.
 * Used to get the EntityManagerFactory and EntityManager.
 * CRUD operations are implemented in this class.
 *
 * @param <T> The type of the entity.
 * @param <I> The type of the primary key.
 * @author Zwazel
 * @since 0.3
 */
@Getter
public class Dao<T, I extends Serializable> {
    /**
     * The class of the entity.
     *
     * @since 0.3
     */
    private final Class<T> tClass;

    /**
     * The EntityManagerFactory.
     *
     * @since 0.3
     */
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("chatbots");

    /**
     * Default constructor.
     *
     * @param tClass The class of the entity.
     * @since 0.3
     */
    public Dao(Class<T> tClass) {
        this.tClass = tClass;
    }

    /**
     * Persist an entity.
     *
     * @param t The entity to persist.
     * @since 0.3
     */
    public void save(T t) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Finds an entity by its primary key.
     *
     * @param i The primary key.
     * @return The entity if found, null otherwise.
     * @since 0.3
     */
    public T find(I i) {
        T t = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            t = entityManager.find(tClass, i);
            entityManager.getTransaction().commit();
        } catch (Exception ignored) {

        }
        return t;
    }

    /**
     * finds an entity by a certain field.
     *
     * <br>
     * Important: The field must be called like the Java field name. Not the database name.
     *
     * @param field The field to search by.
     * @param value The value to search for.
     * @return The entity if found, null otherwise.
     * @since 0.3
     */
    public T findBy(String field, String value) {
        T t = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            t = entityManager.createQuery("SELECT t FROM " + tClass.getName() + " t WHERE t." + field + " = '" + value + "'", tClass).getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception ignored) {

        }
        return t;
    }

    /**
     * finds all entities.
     *
     * @return all entities in the database.
     * @since 0.3
     */
    public Iterable<T> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Iterable<T> t = entityManager.createQuery("SELECT t FROM " + tClass.getName() + " t", tClass).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return t;
    }
}