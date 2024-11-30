package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.dto.StudentDTO;

import java.util.List;

public interface RegistrationBO extends SuperBO {
    boolean save(RegistrationDTO registrationDTO);

    List<RegistrationDTO> getAllRegistrations();

    String getCurrentReId();

    boolean delete(String registerId);

    boolean update(RegistrationDTO registrationDTO);

    ProgramDTO searchProgram(String programId);

    StudentDTO searchStudent(String studentId);

    RegistrationDTO searchRegistration(String registerId);
}
