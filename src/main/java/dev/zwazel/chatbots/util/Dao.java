package dev.zwazel.chatbots.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.Serializable;

public class Dao<T, I extends Serializable> {
    private final Class<T> tClass;

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("chatbots");

    public Dao(Class<T> tClass) {
        this.tClass = tClass;
    }

    public void save(T t) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public T find(I i) {
        T t = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            t = entityManager.find(tClass, i);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}