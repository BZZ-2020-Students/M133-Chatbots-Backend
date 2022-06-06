package dev.zwazel.chatbots.classes.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
     * @author Zwazel
     * @since 0.3
     */
    public Dao(Class<T> tClass) {
        this.tClass = tClass;
    }

    /**
     * Persist an entity.
     *
     * @param t The entity to persist.
     * @author Zwazel
     * @since 0.3
     */
    public void save(T t) throws IllegalArgumentException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * saves a collection of entities.
     *
     * @param t The collection of entities to save.
     * @author Zwazel
     * @since 0.3
     */
    public void saveCollection(Iterable<T> t) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        for (T t1 : t) {
            entityManager.persist(t1);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Finds an entity by its primary key.
     *
     * @param i The primary key.
     * @return The entity if found, null otherwise.
     * @author Zwazel
     * @since 0.3
     */
    public T findById(I i) {
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
     * @param field         The field to search by.
     * @param value         The value to search for.
     * @param caseSensitive Whether the search is case-sensitive or not.
     * @return The entity if found, null otherwise.
     * @author Zwazel
     * @since 0.3
     */
    public T findBy(String field, Object value, boolean caseSensitive) {
        T t = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            if (caseSensitive) {
                t = entityManager.createQuery("SELECT t FROM " + tClass.getName() + " t WHERE t." + field + " = '" + value + "'", tClass).getSingleResult();
            } else {
                t = entityManager.createQuery("SELECT t FROM " + tClass.getName() + " t WHERE LOWER(t." + field + ") = LOWER('" + value + "')", tClass).getSingleResult();
            }
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
     * @author Zwazel
     * @since 0.3
     */
    public T findBy(String field, Object value) {
        return findBy(field, value, true);
    }

    /**
     * finds an entity by multiple fields and values.
     *
     * <br>
     * Important: The field must be called like the Java field name. Not the database name.
     *
     * @param fields The fields to search by with their values.
     * @return The entity if found, null otherwise.
     * @author Zwazel
     * @since 0.3
     */
    public T findBy(Map<String, Object> fields) {
        T t = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            StringBuilder query = new StringBuilder("SELECT t FROM " + tClass.getName() + " t WHERE ");
            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                query.append("t.").append(entry.getKey()).append(" = '").append(entry.getValue()).append("' AND ");
            }
            query = new StringBuilder(query.substring(0, query.length() - 5));

            t = entityManager.createQuery(query.toString(), tClass).getSingleResult();

            entityManager.getTransaction().commit();
        } catch (Exception ignored) {

        }
        return t;
    }

    /**
     * finds all entities that match a certain field.
     *
     * <br>
     * Important: The field must be called like the Java field name. Not the database name.
     *
     * @param field The field to search by.
     * @param value The value to search for.
     * @return The entity if found, null otherwise.
     * @author Zwazel
     * @since 0.3
     */
    public List<T> findByList(String field, Object value) {
        List<T> t = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            t = entityManager.createQuery("SELECT t FROM " + tClass.getName() + " t WHERE t." + field + " = '" + value + "'", tClass).getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception ignored) {

        }
        return t;
    }

    /**
     * finds all entities.
     *
     * @return all entities in the database.
     * @author Zwazel
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

    /**
     * Removes an entity from the database.
     *
     * @param t The entity to remove.
     * @author Zwazel
     * @since 1.1.0
     */
    public void delete(T t) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        if (!entityManager.contains(t)) {
            t = entityManager.merge(t);
        }

        entityManager.remove(t);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Removes an entity from the database.
     *
     * @param i The id of the entity to remove.
     * @author Zwazel
     * @since 1.1.0
     */
    public void delete(I i) throws IllegalArgumentException {
        T t = findById(i);

        if (t != null) {
            delete(t);
        } else {
            throw new IllegalArgumentException("Entity with id " + i + " does not exist.");
        }
    }
}