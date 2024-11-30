package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.entity.Registration;
import lk.ijse.tm.PaymentTm;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class PaymentFormController {

    @FXML
    private ComboBox<String> cmbReId;

    @FXML
    private TableColumn<?, ?> colDueAmount;

    @FXML
    private TableColumn<?, ?> colPayId;

    @FXML
    private TableColumn<?, ?> colPaymentDate;

    @FXML
    private TableColumn<?, ?> colPrepayment;

    @FXML
    private TableColumn<?, ?> colRegisId;

    @FXML
    private TableColumn<?, ?> colTotalA;

    @FXML
    private AnchorPane registrPane;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TextField txtAPayable;

    @FXML
    private TextField txtPayId;

    @FXML
    private TextField txtPaymentDate;

    @FXML
    private TextField txtPrePay;

    @FXML
    private TextField txtpayment;

    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Payment);
    RegistrationBO registrationBO = (RegistrationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Registration);

    public void initialize() {
     setDate();
     setCellValueFactory();
     loadAllPayment();
     getCurrentPaymentId();
     setTableSelection();
     getRegisterationIds();
  }

    private void getRegisterationIds() {
        List<RegistrationDTO> registerList = registrationBO.getAllRegistrations();

        for (RegistrationDTO registrationDTO : registerList){
            cmbReId.getItems().add(registrationDTO.getRegistrationID());
        }

    }

    private void setTableSelection() {
        tblPayment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                getProgram(newValue);
            }
        });
    }

    private void getProgram(PaymentTm paymentTm) {
        txtPayId.setText(paymentTm.getPayment_id());
        cmbReId.setValue(paymentTm.getRegistration_id());
        txtPaymentDate.setText(paymentTm.getPayment_date());
        txtPrePay.setText(String.valueOf(paymentTm.getPrepayment()));
        txtAPayable.setText(String.valueOf(paymentTm.getTotal_amount()));
        txtpayment.setText(String.valueOf(paymentTm.getDue_amount()));
    }

    private String getCurrentPaymentId() {
        try {
            String currentId = paymentBO.getCurrentId();
            if (currentId != null) {
                String[] split = currentId.split("Pay00");
                int idNum = Integer.parseInt(split[1]);
                String availableId = "Pay00" + ++idNum;
                txtPayId.setText(availableId);
                return availableId;
            } else {
                txtPayId.setText("Pay001");
                return "Pay001";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    private void loadAllPayment() {
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();

        try {
            List<PaymentDTO> paymentList = paymentBO.getAllPayment();

            for(PaymentDTO paymentDTO : paymentList){

                PaymentTm paymentTm = new PaymentTm(
                        paymentDTO.getPayment_id(),
                        paymentDTO.getRegistration().getRegistrationID(),
                        paymentDTO.getPayment_date(),
                        paymentDTO.getPrepayment(),
                        paymentDTO.getTotal_amount(),
                        paymentDTO.getDue_amount()
                );
                obList.add(paymentTm);
            }
            tblPayment.setItems(obList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colPayId.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        colRegisId.setCellValueFactory(new PropertyValueFactory<>("registration_id"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("payment_date"));
        colPrepayment.setCellValueFactory(new PropertyValueFactory<>("prepayment"));
        colTotalA.setCellValueFactory(new PropertyValueFactory<>("total_amount"));
        colDueAmount.setCellValueFactory(new PropertyValueFactory<>("due_amount"));
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtPaymentDate.setText(String.valueOf(now));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtPayId.setText("");
        cmbReId.setValue("");
        txtPaymentDate.setText("");
        txtPrePay.setText("");
        txtpayment.setText("");
        txtAPayable.setText("");
        getCurrentPaymentId();
    }

    @FXML
    void btnPayOnAction(ActionEvent event) {
        String paymentId = txtPayId.getText();
        String registerId = cmbReId.getValue();
        String date = String.valueOf(Date.valueOf(LocalDate.now()));
        double prepayment = Double.parseDouble(txtPrePay.getText());
        double totalAmount = Double.parseDouble(txtAPayable.getText());
        double dueAmount = Double.parseDouble(txtpayment.getText());

        RegistrationDTO registrationDTO = registrationBO.searchRegistration(registerId);

        Registration registration = new Registration();

        registration.setRegistrationID(registerId);
        registration.setDate(registrationDTO.getDate());
        registration.setStudent(registrationDTO.getStudent());
        registration.setProgram(registrationDTO.getProgram());
        registration.setStudentName(registrationDTO.getStudentName());
        registration.setProgramName(registrationDTO.getProgramName());
        registration.setFee(registrationDTO.getFee());
        registration.setPrepayment(registrationDTO.getPrepayment());

        boolean isSaved = paymentBO.savePayment(new PaymentDTO(paymentId,registration,date,prepayment,totalAmount,dueAmount));

        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION,"Payment saved!").show();
            loadAllPayment();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Payment not saved!").show();
        }
        initUI();

    }

    private void initUI() {
        txtPrePay.setDisable(true);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String paymentId = txtPayId.getText();
        String registerId = cmbReId.getValue();
        String date = String.valueOf(Date.valueOf(LocalDate.now()));
        double upfrontPayment = Double.parseDouble(txtPrePay.getText());
        double totalAmount = Double.parseDouble(txtAPayable.getText());
        double dueAmount = Double.parseDouble(txtpayment.getText());

        RegistrationDTO registrationDTO = registrationBO.searchRegistration(registerId);

        Registration registration = new Registration();

        registration.setRegistrationID(registerId);
        registration.setDate(registrationDTO.getDate());
        registration.setStudent(registrationDTO.getStudent());
        registration.setProgram(registrationDTO.getProgram());
        registration.setStudentName(registrationDTO.getStudentName());
        registration.setProgramName(registrationDTO.getProgramName());
        registration.setFee(registrationDTO.getFee());
        registration.setPrepayment(registrationDTO.getPrepayment());

        boolean isUpdated = paymentBO.update(new PaymentDTO(paymentId,registration,date,upfrontPayment,totalAmount,dueAmount));

        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Payment updated!").show();
            loadAllPayment();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Payment not updated!").show();
        }

    }

    @FXML
    void btndltOnActon(ActionEvent event) {
        String paymentId = txtPayId.getText();

        boolean isDeleted = paymentBO.delete(paymentId);

        if (isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"Payment deleted!").show();
            loadAllPayment();
            clearFields();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Payment not deleted!").show();
        }

    }

}
