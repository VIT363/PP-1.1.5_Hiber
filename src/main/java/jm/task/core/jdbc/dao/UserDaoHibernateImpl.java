package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery("CREATE TABLE IF NOT EXISTS User (" +
                        "id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                        "name VARCHAR (50) NOT NULL," +
                        "lastName VARCHAR (50) NOT NULL," +
                        "age TINYINT NOT NULL)").executeUpdate();
                transaction.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery("DROP TABLE IF EXISTS User")
                        .executeUpdate();
                transaction.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            try {
                session.save(user);
                transaction.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.delete(session.get(User.class, id));
                transaction.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list;
        try (Session session = Util.getSessionFactory().openSession()) {
            list = new ArrayList<>();
            Transaction transaction = session.beginTransaction();
            try {
                list = session.createQuery("FROM User ").list();
                transaction.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery("TRUNCATE TABLE User").executeUpdate();
                transaction.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }

    }
}