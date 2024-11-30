package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ProgramDAO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.entity.Program;

import java.util.ArrayList;
import java.util.List;

public class ProgramBOImpl implements ProgramBO {

    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Programs);
    @Override
    public List<ProgramDTO> getAll() {
        List<Program> all = programDAO.getAll();
        List<ProgramDTO> programDtos = new ArrayList<>();
        for (Program program : all){
            programDtos.add(new ProgramDTO(program.getProgramId(),program.getProgramName(),program.getDuration(),program.getFee()));
        }
        return programDtos;
    }

    @Override
    public boolean delete(ProgramDTO programDTO) {
        return programDAO.delete(String.valueOf(new Program(programDTO.getProgramId(),programDTO.getProgramName(),programDTO.getDuration(),programDTO.getFee())));
    }

    @Override
    public boolean save(ProgramDTO programDTO) {
        return programDAO.save(new Program(programDTO.getProgramId(),programDTO.getProgramName(),programDTO.getDuration(),programDTO.getFee()));
    }

    @Override
    public boolean update(ProgramDTO programDTO) {
        return programDAO.update(new Program(programDTO.getProgramId(),programDTO.getProgramName(),programDTO.getDuration(),programDTO.getFee()));
    }

    @Override
    public String getCurrentId() {
        return programDAO.getCurrentId();
    }

    @Override
    public ProgramDTO searchById(String programId) {
        Program program = programDAO.search(programId);
        return new ProgramDTO(program.getProgramId(),program.getProgramName(),program.getDuration(),program.getFee());
    }
}
