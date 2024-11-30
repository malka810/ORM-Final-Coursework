package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.dto.UserDTO;

import java.util.List;

public interface StudentBO extends SuperBO {
    

    String getCurrentId();

    boolean save(StudentDTO studentDTO);

    boolean delete(String studentDTO);

    boolean update(StudentDTO studentDTO);

    List<UserDTO> getAllUsers();

    List<StudentDTO> getAllStudent();

    StudentDTO searchById(String studentId);
}
