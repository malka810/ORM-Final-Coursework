package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;

import java.io.IOException;
import java.util.Objects;

public class LoginFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Users);

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {


        String username = txtUserName.getText();
        String password = txtPassword.getText();

        try {
            // Check credentials
            boolean isValid = userBO.checkCredentials(username, password);

            if (isValid) {
                String userType = userBO.getUserRole(username);

                if (userType.equalsIgnoreCase("Admin")) {
                    navigatoT0AdminDashboard();
                } else if (userType.equalsIgnoreCase("Coordinator")) {
                    navigatoToCoordinatorDashboard();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Unknown user role!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid credentials!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    private void navigatoToCoordinatorDashboard() throws IOException {
        AnchorPane mainNode = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/coodashboard_form.fxml")));
        Scene scene = new Scene(mainNode);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Home Page");

    }

    private void navigatoT0AdminDashboard() throws IOException {
        AnchorPane mainNode = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/dashboard_form.fxml")));

        Scene scene = new Scene(mainNode);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Home Page");
    }


    @FXML
    void handleSignUpOnAction(ActionEvent event) throws IOException {
        AnchorPane mainNode = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/signup_form.fxml")));

        Scene scene = new Scene(mainNode);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("SignUp Page");

    }

}

