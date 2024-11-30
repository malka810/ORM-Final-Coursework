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
    public boolean delete(String object) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Retrieve the user object first
            User user = session.get(User.class, object);
            if (user != null) {
                session.delete(user); // Delete the user object
                transaction.commit();
                return true; // Return true if deletion is successful
            } else {
                System.out.println("User not found with ID: " + object);
                return false; // Return false if no user was found
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of an error
            }
            e.printStackTrace(); // Log the exception for debugging
            return false; // Return false if the deletion fails
        } finally {
            session.close(); // Ensure the session is closed in all cases
        }
    }

    @Override
    public User searchUserId(String userId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from User where userId =?1");
        query.setParameter(1, userId);
        User user = (User) query.uniqueResult();
        transaction.commit();
        return user;
    }

    @Override
    public boolean update(Object object) {
        return false;
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

        NativeQuery query = session.createNativeQuery("SELECT * FROM User");
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

    @Override
    public boolean update(User user) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            return true; // Return true if the update is successful
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback the transaction in case of an error
            }
            e.printStackTrace(); // Log the exception for debugging
            return false; // Return false if the update fails
        } finally {
            session.close(); // Ensure the session is closed in all cases
        }
    }

//    @Override
//    public boolean delete(String userId) {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = null;
//
//        try {
//            transaction = session.beginTransaction();
//
//            // Retrieve the user object first
//            User user = session.get(User.class, userId);
//            if (user != null) {
//                session.delete(user); // Delete the user object
//                transaction.commit();
//                return true; // Return true if deletion is successful
//            } else {
//                System.out.println("User not found with ID: " + userId);
//                return false; // Return false if no user was found
//            }
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback(); // Rollback in case of an error
//            }
//            e.printStackTrace(); // Log the exception for debugging
//            return false; // Return false if the deletion fails
//        } finally {
//            session.close(); // Ensure the session is closed in all cases
//        }
//    }
    }

