package lk.ijse.tm;
import lk.ijse.entity.Registration;
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
public class ProgramTm {
    private String programId;
    private String programName;
    private String duration;
    private BigDecimal fee;
    private List<Registration> registrations = new ArrayList<>();
    private Date createdAt;
    private Date updatedAt;
}
