package lk.ijse.dao;

import lk.ijse.dao.custom.impl.ProgramDAOImpl;
import lk.ijse.dao.custom.impl.RegistrationDAOImpl;
import lk.ijse.dao.custom.impl.StudentDAOImpl;
import lk.ijse.dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null)? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        Student, Programs,Registration, Users,Payment,Query
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case Student:

            case Programs:


            case Payment:


            case Registration:


            case Users:
                return new UserDAOImpl();

            case Query:




            default:
                return null;
        }
    }
}
