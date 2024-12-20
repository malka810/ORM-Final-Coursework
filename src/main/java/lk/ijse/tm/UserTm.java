package lk.ijse.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserTm {
    private String userId;
    private String username;
    private String password;
    private String email;
    private String tel;
    private String role;
}
