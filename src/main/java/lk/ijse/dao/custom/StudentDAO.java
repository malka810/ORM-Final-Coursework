package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;

import java.util.List;

public interface StudentDAO extends CrudDAO<Student> {


    List<Student> getAll();

    String getCurrentId();

    Student search(String studentId);

    List<Student> getStudent();
}
