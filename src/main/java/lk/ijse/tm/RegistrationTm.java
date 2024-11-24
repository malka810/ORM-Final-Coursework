package lk.ijse.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationTm {
    private String registrationId;
    private String studentId;
    private String programId;
    private String paymentId;
    private Date registrationDate;
    private String status;
    private String admissionsCoordinatorId;
    private Date createdAt;
    private Date updatedAt;
}
