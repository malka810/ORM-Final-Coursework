package lk.ijse.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lk.ijse.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {
    private String studentId;
    private User user;
    private String name;
    private String address;
    private String email;
    private String contact;

}
