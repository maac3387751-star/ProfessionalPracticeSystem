package uv.lis.professionalpracticesystem.logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import uv.lis.professionalpracticesystem.dataaccess.MySQLConnectionDataAccess;
import uv.lis.professionalpracticesystem.logic.dto.ProjectDTO;
import uv.lis.professionalpracticesystem.logic.interfaces.IProjectDAO;
import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.Exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.Exceptions.EntityNotFoundException;



/** 
 * Data Access Object for handling Project persistence.
 * @author Miguel Aguilar 
 */
public class ProjectDAO implements IProjectDAO {
    private final MySQLConnectionDataAccess newDataBaseConnection;



    public ProjectDAO() {
        this.newDataBaseConnection = new MySQLConnectionDataAccess();
    }



    /**
     * Registers a new Project in the database.
     * 
     * @param newProject The ProjectDTO containing the project details to register.
     * @return true if the project was registered successfully, false otherwise.
     * @throws DatabaseSystemException if a database error occurs.
     */
    @Override 
    public boolean registerProject(ProjectDTO newProject) throws DatabaseSystemException, DataIntegrityException {
        boolean isRegistered = false; 
        
        String queryRegisterProject = "INSERT INTO Proyecto (nombreProyecto, descripcion, " +
                "objetivoGeneral, objetivosInmediatos, objetivosMediatos, metodologia, " +
                "recursos, duracion, diasHorario, fechaInicio, fechaTermino, estatus, " +
                "responsabilidades, idEmpresa, idResponsable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = newDataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryRegisterProject)) {
            
            statement.setString(1, newProject.getProjectName());
            statement.setString(2, newProject.getDescription());
            statement.setString(3, newProject.getGeneralObjective());
            statement.setString(4, newProject.getImmediateObjectives());
            statement.setString(5, newProject.getMediateObjectives());
            statement.setString(6, newProject.getMethodology());
            statement.setString(7, newProject.getResources());
            statement.setString(8, newProject.getDuration());
            statement.setString(9, newProject.getScheduleDays());
            
            if (newProject.getStartDate() != null) {
                statement.setDate(10, java.sql.Date.valueOf(newProject.getStartDate()));
            } else {
                statement.setNull(10, java.sql.Types.DATE);
            }
            
            if (newProject.getEndDate() != null) {
                statement.setDate(11, java.sql.Date.valueOf(newProject.getEndDate()));
            } else {
                statement.setNull(11, java.sql.Types.DATE);
            }
            statement.setString(12, newProject.getStatus());
            statement.setString(13, newProject.getResponsibilities());
            
            if (newProject.getLinkedOrganization() != null) {
                statement.setInt(14, newProject.getLinkedOrganization().getIdLinkedOrganization());
            } else {
                statement.setNull(14, java.sql.Types.INTEGER);
            }
            
            if (newProject.getTechnicalSupervisor() != null) {
                statement.setInt(15, newProject.getTechnicalSupervisor().getTechnicalSupervisorId());
            } else {
                statement.setNull(15, java.sql.Types.INTEGER);
            }
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                isRegistered = true;
            }
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062 || e.getErrorCode() == 1452) {
                throw new DataIntegrityException("Error: Duplicated project or invalid linked organization/technical supervisor.", e);
            }
            throw new DatabaseSystemException("Error saving the project to the database", e);
        }
        
        return isRegistered;
    }



    /**
     * Updates an existing Project in the database.
     * 
     * @param project The ProjectDTO containing the updated project details.
     * @return true if the project was updated successfully, false otherwise.
     * @throws DatabaseSystemException if a database error occurs.
     */
    @Override 
    public boolean updateProject(ProjectDTO project) throws DatabaseSystemException, EntityNotFoundException {
        boolean isUpdated = false;
        
        String queryUpdateProject = "UPDATE Proyecto SET nombreProyecto = ?, descripcion = ?, " +
                "objetivoGeneral = ?, objetivosInmediatos = ?, objetivosMediatos = ?, metodologia = ?, " +
                "recursos = ?, duracion = ?, diasHorario = ?, fechaInicio = ?, fechaTermino = ?, estatus = ?, " +
                "responsabilidades = ?, idEmpresa = ?, idResponsable = ? WHERE idProyecto = ?";
        
        try (Connection connection = newDataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryUpdateProject)) {
            
            statement.setString(1, project.getProjectName());
            statement.setString(2, project.getDescription());
            statement.setString(3, project.getGeneralObjective());
            statement.setString(4, project.getImmediateObjectives());
            statement.setString(5, project.getMediateObjectives());
            statement.setString(6, project.getMethodology());
            statement.setString(7, project.getResources());
            statement.setString(8, project.getDuration());
            statement.setString(9, project.getScheduleDays());
            
            if (project.getStartDate() != null) {
                statement.setDate(10, java.sql.Date.valueOf(project.getStartDate()));
            } else {
                statement.setNull(10, java.sql.Types.DATE);
            }
            
            if (project.getEndDate() != null) {
                statement.setDate(11, java.sql.Date.valueOf(project.getEndDate()));
            } else {
                statement.setNull(11, java.sql.Types.DATE);
            }
            statement.setString(12, project.getStatus());
            statement.setString(13, project.getResponsibilities());
            
            if (project.getLinkedOrganization() != null) {
                statement.setInt(14, project.getLinkedOrganization().getIdLinkedOrganization());
            } else {
                statement.setNull(14, java.sql.Types.INTEGER);
            }
            
            if (project.getTechnicalSupervisor() != null) {
                statement.setInt(15, project.getTechnicalSupervisor().getTechnicalSupervisorId());
            } else {
                statement.setNull(15, java.sql.Types.INTEGER);
            }
            
            statement.setInt(16, project.getIdProject());
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                isUpdated = true;
            } else {
                throw new EntityNotFoundException("Project not found with the provided ID to update.");
            }
            
        } catch (SQLException e) {
            throw new DatabaseSystemException("Error updating the project in the database", e);
        }
        
        return isUpdated;
    }

   
    @Override
    public java.util.List<ProjectDTO> getListProjects() throws DatabaseSystemException {
        java.util.List<ProjectDTO> listProjects = new java.util.ArrayList<>();
        String queryGetListProjects = "SELECT idProyecto, nombreProyecto FROM Proyecto";
        
        try (Connection connection = newDataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryGetListProjects);
             java.sql.ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                ProjectDTO project = new ProjectDTO();
                project.setIdProject(resultSet.getInt("idProyecto"));
                project.setProjectName(resultSet.getString("nombreProyecto"));
                listProjects.add(project);
            }
            
        } catch (SQLException e) {
            throw new DatabaseSystemException("Error retrieving the list of projects from the database", e);
        }
        
        return listProjects;
    }
}
