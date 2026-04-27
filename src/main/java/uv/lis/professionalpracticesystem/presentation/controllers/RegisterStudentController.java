package uv.lis.professionalpracticesystem.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import java.util.List;

import uv.lis.professionalpracticesystem.logic.dao.StudentDAO;
import uv.lis.professionalpracticesystem.logic.dao.UserDAO;
import uv.lis.professionalpracticesystem.exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dao.CourseDAO;
import uv.lis.professionalpracticesystem.logic.dao.ProjectDAO;

import uv.lis.professionalpracticesystem.logic.dto.StudentDTO;
import uv.lis.professionalpracticesystem.logic.dto.UserDTO;
import uv.lis.professionalpracticesystem.logic.dto.CourseDTO;
import uv.lis.professionalpracticesystem.logic.dto.ProjectDTO;
import uv.lis.professionalpracticesystem.logic.utils.DataValidation;

/** 
 * 
 * @author Miguel Aguilar
 */

public class RegisterStudentController {

    @FXML private TextField namesTextField;
    @FXML private TextField lastNamesTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField enrollmentTextField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private TextField indigenousLanguageTextField;
    @FXML private ComboBox<String> nrcComboBox;
    @FXML private ComboBox<String> projectComboBox;

    private List<CourseDTO> listCourses;
    private List<ProjectDTO> listProjects;

    @FXML
    public void initialize() {
        if (genderComboBox != null) {
            genderComboBox.getItems().addAll("Masculino", "Femenino", "Otro");
        }

        try {
            CourseDAO courseDAO = new CourseDAO();

            listCourses = courseDAO.getListCourses();
            if (nrcComboBox != null) {
                for (CourseDTO course : listCourses) {
                    nrcComboBox.getItems().add(course.getNrc() + " - " + course.getCourseName());
                }
            }

            ProjectDAO projectDAO = new ProjectDAO();

            listProjects = projectDAO.getListProjects();
            if (projectComboBox != null) {
                for (ProjectDTO project : listProjects) {
                    projectComboBox.getItems().add(project.getProjectName());
                }
            }
        } catch (DatabaseSystemException e) {
            showMessage(Alert.AlertType.ERROR, "Error al cargar datos", "No se pudo cargar la información de cursos o proyectos.");
        }
    }

    @FXML
    public void handleRegisterStudent() {
        if (validateFields()) {
            UserDTO user = new UserDTO();

            if (namesTextField != null) {
                user.setNames(DataValidation.trimInternalSpaces(namesTextField.getText()));
            }
            if (lastNamesTextField != null) {
                user.setLastNames(DataValidation.trimInternalSpaces(lastNamesTextField.getText()));
            }
            if (emailTextField != null) {
                user.setEmail(DataValidation.trimInternalSpaces(emailTextField.getText()));
            }
            if (phoneTextField != null) {
                user.setPhone(DataValidation.formatPhone(phoneTextField.getText()));
            }

            UserDAO userDAO = new UserDAO();

            try {
                int generatedUserId = userDAO.registerUser(user);

                if (generatedUserId > 0) {
                    StudentDTO student = new StudentDTO();

                    student.setIdUser(generatedUserId);

                    if (enrollmentTextField != null) {
                        student.setEnrollment(DataValidation.formatEnrollment(enrollmentTextField.getText()));
                    }
                    if (genderComboBox != null && genderComboBox.getValue() != null) {
                        student.setGender(genderComboBox.getValue());
                    }
                    if (indigenousLanguageTextField != null) {
                        String language = DataValidation.trimInternalSpaces(indigenousLanguageTextField.getText());
                        student.setIndigenousLanguage(language.isEmpty() ? null : language);
                    }

                    if (nrcComboBox != null && nrcComboBox.getValue() != null && listCourses != null) {
                        for (CourseDTO course : listCourses) {
                            String comboDisplay = course.getNrc() + " - " + course.getCourseName();
                            if (comboDisplay.equals(nrcComboBox.getValue())) {
                                student.setCourse(course);
                                break;
                            }
                        }
                    }

                    if (projectComboBox != null && projectComboBox.getValue() != null && listProjects != null) {
                        for (ProjectDTO project : listProjects) {
                            if (project.getProjectName().equals(projectComboBox.getValue())) {
                                student.setProject(project);
                                break;
                            }
                        }
                    }

                    StudentDAO studentDAO = new StudentDAO();
                    boolean isRegistered = studentDAO.registerStudent(student);

                    if (isRegistered) {
                        showMessage(Alert.AlertType.INFORMATION, "Registro Exitoso", "El estudiante ha sido registrado exitosamente en el SPP.");
                        clearFields();
                    }
                }
            } catch (DataIntegrityException e) {
                showMessage(Alert.AlertType.WARNING, "Error de Integridad", "La matrícula, correo u otros datos ya existen o son inválidos.");
            } catch (DatabaseSystemException e) {
                showMessage(Alert.AlertType.ERROR, "Error del Sistema", "Hubo un problema de conexión. Por favor intente más tarde.");
            }
        }
    }

    @FXML
    public void handleCancelRegister() {
        javafx.scene.Node node = null;

        if (namesTextField != null) {
            node = namesTextField;
        } else if (enrollmentTextField != null) {
            node = enrollmentTextField;
        }

        if (node != null && node.getScene() != null) {
            javafx.stage.Stage stage = (javafx.stage.Stage) node.getScene().getWindow();
            stage.close();
        }
    }

    private boolean validateFields() {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder("Existen errores en los campos:\n");

        if (namesTextField != null && DataValidation.isInvalidName(namesTextField.getText())) {
            errorMessage.append("- Nombre inválido (solo letras y espacios simples).\n");
            isValid = false;
        }
        if (lastNamesTextField != null && DataValidation.isInvalidName(lastNamesTextField.getText())) {
            errorMessage.append("- Apellidos inválidos (solo letras y espacios simples).\n");
            isValid = false;
        }
        if (emailTextField != null && DataValidation.isInvalidEmail(emailTextField.getText())) {
            errorMessage.append("- Correo electrónico inválido.\n");
            isValid = false;
        }
        if (phoneTextField != null && DataValidation.formatPhone(phoneTextField.getText()) == null) {
            errorMessage.append("- Teléfono inválido (debe contener exactamente 10 dígitos).\n");
            isValid = false;
        }
        if (enrollmentTextField != null && DataValidation.formatEnrollment(enrollmentTextField.getText()) == null) {
            errorMessage.append("- Matrícula inválida (ej. S24014329).\n");
            isValid = false;
        }
        if (genderComboBox != null && genderComboBox.getValue() == null) {
            errorMessage.append("- Seleccione un género.\n");
            isValid = false;
        }
        if (nrcComboBox != null && nrcComboBox.getValue() == null) {
            errorMessage.append("- Seleccione una experiencia educativa.\n");
            isValid = false;
        }
        if (projectComboBox != null && projectComboBox.getValue() == null) {
            errorMessage.append("- Seleccione un proyecto.\n");
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
        if (namesTextField != null) {
            namesTextField.clear();
        }
        if (lastNamesTextField != null) {
            lastNamesTextField.clear();
        }
        if (emailTextField != null) {
            emailTextField.clear();
        }
        if (phoneTextField != null) {
            phoneTextField.clear();
        }
        if (enrollmentTextField != null) {
            enrollmentTextField.clear();
        }
        if (indigenousLanguageTextField != null) {
            indigenousLanguageTextField.clear();
        }
        if (genderComboBox != null) {
            genderComboBox.getSelectionModel().clearSelection();
        }
        if (nrcComboBox != null) {
            nrcComboBox.getSelectionModel().clearSelection();
        }
        if (projectComboBox != null) {
            projectComboBox.getSelectionModel().clearSelection();
        }
    }
}
