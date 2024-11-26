package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;
import lk.ijse.util.PasswordUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {


    @Override
    public boolean save(Object object) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Object user = session.save(object);
        System.out.println(user);

        if (user != null) {
            transaction.commit();
            session.close();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getCurrentId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        String currentId = null;

        try {
            transaction = session.beginTransaction();
            Query<String> query = session.createQuery("SELECT userId FROM User ORDER BY userId DESC LIMIT 1", String.class);
            currentId = query.setMaxResults(1).uniqueResult(); // Get the latest userId
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rollback on error
            e.printStackTrace(); // Print exception for debugging
        } finally {
            session.close(); // Ensure session is closed
        }

        return currentId;
    }

    @Override
    public List<User> getAll() {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery query = session.createNativeQuery("SELECT * FROM user");
        query.addEntity(User.class);
        List<User> resultList = query.getResultList();
        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public String getUserRole(String username) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        String userRole = null;

        try {
            transaction = session.beginTransaction();

            // HQL query to retrieve userRole where userName matches
            Query<String> query = session.createQuery("SELECT u.role FROM User u WHERE u.username = :userName", String.class);
            query.setParameter("userName", username);

            // Get the userRole
            userRole = query.setMaxResults(1).uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rollback on error
            e.printStackTrace(); // Print exception for debugging
        } finally {
            session.close(); // Ensure session is closed
        }

        return userRole;
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        boolean isValid = false;

        try {
            transaction = session.beginTransaction();

            // Fetch the user by username
            Query<User> query = session.createQuery("FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);

            User user = query.uniqueResult();

            if (user != null) {
                // Verify the password using BCrypt
                isValid = PasswordUtil.verifyPassword(password, user.getPassword());
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rollback transaction on error
            e.printStackTrace(); // Log error for debugging
        } finally {
            session.close(); // Ensure session is closed
        }

        return isValid;
    }
}
