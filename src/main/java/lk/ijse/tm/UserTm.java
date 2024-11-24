package lk.ijse.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserTm {
    private String userId;
    private String username;
    private String email;
    private String role;
    private String phoneNumber;
    private Date createdAt;
    private Date updatedAt;
}
