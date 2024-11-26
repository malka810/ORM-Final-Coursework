package lk.ijse.config;


import lk.ijse.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration(){
        Configuration configuration = new Configuration().configure().addAnnotatedClass(Student.class).addAnnotatedClass(User.class).addAnnotatedClass(Program.class).addAnnotatedClass(Registration.class).addAnnotatedClass(Payment.class);


        sessionFactory  = configuration.buildSessionFactory();
    }
    public static FactoryConfiguration getInstance(){
        return factoryConfiguration == null ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }
    public Session getSession(){
        return sessionFactory.openSession();
    }
}
