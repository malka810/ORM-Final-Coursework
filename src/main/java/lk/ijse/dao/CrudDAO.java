package lk.ijse.dao;


import lk.ijse.entity.Program;

public interface CrudDAO <T> extends SuperDAO{
    boolean save(T object);

    boolean delete(String object);

    boolean update(T object);

}
