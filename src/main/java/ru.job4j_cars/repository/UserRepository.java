package ru.job4j_cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;


import java.util.List;
import java.util.Optional;


public class UserRepository implements AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public UserRepository(SessionFactory sf) {
        this.sf = sf;
    }

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user){
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }


        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {

        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                    "UPDATE auto_user SET login = :flogin WHERE id = :fId")
                    .setParameter("flogin", "new login")
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();

    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {

        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                    "DELETE auto_user WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();

    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<User> result = session.createQuery("from auto_user").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById( int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from auto_user as i where i.id = :fId", User.class);
        query.setParameter("fId", id);
        Optional result = query.list().stream().findFirst();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from auto_user as i where i.login = :login", User.class );
        query.setParameter("login", key);
        List<User> result = query.list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional findByLogin(String login) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from auto_user i where i.login = :login");
        query.setParameter("login", login);
        Optional result = query.list().stream().findFirst();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}