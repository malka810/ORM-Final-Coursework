package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class RegistrationFormController {

    @FXML
    private ComboBox<?> cmbProgramNames;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private TableColumn<?, ?> colUpfrontPayment;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblProgrameFee;

    @FXML
    private Label lblRegisterId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<?> tblRegistration;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtPrePayment;

    @FXML
    private TextField txtProgramId;

    @FXML
    private TextField txtStudentId;

    @FXML
    private TextField txtStudentName;

    @FXML
    void btnPaymentOnAction(ActionEvent event) {

    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewRegisOnAction(ActionEvent event) {

    }

    @FXML
    void cmbProgramNamesOnAction(ActionEvent event) {

    }

    @FXML
    void getDateOnAction(ActionEvent event) {

    }

    @FXML
    void txtPaymentOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtStudentIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentIdOnKeyReleased(KeyEvent event) {

    }

}
