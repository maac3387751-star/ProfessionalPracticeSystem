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

    @FXML
    private TextField projectName;
    @FXML
    private TextArea description;
    @FXML
    private TextArea generalObjective;
    @FXML
    private TextArea immediateObjectives;
    @FXML
    private TextArea mediateObjectives;
    @FXML
    private TextArea methodology;
    @FXML
    private TextArea resources;
    @FXML
    private TextArea responsibilities;
    @FXML
    private TextField duration;
    @FXML
    private TextField scheduleDays;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ComboBox<String> organization;
    @FXML
    private ComboBox<String> responsible;

    private List<LinkedOrganizationDTO> linkedOrganizations;
    private List<TechnicalSupervisorDTO> technicalSupervisors;

    @FXML
    public void initialize() {
        try {
            LinkedOrganizationDAO linkedOrganizationDAO = new LinkedOrganizationDAO();

            linkedOrganizations = linkedOrganizationDAO.getLinkedOrganization();
            if (organization != null) {
                for (LinkedOrganizationDTO organizationDTO : linkedOrganizations) {
                    organization.getItems().add(organizationDTO.getOrganizationName());
                }
            }

            TechnicalSupervisorDAO technicalSupervisorDAO = new TechnicalSupervisorDAO();

            technicalSupervisors = technicalSupervisorDAO.getTechnicalSupervisorsList();
            if (responsible != null) {
                for (TechnicalSupervisorDTO supervisorDTO : technicalSupervisors) {
                    responsible.getItems().add(supervisorDTO.getTechnicalSupervisorFullName());
                }
            }
        } catch (DatabaseSystemException e) {
            showMessage(Alert.AlertType.ERROR, "Error al cargar datos",
                    "No se pudo cargar la información de las organizaciones o responsables técnicos.");
        }
    }

    @FXML
    public void handleRegisterProject() {
        if (validateFields()) {
            ProjectDTO project = new ProjectDTO();

            if (projectName != null) {
                project.setProjectName(DataValidation.trimInternalSpaces(projectName.getText()));
            }
            if (description != null) {
                project.setDescription(DataValidation.trimInternalSpaces(description.getText()));
            }
            if (generalObjective != null) {
                project.setGeneralObjective(DataValidation.trimInternalSpaces(generalObjective.getText()));
            }

            if (immediateObjectives != null) {
                project.setImmediateObjectives(DataValidation.trimInternalSpaces(immediateObjectives.getText()));
            }
            if (mediateObjectives != null) {
                project.setMediateObjectives(DataValidation.trimInternalSpaces(mediateObjectives.getText()));
            }
            if (methodology != null) {
                project.setMethodology(DataValidation.trimInternalSpaces(methodology.getText()));
            }
            if (resources != null) {
                project.setResources(DataValidation.trimInternalSpaces(resources.getText()));
            }
            if (responsibilities != null) {
                project.setResponsibilities(DataValidation.trimInternalSpaces(responsibilities.getText()));
            }

            if (duration != null) {
                project.setDuration(DataValidation.trimInternalSpaces(duration.getText()));
            }
            if (scheduleDays != null) {
                project.setScheduleDays(DataValidation.trimInternalSpaces(scheduleDays.getText()));
            }

            if (startDate != null && startDate.getValue() != null) {
                project.setStartDate(startDate.getValue());
            } else {
                project.setStartDate(LocalDate.now());
            }

            if (endDate != null && endDate.getValue() != null) {
                project.setEndDate(endDate.getValue());
            }

            if (organization != null && organization.getValue() != null
                    && linkedOrganizations != null) {
                for (LinkedOrganizationDTO linkedOrganization : linkedOrganizations) {
                    if (linkedOrganization.getOrganizationName().equals(organization.getValue())) {
                        project.setLinkedOrganization(linkedOrganization);
                        break;
                    }
                }
            }

            if (responsible != null && responsible.getValue() != null && technicalSupervisors != null) {
                for (TechnicalSupervisorDTO supervisor : technicalSupervisors) {
                    if (supervisor.getTechnicalSupervisorFullName().equals(responsible.getValue())) {
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
                    showMessage(Alert.AlertType.INFORMATION, "Registro Exitoso",
                            "El proyecto ha sido registrado exitosamente.");
                    clearFields();
                }
            } catch (DataIntegrityException e) {
                showMessage(Alert.AlertType.WARNING, "Error en el registro",
                        "El proyecto ya se encuentra registrado o los datos de la organización son inválidos.");
            } catch (DatabaseSystemException e) {
                showMessage(Alert.AlertType.ERROR, "Error del Sistema",
                        "Hubo un problema de conexión. Por favor intente más tarde.");
            }
        }
    }

    @FXML
    public void handleCancelRegister() {
        javafx.scene.Node node = null;

        if (projectName != null)
            node = projectName;
        else if (description != null)
            node = description;
        else if (duration != null)
            node = duration;

        if (node != null && node.getScene() != null) {
            javafx.stage.Stage stage = (javafx.stage.Stage) node.getScene().getWindow();
            stage.close();
        }
    }

    private boolean validateFields() {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder("Existen errores en los campos:\n");

        if (projectName != null && DataValidation.isInvalidText(projectName.getText(), 5)) {
            errorMessage.append("- Nombre del proyecto inválido (mínimo 5 letras reales).\n");
            isValid = false;
        }
        if (description != null && DataValidation.isInvalidText(description.getText(), 10)) {
            errorMessage.append("- Descripción muy corta o inválida.\n");
            isValid = false;
        }
        if (generalObjective != null && DataValidation.isInvalidText(generalObjective.getText(), 10)) {
            errorMessage.append("- Objetivo general muy corto o inválido.\n");
            isValid = false;
        }
        if (immediateObjectives != null && DataValidation.isInvalidText(immediateObjectives.getText(), 5)) {
            errorMessage.append("- Objetivos inmediatos inválidos.\n");
            isValid = false;
        }
        if (mediateObjectives != null && DataValidation.isInvalidText(mediateObjectives.getText(), 5)) {
            errorMessage.append("- Objetivos mediatos inválidos.\n");
            isValid = false;
        }
        if (methodology != null && DataValidation.isInvalidText(methodology.getText(), 5)) {
            errorMessage.append("- Metodología inválida.\n");
            isValid = false;
        }
        if (resources != null && DataValidation.isInvalidText(resources.getText(), 5)) {
            errorMessage.append("- Recursos inválidos.\n");
            isValid = false;
        }
        if (responsibilities != null && DataValidation.isInvalidText(responsibilities.getText(), 5)) {
            errorMessage.append("- Responsabilidades inválidas.\n");
            isValid = false;
        }
        if (duration != null && DataValidation.isInvalidText(duration.getText(), 2)) {
            errorMessage.append("- Duración inválida.\n");
            isValid = false;
        }
        if (scheduleDays != null && DataValidation.isInvalidText(scheduleDays.getText(), 2)) {
            errorMessage.append("- Días y horario inválidos.\n");
            isValid = false;
        }

        if (startDate != null && startDate.getValue() == null) {
            errorMessage.append("- Fecha de inicio requerida.\n");
            isValid = false;
        }
        if (endDate != null && endDate.getValue() == null) {
            errorMessage.append("- Fecha de finalización requerida.\n");
            isValid = false;
        }
        if (organization != null && organization.getValue() == null) {
            errorMessage.append("- Organización vinculada requerida.\n");
            isValid = false;
        }
        if (responsible != null && responsible.getValue() == null) {
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
        if (projectName != null) {
            projectName.clear();
        }
        if (description != null) {
            description.clear();
        }
        if (generalObjective != null) {
            generalObjective.clear();
        }
        if (immediateObjectives != null) {
            immediateObjectives.clear();
        }
        if (mediateObjectives != null) {
            mediateObjectives.clear();
        }
        if (methodology != null) {
            methodology.clear();
        }
        if (resources != null) {
            resources.clear();
        }
        if (responsibilities != null) {
            responsibilities.clear();
        }
        if (duration != null) {
            duration.clear();
        }
        if (scheduleDays != null) {
            scheduleDays.clear();
        }
        if (startDate != null) {
            startDate.setValue(null);
        }
        if (endDate != null) {
            endDate.setValue(null);
        }
        if (organization != null) {
            organization.getSelectionModel().clearSelection();
        }
        if (responsible != null) {
            responsible.getSelectionModel().clearSelection();
        }
    }
}
