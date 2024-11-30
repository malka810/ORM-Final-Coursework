package lk.ijse.dto;
import lk.ijse.entity.Program;
import lk.ijse.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationDTO {
    private String registrationID;
    private String date;
    private Student student;
    private Program program;
    private String studentName;
    private String programName;
    private double fee;
    private double prepayment;
}
