package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID studentId;  // Use UUID for unique identifier
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String email;
    private String phoneNumber;
    private String address;
    @Enumerated(EnumType.STRING)
    private Gender gender;  // Enum for gender
    @Column(name = "registration_date")
    private Date registrationDate;
    @Enumerated(EnumType.STRING)
    private Status status;  // Enum for student status
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
    @ManyToOne
    private User user; // Reference to User entity

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registration> registrations = new ArrayList<>();  // One-to-Many relationship


    // Enum for gender
    public enum Gender {
        MALE, FEMALE, OTHER
    }

    // Enum for student status
    public enum Status {
        ACTIVE, GRADUATED, INACTIVE
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
