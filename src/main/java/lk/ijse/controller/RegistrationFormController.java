package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.entity.Program;
import lk.ijse.entity.Student;
import lk.ijse.tm.RegistrationTm;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class RegistrationFormController {


    @FXML
    public TextField txtStudentName;
    @FXML
    public TextField txtProgramName;
    @FXML
    public TextField txtFee;
    @FXML
    public TableColumn colFee;
    @FXML
    private ComboBox<String> cmbProgramId;

    @FXML
    private ComboBox<String> cmbStudentId;

    @FXML
    private TableColumn<?, ?> colPId;

    @FXML
    private TableColumn<?, ?> colPrepayment;

    @FXML
    private TableColumn<?, ?> colReDate;

    @FXML
    private TableColumn<?, ?> colReId;

    @FXML
    private TableColumn<?, ?> colSId;

    @FXML
    private AnchorPane registrPane;

    @FXML
    private TableView<RegistrationTm> tbl_registr;

    @FXML
    private TextField txtPrepayment;

    @FXML
    private DatePicker txtReDate;

    @FXML
    private TextField txtRegisId;


    RegistrationBO registrationBO = (RegistrationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Registration);
    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Student);
    ProgramBO programBO = (ProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Programs);

    public  void initialize() {
        getStudentIds();
        gerProgramIds();
        setDate();
        getCurrentRegisterId();
        setCellValueFactory();
        loadAllRegistrations();
        setTableSelection();

    }

    private void setTableSelection() {
        tbl_registr.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                getRegister(newValue);
            }
        });
    }

    private void getRegister(RegistrationTm registrationTm) {
        txtRegisId.setText(registrationTm.getRegistrationID());
        txtReDate.setPromptText(registrationTm.getDate());
        cmbStudentId.setValue(registrationTm.getStudentId());
        cmbProgramId.setValue(registrationTm.getProgramId());
        txtStudentName.setText(registrationTm.getStudentName());
        txtProgramName.setText(registrationTm.getProgramName());
        txtFee.setText(String.valueOf(registrationTm.getFee()));
        txtPrepayment.setText(String.valueOf(registrationTm.getPrepayment()));
    }

    private void loadAllRegistrations() {

        ObservableList<RegistrationTm> obList = FXCollections.observableArrayList();

        List<RegistrationDTO> registerList = registrationBO.getAllRegistrations();

        for (RegistrationDTO registrationDTO : registerList) {

            RegistrationTm registrationTm = new RegistrationTm(
                    registrationDTO.getRegistrationID(),
                    registrationDTO.getDate(),
                    registrationDTO.getStudent().getStudentId(),
                    registrationDTO.getProgram().getProgramId(),
                    registrationDTO.getStudentName(),
                    registrationDTO.getProgramName(),
                    registrationDTO.getFee(),
                    registrationDTO.getPrepayment()
            );
            obList.add(registrationTm);
        }
        tbl_registr.setItems(obList);

    }

    private void setCellValueFactory() {
        colReId.setCellValueFactory(new PropertyValueFactory<>("registrationID"));
        colPId.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colSId.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colReDate.setCellValueFactory(new PropertyValueFactory<>("reDate"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colPrepayment.setCellValueFactory(new PropertyValueFactory<>("prepayment"));
    }

    private void getCurrentRegisterId() {

        String currentId = registrationBO.getCurrentReId();
        String nextId = generateRegisterId(currentId);
        txtRegisId.setText(nextId);

    }

    private String generateRegisterId(String currentId) {
        if (currentId != null && currentId.matches("R\\d+")) {
            int idNum = Integer.parseInt(currentId.substring(1));
            return "R" + String.format("%03d", ++idNum);
        }
        return "R001";
    }

    private void setDate() {
    }

    private void gerProgramIds() {
        List<ProgramDTO> programsList = programBO.getAll();

        for (ProgramDTO programDTO : programsList){
            cmbProgramId.getItems().add(programDTO.getProgramId());
        }
    }

    private void getStudentIds() {
        List<StudentDTO> studentsList = studentBO.getAllStudent();

        for (StudentDTO studentDTO : studentsList){
            cmbStudentId.getItems().add(studentDTO.getStudentId());
        }
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearTextFields();
    }

    private void clearTextFields() {
        txtRegisId.setText("");
        txtReDate.setPromptText("");
        cmbStudentId.setValue("");
        cmbProgramId.setValue("");
        txtStudentName.setText("");
        txtProgramName.setText("");
        txtFee.setText("");
        txtPrepayment.setText("");
        getCurrentRegisterId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String registerId = txtRegisId.getText();

        try {
            boolean isDeleted = registrationBO.delete(registerId);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Registration deleted!").show();
                loadAllRegistrations();
                clearTextFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String registerId = txtRegisId.getText();
        String date = String.valueOf(Date.valueOf(LocalDate.now()));
        String studentId = cmbStudentId.getValue();
        String programId = cmbProgramId.getValue();
        String studentName = txtStudentName.getText();
        String programName = txtProgramName.getText();
        double programFee = Double.parseDouble(txtFee.getText());
        double prepayment = Double.parseDouble(txtPrepayment.getText());

        StudentDTO studentDTO = studentBO.searchById(studentId);

        Student student = new Student();
        student.setStudentId(studentId);
        student.setUser(studentDTO.getUser());
        student.setName(studentDTO.getName());
        student.setAddress(studentDTO.getAddress());
        student.setEmail(studentDTO.getEmail());
        student.setContact(studentDTO.getContact());

        ProgramDTO programDTO = programBO.searchById(programId);

        Program program = new Program();

        program.setProgramId(programId);
        program.setProgramName(programDTO.getProgramName());
        program.setDuration(programDTO.getDuration());
        program.setFee(programDTO.getFee());

        boolean isSaved = registrationBO.save(new RegistrationDTO(registerId,date,student,program,studentName,programName,programFee,prepayment));

        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION,"Registration completed!").show();
            loadAllRegistrations();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Registration not completed!").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String registerId = txtRegisId.getText();
        String date = String.valueOf(Date.valueOf(LocalDate.now()));
        String studentId = cmbStudentId.getValue();
        String programId = cmbProgramId.getValue();
        String studentName = txtStudentName.getText();
        String programName = txtProgramName.getText();
        double programFee = Double.parseDouble(txtFee.getText());
        double prepayment = Double.parseDouble(txtPrepayment.getText());

        StudentDTO studentDTO = studentBO.searchById(studentId);

        Student student = new Student();
        student.setStudentId(studentId);
        student.setUser(studentDTO.getUser());
        student.setName(studentDTO.getName());
        student.setAddress(studentDTO.getAddress());
        student.setEmail(studentDTO.getEmail());
        student.setContact(studentDTO.getContact());

        ProgramDTO programDTO = programBO.searchById(programId);

        Program program = new Program();

        program.setProgramId(programId);
        program.setProgramName(programDTO.getProgramName());
        program.setDuration(programDTO.getDuration());
        program.setFee(programDTO.getFee());

        boolean isUpdated = registrationBO.update(new RegistrationDTO(registerId,date,student,program,studentName,programName,programFee,prepayment));

        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Registration updated!").show();
            loadAllRegistrations();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Registration not updated!").show();
        }

    }

    public void cmbProgramIdOnAction(ActionEvent actionEvent) {
        String programId = cmbProgramId.getValue();

        ProgramDTO programDTO = registrationBO.searchProgram(programId);

        if(programDTO != null){
            txtProgramName.setText(programDTO.getProgramName());
            txtFee.setText(String.valueOf(programDTO.getFee()));
        }
        txtPrepayment.requestFocus();
    }

    public void cmbStudentOnAction(ActionEvent actionEvent) {
        String studentId = cmbStudentId.getValue();

        StudentDTO studentDTO = registrationBO.searchStudent(studentId);

        if(studentDTO != null){
            txtStudentName.setText(studentDTO.getName());
        }
    }
}
