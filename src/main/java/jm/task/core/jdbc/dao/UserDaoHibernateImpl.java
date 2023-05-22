package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String query = """
                CREATE TABLE IF NOT EXISTS users (
                id          SERIAL PRIMARY KEY,
                name        VARCHAR(255),
                lastName    VARCHAR(255),
                age         SMALLINT
                )""";
        try (Session session = Util.sessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            transaction.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String query = "DROP TABLE IF EXISTS users";
        try (Session session = Util.sessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            transaction.commit();
        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.sessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.sessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> list = new ArrayList<>();
        try (Session session = Util.sessionFactory().openSession()) {
            transaction = session.beginTransaction();
            list = session.createQuery("FROM User", User.class).list();//list() используется при SELECT запросе.возвращает результат запроса в виде List, либо Object [].
            transaction.commit();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.sessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            transaction.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}

