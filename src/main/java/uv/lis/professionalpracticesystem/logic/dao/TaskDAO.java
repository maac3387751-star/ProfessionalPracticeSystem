package uv.lis.professionalpracticesystem.logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import uv.lis.professionalpracticesystem.dataaccess.MySQLConnectionDataAccess;
import uv.lis.professionalpracticesystem.exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.exceptions.EntityNotFoundException;
import uv.lis.professionalpracticesystem.logic.dto.TaskDTO;
import uv.lis.professionalpracticesystem.logic.interfaces.ITaskDAO;



/**
 * 
 * @author Miguel Aguilar
 */
public class TaskDAO implements ITaskDAO {
    private final MySQLConnectionDataAccess newDataBaseConnection;



    public TaskDAO() {
        this.newDataBaseConnection = new MySQLConnectionDataAccess();
    }




    @Override
    public boolean registerTask(TaskDTO newTask) throws DatabaseSystemException, DataIntegrityException {
        boolean isRegistered = false;
        String queryRegisterTask = "INSERT INTO Actividad (descripcion, fechaInicioPlan, fechaFinPlan, idProyecto) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = newDataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryRegisterTask)) {
            
            statement.setString(1, newTask.getTaskDescription());
            
            if (newTask.getPlannedStartDate() != null) {
                statement.setDate(2, java.sql.Date.valueOf(newTask.getPlannedStartDate()));
            } else {
                statement.setNull(2, java.sql.Types.DATE);
            }
            
            if (newTask.getPlannedEndDate() != null) {
                statement.setDate(3, java.sql.Date.valueOf(newTask.getPlannedEndDate()));
            } else {
                statement.setNull(3, java.sql.Types.DATE);
            }
            
            statement.setInt(4, newTask.getProject().getIdProject());
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                isRegistered = true;
            }
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 1452) {
                throw new DataIntegrityException("Error: The associated project for the task does not exist.", e);
            }
            throw new DatabaseSystemException("Error saving the task to the database", e);
        }
        
        return isRegistered;
    }




    @Override
    public boolean updateTaskProgress(TaskDTO task) throws DatabaseSystemException, EntityNotFoundException {
        boolean isUpdated = false;
        String queryUpdateProgress = "UPDATE Actividad SET horasDedicadas = ?, porcentajeAvance = ?, observaciones = ?, fechaInicioReal = ?, fechaFinReal = ? WHERE idActividad = ?";
        
        try (Connection connection = newDataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryUpdateProgress)) {
            
            statement.setDouble(1, task.getHoursLogged());
            statement.setDouble(2, task.getProgressPercentage());
            statement.setString(3, task.getTasksNotes());
            
            if (task.getActualStartDate() != null) {
                statement.setDate(4, java.sql.Date.valueOf(task.getActualStartDate()));
            } else {
                statement.setNull(4, java.sql.Types.DATE);
            }
            
            if (task.getActualEndDate() != null) {
                statement.setDate(5, java.sql.Date.valueOf(task.getActualEndDate()));
            } else {
                statement.setNull(5, java.sql.Types.DATE);
            }
            
            statement.setInt(6, task.getTaskId());
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                isUpdated = true;
            } else {
                throw new EntityNotFoundException("Task not found with the provided ID to update.");
            }
            
        } catch (SQLException e) {
            throw new DatabaseSystemException("Error updating the task progress in the database", e);
        }
        
        return isUpdated;
    }
}
