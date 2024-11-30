package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Payment);

    @Override
    public String getCurrentId() {
        return paymentDAO.getCurrentID();
    }

    @Override
    public List<PaymentDTO> getAllPayment() {
        List<Payment> payments = paymentDAO.getAll();
        List<PaymentDTO> paymentList = new ArrayList<>();

        for (Payment payment : payments){
            PaymentDTO paymentDTO = new PaymentDTO(payment.getPayment_id(),payment.getRegistration(),payment.getPayment_date(),payment.getPrepayment(),payment.getTotal_amount(),payment.getDue_amount());
            paymentList.add(paymentDTO);
        }
        return paymentList;
    }

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) {
        return paymentDAO.save(new Payment(paymentDTO.getPayment_id(),paymentDTO.getRegistration(),paymentDTO.getPayment_date(),paymentDTO.getPrepayment(),paymentDTO.getTotal_amount(),paymentDTO.getDue_amount()));
    }

    @Override
    public boolean update(PaymentDTO paymentDTO) {
        return paymentDAO.update(new Payment(paymentDTO.getPayment_id(),paymentDTO.getRegistration(),paymentDTO.getPayment_date(),paymentDTO.getPrepayment(),paymentDTO.getTotal_amount(),paymentDTO.getDue_amount()));
    }

    @Override
    public boolean delete(String paymentId) {
        return paymentDAO.delete(paymentId);
    }
}
