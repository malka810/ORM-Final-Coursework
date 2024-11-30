package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;
import lk.ijse.tm.ProgramTm;
import lk.ijse.tm.StudentTm;
import lk.ijse.tm.UserTm;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.util.List;;

public class StudentFormController {

    @FXML
    private ComboBox<String> cmbUserId;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSEmail;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colUId;

    @FXML
    private TableView<StudentTm> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtStudentID;

    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Student);

     UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Users);

    public void initialize(){
        System.out.println("initialize");
        setCellValueFactory();
        generateStudentId();
        getUserIds();
        setTableSelection();
        setStudentTable();
   }

    private void setStudentTable() {
        ObservableList<StudentTm> obList = FXCollections.observableArrayList();
        List<StudentDTO> student = studentBO.getAllStudent();
        System.out.println(student.size());
        for (StudentDTO studentDTO : student) {
            System.out.println(studentDTO.getName());
            StudentTm studentTm = new StudentTm(
                    studentDTO.getStudentId(),
                    studentDTO.getUser().getUserId(),
                    studentDTO.getName(),
                    studentDTO.getAddress(),
                    studentDTO.getEmail(),
                    studentDTO.getContact()
            );
            obList.add(studentTm);
            System.out.println(obList);
        }
        tblStudent.setItems(obList);
        System.out.println("Students loaded successfully.");
    }


    private void setTableSelection() {
        tblStudent.setOnMouseClicked(event -> {
            int focusedIndex = tblStudent.getFocusModel().getFocusedIndex();
            if (focusedIndex >= 0) { // Ensure the index is valid.
                StudentTm studentTm = tblStudent.getItems().get(focusedIndex);
                txtStudentID.setText(studentTm.getStudentId());
                txtName.setText(studentTm.getName());
                txtAddress.setText(studentTm.getAddress());
                txtEmail.setText(studentTm.getEmail());
                txtContact.setText(studentTm.getContact());
                cmbUserId.getSelectionModel().select(studentTm.getUser_Id());
            }
        });
    }


    private void getUserIds() {
        List<UserDTO> userList = studentBO.getAllUsers();

        for (UserDTO userDTO : userList){
            cmbUserId.getItems().add(userDTO.getUser_Id());
        }
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colUId.setCellValueFactory(new PropertyValueFactory<>("user_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private String generateStudentId() {
        try {
            String currentId = studentBO.getCurrentId();
            if (currentId != null) {
                String[] split = currentId.split("S00");
                int idNum = Integer.parseInt(split[1]);
                String availableId = "S00" + ++idNum;
                txtStudentID.setText(availableId);
                return availableId;
            } else {
                txtStudentID.setText("S001");
                return "S001";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
      clearTextFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String studentId = txtStudentID.getText();

        try {
            boolean isDeleted = studentBO.delete(studentId);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Student deleted!").show();
                setStudentTable();
                clearTextFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String studentID = txtStudentID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String userId = cmbUserId.getValue();

        UserDTO userDTO = userBO.searchByID(userId);

        User user = new User();

        user.setUserId(userId);
        user.setRole(userDTO.getRole());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setTel(userDTO.getTel());

        try {

            if (isValid() && (isEmailValid()) && (isNameValid())) {

                boolean isSaved = studentBO.save(new StudentDTO(studentID, user, name, address, email, contact));

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Student saved!").show();
                    setStudentTable();
                    clearTextFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Student not saved!").show();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValid() {
        if (!Regex.setTextColor(TextFields.StudentID,txtStudentID)) return false;

        if (!Regex.setTextColor(TextFields.Contact,txtContact)) return false;
        return true;
    }

    private boolean isNameValid() {
        if(!Regex.setTextColor(TextFields.Name,txtName));
        return true;
    }

    private boolean isEmailValid() {
        if(!Regex.setTextColor(TextFields.Email,txtEmail));
        return true;
    }

    private void clearTextFields() {
        txtStudentID.clear();
        txtName.clear();
        txtEmail.clear();
        txtContact.clear();
        txtAddress.clear();
        cmbUserId.getSelectionModel().clearSelection();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String studentID = txtStudentID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String userId = cmbUserId.getValue();

        UserDTO userDTO = userBO.searchByID(userId);

        User user = new User();

        user.setUserId(userId);
        user.setRole(userDTO.getRole());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setTel(userDTO.getTel());

        try {

            if (isValid() && (isEmailValid()) && (isNameValid())){

                boolean isUpdated = studentBO.update(new StudentDTO(studentID, user, name, address, email, contact));

                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Student updated!").show();
                    setStudentTable();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbUserIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {

    }

}
