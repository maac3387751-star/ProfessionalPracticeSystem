package uv.lis.professionalpracticesystem.logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uv.lis.professionalpracticesystem.dataaccess.MySQLConnectionDataAccess;
import uv.lis.professionalpracticesystem.exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.exceptions.EntityNotFoundException;
import uv.lis.professionalpracticesystem.logic.dto.ActivityLogDTO;
import uv.lis.professionalpracticesystem.logic.dto.StudentDTO;
import uv.lis.professionalpracticesystem.logic.dto.ProjectDTO;
import uv.lis.professionalpracticesystem.logic.interfaces.IActivityLogDAO;



/** 
 * 
 * @author Miguel Aguilar
 */
public class ActivityLogDAO implements IActivityLogDAO {
    private final MySQLConnectionDataAccess newDataBaseConnection;



    public ActivityLogDAO() {
        this.newDataBaseConnection = new MySQLConnectionDataAccess();
    }




    @Override
    public boolean registerActivityLog(ActivityLogDTO newActivityLog) throws DatabaseSystemException, DataIntegrityException {
        boolean isRegistered = false;

        String queryRegisterLog = "INSERT INTO Reporte (tipoReporte, fechaEntrega, rutaReporteFirmado, observaciones, " +
                "resultadosObtenidos, estatus, matricula, idProyecto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = newDataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryRegisterLog)) {

            statement.setString(1, newActivityLog.getReportType());
            statement.setDate(2, java.sql.Date.valueOf(newActivityLog.getSubmissionDate()));
            statement.setString(3, newActivityLog.getSignedReportPath());
            statement.setString(4, newActivityLog.getActivityLogNotes());
            statement.setString(5, newActivityLog.getActivityLogOutcomes());
            statement.setString(6, newActivityLog.getActivityLogStatus());
            
            if (newActivityLog.getStudent() != null) {
                statement.setString(7, newActivityLog.getStudent().getEnrollment());
            } else {
                statement.setNull(7, java.sql.Types.VARCHAR);
            }
            
            if (newActivityLog.getProject() != null) {
                statement.setInt(8, newActivityLog.getProject().getIdProject());
            } else {
                statement.setNull(8, java.sql.Types.INTEGER);
            }

            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                isRegistered = true;
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1452 || e.getErrorCode() == 1062) {
                throw new DataIntegrityException("Error: Associated student or project not found, or duplicate entry.", e);
            }
            throw new DatabaseSystemException("Error saving the activity log to the database", e);
        }

        return isRegistered;
    }




    @Override
    public boolean updateActivityLogStatus(ActivityLogDTO activityLog) throws DatabaseSystemException, EntityNotFoundException {
        boolean isUpdated = false;

        String queryUpdateStatus = "UPDATE Reporte SET estatus = ?, observacionValidacion = ? WHERE idReporte = ?";

        try (Connection connection = newDataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryUpdateStatus)) {

            statement.setString(1, activityLog.getActivityLogStatus());
            statement.setString(2, activityLog.getValidationNotes());
            statement.setInt(3, activityLog.getActivityLogId());

            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                isUpdated = true;
            } else {
                throw new EntityNotFoundException("No activity log found with the provided ID to update.");
            }

        } catch (SQLException e) {
            throw new DatabaseSystemException("Error updating the activity log status in the database", e);
        }

        return isUpdated;
    }




    @Override
    public ActivityLogDTO getActivityLogById(int activityLogId) throws DatabaseSystemException, EntityNotFoundException {
        ActivityLogDTO activityLog = null;
        
        String queryGetLog = "SELECT * FROM Reporte WHERE idReporte = ?";

        try (Connection connection = newDataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryGetLog)) {

            statement.setInt(1, activityLogId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    activityLog = new ActivityLogDTO();
                    
                    activityLog.setActivityLogId(resultSet.getInt("idReporte"));
                    activityLog.setReportType(resultSet.getString("tipoReporte"));
                    activityLog.setSubmissionDate(resultSet.getDate("fechaEntrega").toLocalDate());
                    activityLog.setSignedReportPath(resultSet.getString("rutaReporteFirmado"));
                    activityLog.setActivityLogNotes(resultSet.getString("observaciones"));
                    activityLog.setActivityLogOutcomes(resultSet.getString("resultadosObtenidos"));
                    activityLog.setActivityLogStatus(resultSet.getString("estatus"));
                    
                    StudentDTO student = new StudentDTO();
                    student.setEnrollment(resultSet.getString("matricula"));
                    activityLog.setStudent(student);
                    
                    ProjectDTO project = new ProjectDTO();
                    project.setIdProject(resultSet.getInt("idProyecto"));
                    activityLog.setProject(project);
                } else {
                    throw new EntityNotFoundException("No activity log found with the provided ID.");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseSystemException("Error retrieving the activity log from the database", e);
        }

        return activityLog;
    }
}
