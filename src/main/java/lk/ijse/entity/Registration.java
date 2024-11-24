package lk.ijse.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID registrationId; // Use UUID for unique identifier

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student; // Many-to-One relationship with Student

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private Program program; // Many-to-One relationship with Program

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status; // Enum for registration status

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", unique = true)
    private Payment payment; // One-to-One relationship with Payment

    @ManyToOne
    @JoinColumn(name = "admissions_coordinator_id", nullable = false)
    private User admissionsCoordinator; // Many-to-One relationship with User

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    // Enum for registration status
    public enum Status {
        PENDING, COMPLETED, CANCELLED
    }

}
