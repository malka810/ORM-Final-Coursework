package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.PaymentDTO;

import java.util.List;

public interface PaymentBO extends SuperBO {


    String getCurrentId();

    List<PaymentDTO> getAllPayment();

    boolean savePayment(PaymentDTO paymentDTO);

    boolean update(PaymentDTO paymentDTO);

    boolean delete(String paymentId);
}
