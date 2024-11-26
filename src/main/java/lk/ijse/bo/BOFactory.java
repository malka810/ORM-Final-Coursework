package lk.ijse.bo;

import lk.ijse.bo.custom.impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {}

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }
    public enum BOTypes{
        Student, Programs,Registration, Users, Query, Payment
    }
    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case Student:


            case Programs:


            case Registration:


            case Users:
                return new UserBOImpl();

            case Payment:


            default:
                return null;
        }
    }
}
