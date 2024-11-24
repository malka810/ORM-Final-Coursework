package lk.ijse.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId; // Unique identifier for each user

    @Column(name = "username", nullable = false, unique = true)
    private String username; // Unique username for login

    @Column(name = "password", nullable = false)
    private String password; // Encrypted password

    @Column(name = "email", nullable = false, unique = true)
    private String email; // Unique email address

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role; // Enum for user role

    @Column(name = "phone_number")
    private String phoneNumber; // Contact number of the user

    @OneToMany(mappedBy = "admissionsCoordinator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registration> registrationsHandled = new ArrayList<>(); // One-to-Many relationship with Registration

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    // Enum for user roles
    public enum Role {
        ADMIN, ADMISSIONS_COORDINATOR
    }


}
