/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uv.lis.professionalpracticesystem.GUIviews.controllers;


import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uv.lis.professionalpracticesystem.Exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.Exceptions.DataValidationException;
import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dao.LinkedOrganizationDAO;
import uv.lis.professionalpracticesystem.logic.dto.LinkedOrganizationDTO;

/**
 *
 * @author Maria Jose
 */
public class RegisterOrganizationLinkedController {
    private static final Logger LOGGER = Logger.getLogger(RegisterOrganizationLinkedController.class.getName());
    
    @FXML private TextField organizationName;
    @FXML private ComboBox<String> organizationSector;
    @FXML private TextField addressOrganization;
    @FXML private TextField organizationCity;
    @FXML private TextField organizationState;
    @FXML private TextField phoneNumberOrganization;
    @FXML private TextField organizationEmail;
    @FXML private Spinner<Integer> primaryUsers;
    @FXML private Spinner<Integer> secondaryUsers;
    
    @FXML
    public void initialize() {
        organizationSector.getItems().addAll("Selecciona el sector: ", "Público",
                                                "Privado","Social");
        organizationSector.getSelectionModel().selectFirst();
        
        primaryUsers.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));
        secondaryUsers.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0));  
    }
    
    @FXML
    private void registerNewOrganization(ActionEvent event) {
        if (isFormDataInvalid()) {
            return;
        }
        
        LinkedOrganizationDTO newLinkedOrganization = new LinkedOrganizationDTO();
        
        String cleanName = organizationName.getText().trim().replaceAll("\\s{2,}"," ");
        newLinkedOrganization.setOrganizationName(cleanName);
      
        newLinkedOrganization.setOrganizationName(organizationName.getText().trim());
        newLinkedOrganization.setOrganizationSector(organizationSector.getValue());
        newLinkedOrganization.setAddressOrganization(addressOrganization.getText().trim());
        newLinkedOrganization.setOrganizationCity(organizationCity.getText().trim());
        newLinkedOrganization.setOrganizationState(organizationState.getText().trim());
        newLinkedOrganization.setPhoneNumberOrganization(phoneNumberOrganization.getText().trim());
        newLinkedOrganization.setOrganizationEmail(organizationEmail.getText().trim());
        newLinkedOrganization.setPrimaryUsers(primaryUsers.getValue());
        newLinkedOrganization.setSecondaryUsers(secondaryUsers.getValue());
            
        LinkedOrganizationDAO organizationDAO = new LinkedOrganizationDAO();
            
            try {
                boolean success = organizationDAO.registerOrganizationLinked(newLinkedOrganization);
                
                if (success) {
                    showSimpleAlert(Alert.AlertType.INFORMATION,"Registro exitoso", "Organización registrada.");
                    closeWindow(event);
                } 
                
        } catch (DataValidationException ex) {
            showSimpleAlert(Alert.AlertType.WARNING,"Datos inválidos", ex.getMessage());
        } catch (DataIntegrityException ex) {
            showSimpleAlert(Alert.AlertType.WARNING, "Error de integridad", ex.getMessage());
        }
            catch (DatabaseSystemException ex) {
            LOGGER.log(Level.SEVERE, "No se pudo registrar la organización en la base de datos", ex);
            showSimpleAlert(Alert.AlertType.ERROR,"Error del servidor",
                    "No se pudo conectar a la base de datos, inténtelo más tarde");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error inesperado en el registro", ex);
            showSimpleAlert(Alert.AlertType.ERROR,"Error fatal","Error inesperado");
        }
    }
    
    private boolean isFormDataInvalid() {
        if (hasEmptyFields()) {
            showSimpleAlert(Alert.AlertType.WARNING, "Campos incompletos", "Por favor completa todos los campos.");
            return true;
        }
        
        if (isInvalidName()) {
            showSimpleAlert(Alert.AlertType.WARNING,"Formato inválido","El formato del "
                    + "nombre no es válido.");
            return true;
        }
        
        if (isInvalidEmail()) {
            showSimpleAlert(Alert.AlertType.WARNING,"Correo inválido","El formato del "
                    + " Email no es válido.");
            return true;
        }
        
        if (isInvalidPhoneNumber()) {
            showSimpleAlert(Alert.AlertType.WARNING,"Número de teléfono inválido","El formato del "
                    + "número de teléfono no es válido.");
            return true;
        }
        
        return false;
    }
        
    
    private boolean hasEmptyFields() {
        return organizationName.getText().trim().isEmpty() || organizationSector.getSelectionModel().getSelectedIndex() == 0 ||
                addressOrganization.getText().trim().isEmpty() || organizationCity.getText().trim().isEmpty() ||
                organizationState.getText().trim().isEmpty() || phoneNumberOrganization.getText().trim().isEmpty() ||
                organizationEmail.getText().trim().isEmpty();
    }
    
    private boolean isInvalidName() {
       String namePattern = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ](.*[a-zA-z0-9áéíóúÁÉÍÓÚñÑ])?$";
       return !organizationName.getText().matches(namePattern);
    }
    
    private boolean isInvalidEmail() {
        String emailPattern = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$";
        
        return !organizationEmail.getText().trim().matches(emailPattern);
    }
    
    private boolean isInvalidPhoneNumber() {
        String phoneNumberPattern = "^\\d{7,15}$";
        return !phoneNumberOrganization.getText().trim().matches(phoneNumberPattern);
        }

    @FXML
    private void cancelRegistration(ActionEvent event) {
        showSimpleAlert(Alert.AlertType.INFORMATION,"Cancelación","Registro cancelado.");
        closeWindow(event);
    }
    
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    private void showSimpleAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
