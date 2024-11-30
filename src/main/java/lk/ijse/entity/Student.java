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
public class Student {
    @Id
    private String studentId;
    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;
    private String name;
    private String address;
    private String email;
    private String contact;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Registration> registrations;

    public Student(String studentId, User user, String name, String address, String email, String contact) {
        this.studentId = studentId;
        this.user = user;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
    }
}