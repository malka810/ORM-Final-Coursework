package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "programs")
public class Program {

    @Id
    @Column(name = "program_id", nullable = false, unique = true)
    private String programId; // Unique identifier for each program

    @Column(name = "program_name", nullable = false)
    private String programName;

    @Column(name = "duration", nullable = false)
    private String duration; // Duration of the program

    @Column(name = "fee", nullable = false)
    private BigDecimal fee; // Fee for the program

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registration> registrations = new ArrayList<>(); // One-to-Many relationship with Registration

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

}

