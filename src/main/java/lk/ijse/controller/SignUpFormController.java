package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;
import lk.ijse.util.PasswordUtil;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUpFormController {

    @FXML
    private ComboBox<String> cmbAdminCoordinator;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtRePassword;

    @FXML
    private TextField txtTel;

    @FXML
    private TextField txtUserID;

    @FXML
    private TextField txtUserName;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Users);

    public void initialize() {
        generateUserId();
        loadUser();
    }

    private String generateUserId() {
        try {
            String currentId = userBO.getCurrentId();
            if (currentId != null) {
                String[] split = currentId.split("U");
                int idNum = Integer.parseInt(split[1]);
                String availableId = String.format("U%03d", ++idNum);
                txtUserID.setText(availableId);
                return availableId;
            } else {
                String firstId = "U001";
                txtUserID.setText(firstId);
                return firstId;
            }
        } catch (Exception e) {
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.log(Level.SEVERE, "Error generating user ID", e);
        }
        return null;
    }

    private void loadUser() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            obList.add("Admin");
            obList.add("Coordinator");

            cmbAdminCoordinator.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load user roles: " + e.getMessage()).show();
        }

    }

    @FXML
    void btnSignupOnAction(ActionEvent event) {
        String userID = txtUserID.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String rePassword = txtRePassword.getText();
        String email = txtEmail.getText();
        String tel = txtTel.getText();
        String userType = cmbAdminCoordinator.getValue();


        // Input Validation
        if (userID.isEmpty() || userName.isEmpty() || password.isEmpty() ||
                rePassword.isEmpty() || email.isEmpty() || tel.isEmpty() || userType == null) {
            new Alert(Alert.AlertType.INFORMATION, "All fields must be filled!").show();
            return;
        }

        if (!password.equals(rePassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
            return;
        }

        if (!isValidEmail(email)) {
            new Alert(Alert.AlertType.ERROR, "Invalid email format!").show();
            return;
        }

        if (!isValidPhoneNumber(tel)) {
            new Alert(Alert.AlertType.ERROR, "Invalid mobile number! Must be 10 digits.").show();
            return;
        }
        // Encrypt Password
        String hashedPassword = PasswordUtil.hashPassword(rePassword);

        // Create UserDTO Object
        UserDTO userDTO = new UserDTO(userID, userName, hashedPassword, email, tel, userType);
        try {
            // Save user into the database
            userBO.save(userDTO);
            new Alert(Alert.AlertType.CONFIRMATION, "Success, User registered successfully!").show();
            clearFields(); // Clear input fields after successful registration
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error,Failed to save user").show();
        }

    }

    private boolean isValidPhoneNumber(String tel) {
        String phoneRegex = "\\d{10}"; // Assuming a 10-digit phone number
        return tel.matches(phoneRegex);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

//    private void redirectToDashboard(String userType) throws IOException {
//        String fxmlFile = "/view/";
//
//        if ("Admin".equals(userType)) {
//            fxmlFile += "dashboard_form.fxml"; // Path to Admin Dashboard FXML
//        } else if ("Coordinator".equals(userType)) {
//            fxmlFile += "coodashboard_form.fxml"; // Path to Coordinator Dashboard FXML
//        }
//
//        AnchorPane dashboardNode = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource( fxmlFile)));
//
//        Scene scene = new Scene(dashboardNode);
//        Stage stage = (Stage) rootNode.getScene().getWindow();
//        stage.setScene(scene);
//        stage.centerOnScreen();
//
//        if ("Admin".equals(userType)) {
//            stage.setTitle("Admin Dashboard");
//        } else {
//            stage.setTitle("Coordinator Dashboard");
//        }
//
//    }

    private void clearFields() {
        txtUserID.clear();
        txtUserName.clear();
        txtPassword.clear();
        txtRePassword.clear();
        txtEmail.clear();
        txtTel.clear();
        cmbAdminCoordinator.setValue(null);
    }

    @FXML
    void linkLoginOnAction(ActionEvent event) throws IOException {
        AnchorPane mainNode = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/login_form.fxml")));

        Scene scene = new Scene(mainNode);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Page");

    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtPassOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtRePassOnKeyReleased(KeyEvent event) {

    }

}
