package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Payment;

import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {
    String getCurrentID();

    List<Payment> getAll();
}
