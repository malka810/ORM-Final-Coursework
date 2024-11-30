package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ProgramDTO;

import java.util.List;

public interface ProgramBO extends SuperBO {
    List<ProgramDTO> getAll();

    boolean delete(ProgramDTO programDTO);

    boolean save(ProgramDTO programDTO);

    boolean update(ProgramDTO programDTO);

    String getCurrentId();

    ProgramDTO searchById(String programId);
}
