package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.tm.ProgramTm;

import java.util.List;

public class ProgramFormController {

    @FXML
    private AnchorPane Proot;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colPId;

    @FXML
    private TableColumn<?, ?> colProName;

    @FXML
    private TableView<ProgramTm> tblProgram;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtPName;

    @FXML
    private TextField txtProFee;

    @FXML
    private TextField txtProID;


    ProgramBO programBO = (ProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Programs);
    
    public void initialize() {
        setProgramTable();
        setCellValueFactory();
        selectTable();
        generateProgramId();
    }

    private String generateProgramId() {
        try {
            String currentId = programBO.getCurrentId();
            if (currentId != null) {
                String[] split = currentId.split("P00");
                int idNum = Integer.parseInt(split[1]);
                String availableId = "P00" + ++idNum;
                txtProID.setText(availableId);
                return availableId;
            } else {
                txtProID.setText("P001");
                return "P001";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private void selectTable() {
        tblProgram.setOnMouseClicked(event -> {
            int focusedIndex = tblProgram.getFocusModel().getFocusedIndex();
            ProgramTm programTm = tblProgram.getItems().get(focusedIndex);
            txtProID.setText(programTm.getProgramId());
            txtPName.setText(programTm.getProgramName());
            txtDuration.setText(programTm.getDuration());
            txtProFee.setText(String.valueOf(programTm.getFee()));

        });
    }

    private void setCellValueFactory() {
        colPId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    private void setProgramTable() {
        ObservableList<ProgramTm> obList = FXCollections.observableArrayList();
        List<ProgramDTO> programs = programBO.getAll();
        for (ProgramDTO programDto : programs) {
            ProgramTm programTm = new ProgramTm(
                    programDto.getProgramId(),
                    programDto.getProgramName(),
                    programDto.getDuration(),
                    programDto.getFee()
            );
            obList.add(programTm);
        }
        tblProgram.setItems(obList);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        ClearField();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean isDeleted = programBO.delete(new ProgramDTO(txtProID.getText(), txtPName.getText(), txtDuration.getText(), Double.parseDouble(txtProFee.getText())));
        if (isDeleted){
            ClearField();
            setProgramTable();
            setCellValueFactory();
            tblProgram.refresh();
            generateProgramId();
            new Alert(Alert.AlertType.CONFIRMATION,"program delete successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR,"Program delete unsuccessfully").show();
        }
    }

    private void ClearField() {
        txtProID.setText("");
        txtPName.setText("");
        txtDuration.setText("");
        txtProFee.setText("");
        generateProgramId();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean isSaved = programBO.save(new ProgramDTO(txtProID.getText(), txtPName.getText(), txtDuration.getText(), Double.parseDouble(txtProFee.getText())));
        if (isSaved){
            ClearField();
            setProgramTable();
            setCellValueFactory();
            tblProgram.refresh();
            txtProID.setText(generateProgramId());
            new Alert(Alert.AlertType.CONFIRMATION,"Program save successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR,"Program save unsuccessfully").show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isUpdated = programBO.update(new ProgramDTO(txtProID.getText(), txtPName.getText(), txtDuration.getText(), Double.parseDouble(txtProFee.getText())));
        if (isUpdated){
            ClearField();
            setProgramTable();
            setCellValueFactory();
            tblProgram.refresh();
            txtProID.setText(generateProgramId());
            new Alert(Alert.AlertType.CONFIRMATION,"Program update successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR,"Program update unsuccessfully").show();
        }
    }


}
