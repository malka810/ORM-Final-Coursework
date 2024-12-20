package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.RegistrationDAO;
import lk.ijse.entity.Program;
import lk.ijse.entity.Registration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RegistrationDAOImpl implements RegistrationDAO {

    @Override
    public boolean save(Registration object) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Object isSaved = session.save(object);

        if(isSaved != null){
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String object) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Registration registration = session.get(Registration.class,object);
        session.delete(registration);
        transaction.commit();

        session.close();
        return true;

    }

    @Override
    public boolean update(Registration object) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(object);
        transaction.commit();
        session.close();
        return true;
    }


    @Override
    public String getCurrentID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("select registrationID from Registration ORDER BY registrationID DESC LIMIT 1");
        String currentId = (String) query.uniqueResult();

        transaction.commit();
        session.close();
        return currentId;
    }

    @Override
    public List<Registration> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Registration ");
        List<Registration> registrations = query.list();
        transaction.commit();
        session.close();
        return registrations;
    }

    @Override
    public Registration search(String registerId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Registration where registrationID =?1");
        query.setParameter(1, registerId);
        Registration registration = (Registration) query.uniqueResult();
        transaction.commit();
        return registration;
    }
}
