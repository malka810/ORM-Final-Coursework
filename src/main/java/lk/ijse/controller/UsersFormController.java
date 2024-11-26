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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersFormController {
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
    private ComboBox<?> cmbAdminCoordinator;
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
    }

    private void setTable() {
        ObservableList<UserTm> userTm = FXCollections.observableArrayList();
        List<UserDTO> all = userBO.getAll();
        for (UserDTO userDTO : all) {
          UserTm userTM = new UserTm(userDTO.getUserId(),userDTO.getUsername(),userDTO.getPassword(),userDTO.getEmail(),userDTO.getTel(),userDTO.getRole());
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
                        userDTO.getUserId(),
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

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewAllOnAction(ActionEvent event) {

    }

}
