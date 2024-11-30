package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;

import java.util.ArrayList;
import java.util.List;


public class StudentBOImpl implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Student);
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Users);


    @Override
    public String getCurrentId() {
        return studentDAO.getCurrentId();
    }

    @Override
    public boolean save(StudentDTO studentDTO) {
        return studentDAO.save(new Student(studentDTO.getStudentId(),studentDTO.getUser(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getEmail(),studentDTO.getContact()));
    }

    @Override
    public boolean delete(String studentId) {
        return studentDAO.delete(studentId);

    }

    @Override
    public boolean update(StudentDTO studentDTO) {
        return studentDAO.update(new Student(studentDTO.getStudentId(),studentDTO.getUser(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getEmail(),studentDTO.getContact()));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.getAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : users){
            UserDTO userDTO = new UserDTO(user.getUserId(),user.getRole(),user.getUsername(),user.getPassword(),user.getEmail(),user.getTel());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public List<StudentDTO> getAllStudent() {
        List<Student> students = studentDAO.getAll();
        List<StudentDTO> stList = new ArrayList<>();

        for (Student student : students){
            StudentDTO studentDTO = new StudentDTO(student.getStudentId(),student.getUser(),student.getName(),student.getAddress(),student.getEmail(),student.getContact());
            stList.add(studentDTO);
        }
        return stList;
    }

    @Override
    public StudentDTO searchById(String studentId) {
        Student student = studentDAO.search(studentId);
        return new StudentDTO(student.getStudentId(),student.getUser(),student.getName(),student.getAddress(),student.getEmail(),student.getContact());
    }
}
