package lk.ijse.dto;

import lk.ijse.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO{
    private String user_Id;
    private String username;
    private String password;
    private String email;
    private String tel;
    private String role;
}
