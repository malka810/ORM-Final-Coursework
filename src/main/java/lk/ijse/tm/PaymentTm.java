package lk.ijse.tm;

import lk.ijse.entity.Program;
import lk.ijse.entity.Registration;
import lk.ijse.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentTm {
    private String payment_id;
    private String registration_id;
    private String payment_date;
    private double prepayment;
    private double total_amount;
    private double due_amount;
}
