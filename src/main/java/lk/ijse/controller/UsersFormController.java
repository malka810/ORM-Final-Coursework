package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.tm.UserTm;
import lk.ijse.util.PasswordUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersFormController {
    public PasswordField txtRePassword;
    @FXML
    private TextField txtUserID;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTel;
    @FXML
    private ComboBox<String> cmbAdminCoordinator;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<UserTm> tblUser;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPw;

    @FXML
    private TableColumn<?, ?> colRole;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TextField txtUserSearch;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Users);

    public void initialize(){
        setTable();
        setCellValueFactory();
        loadAllUsers();
        generateUserId();
        // Populate the ComboBox with roles
        cmbAdminCoordinator.setItems(FXCollections.observableArrayList("Admin", "Coordinator"));

    }

    private void setTable() {
        ObservableList<UserTm> userTm = FXCollections.observableArrayList();
        List<UserDTO> all = userBO.getAll();
        for (UserDTO userDTO : all) {
          UserTm userTM = new UserTm(userDTO.getUser_Id(),userDTO.getUsername(),userDTO.getPassword(),userDTO.getEmail(),userDTO.getTel(),userDTO.getRole());
            userTm.add(userTM);
        }
        tblUser.setItems(userTm);
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


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPw.setCellValueFactory(new PropertyValueFactory<>("password"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

    }

    private void loadAllUsers() {
        ObservableList<UserTm> obList = FXCollections.observableArrayList();
        try {
            List<UserDTO> userList = userBO.getAllUser();
            for (UserDTO userDTO : userList) {
                UserTm tm = new UserTm(
                        userDTO.getUser_Id(),
                        userDTO.getUsername(),
                        userDTO.getPassword(),
                        userDTO.getEmail(),
                        userDTO.getTel(),
                        userDTO.getRole()

                );

                obList.add(tm);
            }

            tblUser.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
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
            ClearTextField(); // Clear input fields after successful registration
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

    private void ClearTextField() {
        txtUserID.clear();
        txtUserName.clear();
        txtPassword.clear();
        txtEmail.clear();
        txtTel.clear();
        generateUserId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        // Get the selected user from the TableView
        UserTm selectedUser = tblUser.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            // Confirm the deletion
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this user?",
                    ButtonType.YES, ButtonType.NO);
            confirmAlert.setTitle("Delete Confirmation");
            confirmAlert.setHeaderText(null);

            // Check user response
            if (confirmAlert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                try {
                    // Call the UserBO to delete the user
                    boolean isDeleted = userBO.delete(selectedUser.getUserId());
                    if (isDeleted) {
                        // Refresh the table
                        tblUser.getItems().remove(selectedUser);
                        tblUser.refresh();
                        new Alert(Alert.AlertType.CONFIRMATION, "User deleted successfully!").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete user!").show();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "An error occurred while deleting the user: " + e.getMessage()).show();
                }
            }
        } else {
            // Show an alert if no user is selected
            new Alert(Alert.AlertType.WARNING, "Please select a user to delete.").show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isUpdated = userBO.update(new UserDTO(txtUserID.getText(), txtUserName.getText(), txtPassword.getText(), txtEmail.getText(), txtTel.getText(), cmbAdminCoordinator.getValue()));
        if (isUpdated){
            ClearTextField();
            setTable();
            setCellValueFactory();
            tblUser.refresh();
            txtUserID.setText(generateUserId());
            new Alert(Alert.AlertType.CONFIRMATION,"user update successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR,"user update unsuccessfully").show();
        }

    }

    @FXML
    void btnViewAllOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        ClearTextField();

    }

}
