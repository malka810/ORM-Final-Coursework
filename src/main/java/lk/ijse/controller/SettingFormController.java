package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class SettingFormController {
    public JFXTextField txtCurrentUsername;
    public JFXTextField txtNewUsername;
    public JFXTextField txtConfirmUsername;
    public JFXTextField txtConfirmPassword;
    public JFXTextField txtNewPassword;
    public JFXTextField txtCurrentPassword;
    public Label lblCurrentUsername;
    public Label lblNewUsername;
    public Label lblConfirmUsername;
    public Label lblCurrentPassword;
    public Label lblNewPassword;
    public Label lblConfirmPassword;
    public JFXButton btnClose;

    public void saveChangesOnAction(ActionEvent actionEvent) {
    }

    public void closeWindowOnAction(ActionEvent actionEvent) {
    }
}
