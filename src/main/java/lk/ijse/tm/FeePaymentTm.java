package lk.ijse.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeePaymentTm {
    private String paymentId;           // Unique identifier for the payment
    private String registrationId;       // Reference to the related registration
    private String studentId;            // ID of the student making the payment
    private String studentName;          // Name of the student making the payment
    private String programId;            // ID of the program associated with the registration
    private String programName;          // Name of the program
    private BigDecimal fee;              // Total fee for the program
    private BigDecimal amount;           // Amount paid by the student
    private Date paymentDate;            // Date of payment
    private String paymentMethod;        // Payment method (e.g., "Credit Card", "Bank Transfer")
    private BigDecimal balance;          // Remaining balance after payment

}
