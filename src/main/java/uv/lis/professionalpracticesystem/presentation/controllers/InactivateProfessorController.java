package uv.lis.professionalpracticesystem.presentation.controllers;


import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dao.ProfessorDAO;
import uv.lis.professionalpracticesystem.logic.dto.ProfessorDTO;

/**
 *
 * @author Maria Jose
 */
public class InactivateProfessorController {
    private static final Logger LOGGER = Logger.getLogger(InactivateProfessorController.class.getName());
    
    @FXML private TextField searchFacultyId;
    @FXML private Label professorNames;
    @FXML private Label professorEmail;
    @FXML private Label professorShift;
    @FXML private Button inactivateButton;
    @FXML private GridPane professorDataPane;
    
    private ProfessorDAO professorDAO = new ProfessorDAO();
    private ProfessorDTO currentProfessor;
    
    @FXML
    private void handleSearchProfessor(ActionEvent event) {
        String professorId = searchFacultyId.getText().trim();
        
        if (professorId.isEmpty()) {
            showSimpleAlert(Alert.AlertType.WARNING,"Dato requerido", "Ingrese un número de personal.");
            return;
        }
        
        try {
            currentProfessor = professorDAO.getProfessorByFacultyId(professorId);
            if (currentProfessor == null) {
                professorDataPane.setVisible(false);
                inactivateButton.setDisable(true);
                showSimpleAlert(Alert.AlertType.ERROR,"No encontrado","No se encontro información "
                        + "ligada a ese número de personal.");
            } else {
                professorNames.setText(currentProfessor.getFacultyId());
                professorEmail.setText(currentProfessor.getEmail());
                professorShift.setText(currentProfessor.getProfessorShift());
                
                professorDataPane.setVisible(true);
                inactivateButton.setDisable(false);
            }
        } catch (DatabaseSystemException ex) {
            LOGGER.log(Level.SEVERE, "Error al buscar al profesor", ex);
            showSimpleAlert(Alert.AlertType.ERROR,"Error de conexión", ex.getMessage());
        }
    }
    
    @FXML
    private void handleInactivate(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmación de inactividad");
        confirm.setContentText("¿Estás seguro de inactivar al PROFESOR?");
        
        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                boolean success = professorDAO.deactivateUserProfessor(currentProfessor.getFacultyId());
                if (success) {
                    showSimpleAlert(Alert.AlertType.INFORMATION,"Éxito", "Se cambio el estatus a inactivo con éxito");
                    handleCancel(event);
                }
            } catch (DatabaseSystemException ex) {
                LOGGER.log(Level.SEVERE,"Error de inactivación", ex);
                showSimpleAlert(Alert.AlertType.ERROR,"Error del sistema","No se pudo completar la operación.");
            }
        }
    }
    
    @FXML
    private void handleCancel(ActionEvent event) {
        ((javafx.stage.Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow()).close();
    }
    
    private void showSimpleAlert(Alert.AlertType type, String title, String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
