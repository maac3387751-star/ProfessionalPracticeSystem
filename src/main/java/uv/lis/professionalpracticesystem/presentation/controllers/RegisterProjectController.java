package uv.lis.professionalpracticesystem.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import java.time.LocalDate;
import uv.lis.professionalpracticesystem.logic.dao.ProjectDAO;
import uv.lis.professionalpracticesystem.logic.dto.ProjectDTO;
import uv.lis.professionalpracticesystem.exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dao.LinkedOrganizationDAO;
import uv.lis.professionalpracticesystem.logic.dao.TechnicalSupervisorDAO;
import uv.lis.professionalpracticesystem.logic.dto.LinkedOrganizationDTO;
import uv.lis.professionalpracticesystem.logic.dto.TechnicalSupervisorDTO;
import java.util.List;

import uv.lis.professionalpracticesystem.logic.utils.DataValidation;

/** 
 * 
 * @author Miguel Aguilar
 */

public class RegisterProjectController {

    @FXML private TextField projectNameTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private TextArea generalObjectiveTextArea;
    @FXML private TextArea immediateObjectivesTextArea;
    @FXML private TextArea mediateObjectivesTextArea;
    @FXML private TextArea methodologyTextArea;
    @FXML private TextArea resourcesTextArea;
    @FXML private TextArea responsibilitiesTextArea;
    @FXML private TextField durationTextField;
    @FXML private TextField scheduleDaysTextField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<String> organizationComboBox;
    @FXML private ComboBox<String> responsibleComboBox;

    private List<LinkedOrganizationDTO> linkedOrganizations;
    private List<TechnicalSupervisorDTO> technicalSupervisors;

    @FXML
    public void initialize() {
        try {
            LinkedOrganizationDAO linkedOrganizationDAO = new LinkedOrganizationDAO();

            linkedOrganizations = linkedOrganizationDAO.getLinkedOrganization();
            if (organizationComboBox != null) {
                for (LinkedOrganizationDTO organizationDTO : linkedOrganizations) {
                    organizationComboBox.getItems().add(organizationDTO.getOrganizationName());
                }
            }

            TechnicalSupervisorDAO technicalSupervisorDAO = new TechnicalSupervisorDAO();

            technicalSupervisors = technicalSupervisorDAO.getTechnicalSupervisorsList();
            if (responsibleComboBox != null) {
                for (TechnicalSupervisorDTO supervisorDTO : technicalSupervisors) {
                    responsibleComboBox.getItems().add(supervisorDTO.getTechnicalSupervisorFullName());
                }
            }
        } catch (DatabaseSystemException e) {
            showMessage(Alert.AlertType.ERROR, "Error al cargar datos", "No se pudo cargar la información de las organizaciones o responsables técnicos.");
        }
    }

    @FXML
    public void handleRegisterProject() {
        if (validateFields()) {
            ProjectDTO project = new ProjectDTO();

            if (projectNameTextField != null) {
                project.setProjectName(DataValidation.trimInternalSpaces(projectNameTextField.getText()));
            }
            if (descriptionTextArea != null) {
                project.setDescription(DataValidation.trimInternalSpaces(descriptionTextArea.getText()));
            }
            if (generalObjectiveTextArea != null) {
                project.setGeneralObjective(DataValidation.trimInternalSpaces(generalObjectiveTextArea.getText()));
            }

            if (immediateObjectivesTextArea != null) {
                project.setImmediateObjectives(DataValidation.trimInternalSpaces(immediateObjectivesTextArea.getText()));
            }
            if (mediateObjectivesTextArea != null) {
                project.setMediateObjectives(DataValidation.trimInternalSpaces(mediateObjectivesTextArea.getText()));
            }
            if (methodologyTextArea != null) {
                project.setMethodology(DataValidation.trimInternalSpaces(methodologyTextArea.getText()));
            }
            if (resourcesTextArea != null) {
                project.setResources(DataValidation.trimInternalSpaces(resourcesTextArea.getText()));
            }
            if (responsibilitiesTextArea != null) {
                project.setResponsibilities(DataValidation.trimInternalSpaces(responsibilitiesTextArea.getText()));
            }

            if (durationTextField != null) {
                project.setDuration(DataValidation.trimInternalSpaces(durationTextField.getText()));
            }
            if (scheduleDaysTextField != null) {
                project.setScheduleDays(DataValidation.trimInternalSpaces(scheduleDaysTextField.getText()));
            }

            if (startDatePicker != null && startDatePicker.getValue() != null) {
                project.setStartDate(startDatePicker.getValue());
            } else {
                project.setStartDate(LocalDate.now());
            }

            if (endDatePicker != null && endDatePicker.getValue() != null) {
                project.setEndDate(endDatePicker.getValue());
            }

            if (organizationComboBox != null && organizationComboBox.getValue() != null
                    && linkedOrganizations != null) {
                for (LinkedOrganizationDTO organization : linkedOrganizations) {
                    if (organization.getOrganizationName().equals(organizationComboBox.getValue())) {
                        project.setLinkedOrganization(organization);
                        break;
                    }
                }
            }

            if (responsibleComboBox != null && responsibleComboBox.getValue() != null && technicalSupervisors != null) {
                for (TechnicalSupervisorDTO supervisor : technicalSupervisors) {
                    if (supervisor.getTechnicalSupervisorFullName().equals(responsibleComboBox.getValue())) {
                        project.setTechnicalSupervisor(supervisor);
                        break;
                    }
                }
            }

            project.setStatus("Disponible");

            ProjectDAO projectDAO = new ProjectDAO();

            try {
                boolean isRegistered = projectDAO.registerProject(project);
                if (isRegistered) {
                    showMessage(Alert.AlertType.INFORMATION, "Registro Exitoso", "El proyecto ha sido registrado exitosamente.");
                    clearFields();
                }
            } catch (DataIntegrityException e) {
                showMessage(Alert.AlertType.WARNING, "Error en el registro", "El proyecto ya se encuentra registrado o los datos de la organización son inválidos.");
            } catch (DatabaseSystemException e) {
                showMessage(Alert.AlertType.ERROR, "Error del Sistema", "Hubo un problema de conexión. Por favor intente más tarde.");
            }
        }
    }

    @FXML
    public void handleCancelRegister() {
        javafx.scene.Node node = null;

        if (projectNameTextField != null)
            node = projectNameTextField;
        else if (descriptionTextArea != null)
            node = descriptionTextArea;
        else if (durationTextField != null)
            node = durationTextField;

        if (node != null && node.getScene() != null) {
            javafx.stage.Stage stage = (javafx.stage.Stage) node.getScene().getWindow();
            stage.close();
        }
    }

    private boolean validateFields() {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder("Existen errores en los campos:\n");

        if (projectNameTextField != null && DataValidation.isInvalidText(projectNameTextField.getText(), 5)) {
            errorMessage.append("- Nombre del proyecto inválido (mínimo 5 letras reales).\n");
            isValid = false;
        }
        if (descriptionTextArea != null && DataValidation.isInvalidText(descriptionTextArea.getText(), 10)) {
            errorMessage.append("- Descripción muy corta o inválida.\n");
            isValid = false;
        }
        if (generalObjectiveTextArea != null && DataValidation.isInvalidText(generalObjectiveTextArea.getText(), 10)) {
            errorMessage.append("- Objetivo general muy corto o inválido.\n");
            isValid = false;
        }
        if (immediateObjectivesTextArea != null && DataValidation.isInvalidText(immediateObjectivesTextArea.getText(), 5)) {
            errorMessage.append("- Objetivos inmediatos inválidos.\n");
            isValid = false;
        }
        if (mediateObjectivesTextArea != null && DataValidation.isInvalidText(mediateObjectivesTextArea.getText(), 5)) {
            errorMessage.append("- Objetivos mediatos inválidos.\n");
            isValid = false;
        }
        if (methodologyTextArea != null && DataValidation.isInvalidText(methodologyTextArea.getText(), 5)) {
            errorMessage.append("- Metodología inválida.\n");
            isValid = false;
        }
        if (resourcesTextArea != null && DataValidation.isInvalidText(resourcesTextArea.getText(), 5)) {
            errorMessage.append("- Recursos inválidos.\n");
            isValid = false;
        }
        if (responsibilitiesTextArea != null && DataValidation.isInvalidText(responsibilitiesTextArea.getText(), 5)) {
            errorMessage.append("- Responsabilidades inválidas.\n");
            isValid = false;
        }
        if (durationTextField != null && DataValidation.isInvalidText(durationTextField.getText(), 2)) {
            errorMessage.append("- Duración inválida.\n");
            isValid = false;
        }
        if (scheduleDaysTextField != null && DataValidation.isInvalidText(scheduleDaysTextField.getText(), 2)) {
            errorMessage.append("- Días y horario inválidos.\n");
            isValid = false;
        }
        
        if (startDatePicker != null && startDatePicker.getValue() == null) {
            errorMessage.append("- Fecha de inicio requerida.\n");
            isValid = false;
        }
        if (endDatePicker != null && endDatePicker.getValue() == null) {
            errorMessage.append("- Fecha de finalización requerida.\n");
            isValid = false;
        }
        if (organizationComboBox != null && organizationComboBox.getValue() == null) {
            errorMessage.append("- Organización vinculada requerida.\n");
            isValid = false;
        }
        if (responsibleComboBox != null && responsibleComboBox.getValue() == null) {
            errorMessage.append("- Responsable técnico requerido.\n");
            isValid = false;
        }

        if (!isValid) {
            showMessage(Alert.AlertType.WARNING, "Datos Inválidos", errorMessage.toString());
        }
        return isValid;
    }

    private void showMessage(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        if (projectNameTextField != null) {
            projectNameTextField.clear();
        }
        if (descriptionTextArea != null) {
            descriptionTextArea.clear();
        }
        if (generalObjectiveTextArea != null) {
            generalObjectiveTextArea.clear();
        }
        if (immediateObjectivesTextArea != null) {
            immediateObjectivesTextArea.clear();
        }
        if (mediateObjectivesTextArea != null) {
            mediateObjectivesTextArea.clear();
        }
        if (methodologyTextArea != null) {
            methodologyTextArea.clear();
        }
        if (resourcesTextArea != null) {
            resourcesTextArea.clear();
        }
        if (responsibilitiesTextArea != null) {
            responsibilitiesTextArea.clear();
        }
        if (durationTextField != null) {
            durationTextField.clear();
        }
        if (scheduleDaysTextField != null) {
            scheduleDaysTextField.clear();
        }
        if (startDatePicker != null) {
            startDatePicker.setValue(null);
        }
        if (endDatePicker != null) {
            endDatePicker.setValue(null);
        }
        if (organizationComboBox != null) {
            organizationComboBox.getSelectionModel().clearSelection();
        }
        if (responsibleComboBox != null) {
            responsibleComboBox.getSelectionModel().clearSelection();
        }
    }
}
