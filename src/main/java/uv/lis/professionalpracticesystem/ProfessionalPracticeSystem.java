package uv.lis.professionalpracticesystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.stage.Stage;
import uv.lis.professionalpracticesystem.dataaccess.MySQLConnectionDataAccess;
import uv.lis.professionalpracticesystem.logic.dto.ActivityLogDTO;
import uv.lis.professionalpracticesystem.logic.dto.CourseDTO;
import uv.lis.professionalpracticesystem.logic.dto.LinkedOrganizationDTO;
import uv.lis.professionalpracticesystem.logic.dto.ProfessorDTO;
import uv.lis.professionalpracticesystem.logic.dto.ProjectDTO;
import uv.lis.professionalpracticesystem.logic.dto.StudentDTO;
import uv.lis.professionalpracticesystem.logic.dto.TechnicalSupervisorDTO;
import uv.lis.professionalpracticesystem.logic.dao.ActivityLogDAO;
import uv.lis.professionalpracticesystem.logic.dao.CourseDAO;
import uv.lis.professionalpracticesystem.logic.dao.ProjectDAO;
import uv.lis.professionalpracticesystem.logic.dao.StudentDAO;
import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.Exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.Exceptions.EntityNotFoundException;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author Maria Jose
 */
public class ProfessionalPracticeSystem extends Application {

    private static final Logger LOGGER = Logger.getLogger(ProfessionalPracticeSystem.class.getName());

    @Override
    public void start(Stage stage) throws IOException {

        /*
        FXMLLoader loader = new
        FXMLLoader(getClass().getResource("/RegisterProjectFXML.fxml"));
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sistema de Prácticas Profesionales");
        stage.show();
        */

        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegisterStudentFXML.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sistema de Prácticas Profesionales");
        stage.show();
        
    }
    
    public static void startGUIRegisterProject() {
        
    }

    public static void main(String[] args) throws IOException {

        launch();

        MySQLConnectionDataAccess newDataBaseConnection = new MySQLConnectionDataAccess();

        try (Connection newConnection = newDataBaseConnection.getConnection()) {

            if (newConnection != null && !newConnection.isClosed()) {
                LOGGER.info("Connection completed to the Database");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error connection to the Database", e);
        }

        // testCourse();
        // testProject();
        // testStudent();
        // testTask();
        // testActivityLog();

    }

    public static void testCourse() {
        LOGGER.info("Starting Course test...");

        try {
            CourseDTO newCourse = new CourseDTO();
            newCourse.setNrc(12345);
            newCourse.setCourseName("Principios de Construccion de Software");
            newCourse.setAcademicProgram("Licenciatura en Ingeniería de Software");

            newCourse.setPeriodId(1);

            ProfessorDTO newProfessor = new ProfessorDTO();
            newProfessor.setFacultyId("1");
            newCourse.setProfessor(newProfessor);

            CourseDAO courseDAO = new CourseDAO();
            boolean isInserted = courseDAO.registerCourse(newCourse);

            if (isInserted) {
                LOGGER.info("Success. The Course was inserted.");

                CourseDTO addedCourse = courseDAO.getCourseByNRC(12345);
                if (addedCourse != null) {
                    LOGGER.info("Added Course: " + addedCourse.getCourseName());
                }

            } else {
                LOGGER.warning("Failed to insert the Course.");
            }

        } catch (DataIntegrityException ex) {
            LOGGER.log(Level.SEVERE, "Data integrity error in Course test.", ex);
        } catch (EntityNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Entity not found error in Course test.", ex);
        } catch (DatabaseSystemException ex) {
            LOGGER.log(Level.SEVERE, "Error. A Database access error occurred during Course test.", ex);
        } catch (IllegalStateException ex) {
            LOGGER.log(Level.SEVERE, "Error. Could not connect to the database.", ex);
        }
    }

    public static void testProject() {
        LOGGER.info("Starting Project test...");

        try {
            ProjectDTO newProject = new ProjectDTO();
            newProject.setProjectName("Proyecto X");
            newProject.setDescription("Descripción del Proyecto X");
            newProject.setGeneralObjective("Objetivo General del Proyecto X");
            newProject.setStartDate(LocalDate.now());
            newProject.setStatus("Aprobado");
            newProject.setResponsibilities("Responsabilidades del Proyecto X");

            LinkedOrganizationDTO newLinkedOrganization = new LinkedOrganizationDTO();
            newLinkedOrganization.setIdLinkedOrganization(1);
            newProject.setLinkedOrganization(newLinkedOrganization);

            TechnicalSupervisorDTO newTechnicalSupervisor = new TechnicalSupervisorDTO();
            newTechnicalSupervisor.setTechnicalSupervisorId(1);
            newProject.setTechnicalSupervisor(newTechnicalSupervisor);

            ProjectDAO projectDAO = new ProjectDAO();
            boolean isInserted = projectDAO.registerProject(newProject);

            if (isInserted) {
                LOGGER.info("Success. The Project was inserted.");
            } else {
                LOGGER.warning("Failed to insert the Project.");
            }

        } catch (DataIntegrityException ex) {
            LOGGER.log(Level.SEVERE, "Data integrity error in Project test.", ex);
        } catch (DatabaseSystemException ex) {
            LOGGER.log(Level.SEVERE, "Error. A Database access error occurred during Project test.", ex);
        } catch (IllegalStateException ex) {
            LOGGER.log(Level.SEVERE, "Error. Could not connect to the database.", ex);
        }
    }

    public static void testStudent() {
        LOGGER.info("Starting Student test...");

        try {
            StudentDTO newStudent = new StudentDTO();

            newStudent.setEnrollment("S24013297");
            newStudent.setGender("Masculino");
            newStudent.setIndigenousLanguage("Nahuatl");
            newStudent.setIdUser(2);

            CourseDTO newCourse = new CourseDTO();
            newCourse.setNrc(12345);
            newStudent.setCourse(newCourse);

            ProjectDTO newProject = new ProjectDTO();
            newProject.setIdProject(1);
            newStudent.setProject(newProject);

            StudentDAO studentDAO = new StudentDAO();
            boolean isInserted = studentDAO.registerStudent(newStudent);

            if (isInserted) {
                LOGGER.info("Success. The Student was perfectly inserted.");
            } else {
                LOGGER.warning("Failed to insert the Student.");
            }

        } catch (DataIntegrityException ex) {
            LOGGER.log(Level.SEVERE, "Data integrity error in Student test.", ex);
        } catch (DatabaseSystemException ex) {
            LOGGER.log(Level.SEVERE, "Error. A Database access error occurred during Student test!", ex);
        } catch (IllegalStateException ex) {
            LOGGER.log(Level.SEVERE, "Error. Could not connect to the database.", ex);
        }
    }

    public static void testTask() {

    }

    public static void testActivityLog() {
        LOGGER.info("Starting ActivityLog test...");

        try {
            ActivityLogDTO newActivityLog = new ActivityLogDTO();
            newActivityLog.setReportType("Parcial");
            newActivityLog.setSubmissionDate(LocalDate.now());
            newActivityLog.setSignedReportPath("/documentos/pdf_reporte_parcial.pdf");
            newActivityLog.setActivityLogNotes("Esto es un reporte de prueba");
            newActivityLog.setActivityLogOutcomes("Resultados funcionales satisfactorios");
            newActivityLog.setActivityLogStatus("Sin revisar");

            StudentDTO newStudent = new StudentDTO();
            newStudent.setEnrollment("S24013297");
            newActivityLog.setStudent(newStudent);

            ProjectDTO newProject = new ProjectDTO();
            newProject.setIdProject(1);
            newActivityLog.setProject(newProject);

            ActivityLogDAO logDAO = new ActivityLogDAO();
            boolean isInserted = logDAO.registerActivityLog(newActivityLog);

            if (isInserted) {
                LOGGER.info("Success. The Activity Log was inserted.");
            } else {
                LOGGER.warning("Failed to insert the Activity Log.");
            }

        } catch (DataIntegrityException ex) {
            LOGGER.log(Level.SEVERE, "Data integrity error in ActivityLog test.", ex);
        } catch (DatabaseSystemException ex) {
            LOGGER.log(Level.SEVERE, "Error. A Database access error occurred during ActivityLog test.", ex);
        } catch (IllegalStateException ex) {
            LOGGER.log(Level.SEVERE, "Error. Could not connect to the database.", ex);
        }
    }

}
