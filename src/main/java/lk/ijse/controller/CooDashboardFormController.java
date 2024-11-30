package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CooDashboardFormController {

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
    void btnLogoutOnAction(ActionEvent event) {

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

}
