package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Program;

import java.util.List;

public interface ProgramDAO extends CrudDAO<Program> {
    List<Program> getAll();

    String getCurrentId();

    Program search(String programId);
}
