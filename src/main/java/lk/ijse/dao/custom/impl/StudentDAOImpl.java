package lk.ijse.dao.custom.impl;


import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public boolean save(Student object) {
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

        Student student = session.get(Student.class, object);

        if (student != null) {
            // Delete the Student entity
            session.delete(student);
            transaction.commit(); // Commit the transaction if successful
            return true;
        }
        session.close();
        return false;
    }

    @Override
    public boolean update(Student object) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(object);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Student search(String studentId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Student where studentId =?1");
        query.setParameter(1, studentId);
        Student student = (Student) query.uniqueResult();
        transaction.commit();
        return student;
    }

    @Override
    public List<Student> getStudent() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT s " +
                "FROM Student s " +
                "JOIN s.registrations r " +
                "JOIN r.program p " +
                "GROUP BY s.studentId, s.name, s.user " +
                "HAVING COUNT(DISTINCT p.programId) = (" +
                "  SELECT COUNT(DISTINCT p1.programId) FROM Program p1" +
                ")");
        List<Student> students = query.list();
        transaction.commit();
        session.close();
        return students;
    }

    @Override
    public List<Student> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Student ");
        List<Student> resultList = query.getResultList();
        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public String getCurrentId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("select studentId from Student order by studentId desc limit 1");
        String id = (String) query.uniqueResult();
        transaction.commit();
        session.close();
        return id;
    }
}
