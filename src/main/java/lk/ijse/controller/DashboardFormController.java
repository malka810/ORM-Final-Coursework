package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {

    @FXML
    private AnchorPane Anchorpane;

    @FXML
    private AnchorPane rootnode;

    @FXML
    void btnCourseOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/program_form.fxml"));
        Parent rootnode = null;
        try {
            rootnode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Anchorpane.getChildren().clear();
        Anchorpane.getChildren().add(rootnode);
    }


    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage =(Stage) this.rootnode.getScene().getWindow();
        stage.setScene(scene);

        stage.setTitle("LogOut Form");
        stage.centerOnScreen();

    }

    @FXML
    void btnRegistrationOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/registration_form.fxml"));
        Parent rootnode = null;
        try {
            rootnode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Anchorpane.getChildren().clear();
        Anchorpane.getChildren().add(rootnode);

    }

    @FXML
    void btnStudentOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/student_form.fxml"));
        Parent rootnode = null;
        try {
            rootnode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Anchorpane.getChildren().clear();
        Anchorpane.getChildren().add(rootnode);


    }

    @FXML
    void btnUserOnAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user_form.fxml"));
        Parent rootnode = null;
        try {
            rootnode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Anchorpane.getChildren().clear();
        Anchorpane.getChildren().add(rootnode);

    }

    public void btnPaymentOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/payment_form.fxml"));
        Parent rootnode = null;
        try {
            rootnode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Anchorpane.getChildren().clear();
        Anchorpane.getChildren().add(rootnode);

    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }
}
