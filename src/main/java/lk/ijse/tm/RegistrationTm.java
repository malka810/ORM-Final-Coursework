package lk.ijse.tm;


import lk.ijse.entity.Program;
import lk.ijse.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistrationTm {
    private String registrationID;
    private String date;
    private String studentId;
    private String programId;
    private String studentName;
    private String programName;
    private double fee;
    private double prepayment;

}
