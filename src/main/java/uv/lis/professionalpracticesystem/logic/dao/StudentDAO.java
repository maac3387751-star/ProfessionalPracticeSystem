package uv.lis.professionalpracticesystem.logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import uv.lis.professionalpracticesystem.dataaccess.MySQLConnectionDataAccess;
import uv.lis.professionalpracticesystem.logic.dto.StudentDTO;
import uv.lis.professionalpracticesystem.logic.interfaces.IStudentDAO;
import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.Exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.Exceptions.EntityNotFoundException;



/**
 * Data Access Object for handling Student persistence.
 * @author Miguel Aguilar
 */
public class StudentDAO implements IStudentDAO {
    private final MySQLConnectionDataAccess newDataBaseConnection;



    public StudentDAO() {
        this.newDataBaseConnection = new MySQLConnectionDataAccess();
    }



    /**
     * Registers a new Student in the database.
     * 
     * @param newStudent The StudentDTO containing the student details to register.
     * @return true if the student was registered successfully, false otherwise.
     * @throws DatabaseSystemException if a database error occurs.
     */
    @Override
    public boolean registerStudent(StudentDTO newStudent) throws DatabaseSystemException, DataIntegrityException {
        boolean isRegistered = false; 
        String queryRegisterStudent = "INSERT INTO Alumno (matricula, genero, lenguaIndigena, idUsuario, NRC, idProyecto) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = newDataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryRegisterStudent)) {
            
            statement.setString(1, newStudent.getEnrollment());
            statement.setString(2, newStudent.getGender());
            statement.setString(3, newStudent.getIndigenousLanguage());
            statement.setInt(4, newStudent.getIdUser());
            
            if (newStudent.getCourse() != null && newStudent.getCourse().getNrc() != null) {
                statement.setInt(5, newStudent.getCourse().getNrc());
            } else {
                statement.setNull(5, java.sql.Types.INTEGER);
            }
            
            if (newStudent.getProject() != null) {
                statement.setInt(6, newStudent.getProject().getIdProject());
            } else {
                statement.setNull(6, java.sql.Types.INTEGER);
            }
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                isRegistered = true;
            }
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062 || e.getErrorCode() == 1452) {
                throw new DataIntegrityException("Error: Duplicated enrollment ID or invalid course/project.", e);
            }
            throw new DatabaseSystemException("Error saving the student to the database", e);
        }
        
        return isRegistered;
    }



    /**
     * Deactivates a Student by setting their linked user status to false.
     * 
     * @param enrollment The enrollment of the student to deactivate.
     * @return true if the student was deactivated successfully, false otherwise.
     * @throws DatabaseSystemException if a database error occurs.
     */
    @Override
    public boolean deactivateStudent(String enrollment) throws DatabaseSystemException, EntityNotFoundException {
        boolean isDeactivated = false;
        String queryDeactivateStudent = "UPDATE Usuario u INNER JOIN Alumno a ON u.idUsuario = a.idUsuario SET u.estatus = false WHERE a.matricula = ?";
        
        try (Connection connection = newDataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryDeactivateStudent)) {
            
            statement.setString(1, enrollment);
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                isDeactivated = true;
            } else {
                throw new EntityNotFoundException("Student not found with the provided enrollment ID.");
            }
            
        } catch (SQLException e) {
            throw new DatabaseSystemException("Error deactivating the student in the database", e);
        }
        
        return isDeactivated;
    }
}
