package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
    @Id

    private String userId;
    private String username;
    private String password;
    private String email;
    private String tel;
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // Cascade all operations
    private List<Student> student;

    public User(String userId, String username, String password, String email, String tel, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.role = role;
    }
}
