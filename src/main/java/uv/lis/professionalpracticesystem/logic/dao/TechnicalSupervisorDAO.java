/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uv.lis.professionalpracticesystem.logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.dataaccess.MySQLConnectionDataAccess;
import uv.lis.professionalpracticesystem.logic.dto.LinkedOrganizationDTO;
import uv.lis.professionalpracticesystem.logic.dto.TechnicalSupervisorDTO;
import uv.lis.professionalpracticesystem.logic.interfaces.ITechnicalSupervisorDAO;

/**
 *
 * @author Maria Jose
 */
public class TechnicalSupervisorDAO implements ITechnicalSupervisorDAO{
    private static final Logger LOGGER = Logger.getLogger(TechnicalSupervisorDAO.class.getName());
    private final MySQLConnectionDataAccess newDataBaseConnection;
    
    public TechnicalSupervisorDAO() {
        this.newDataBaseConnection = new MySQLConnectionDataAccess();
    }
    
    @Override
    public boolean registerTechnicalSupervisor(TechnicalSupervisorDTO newTechnicalSupervisor) 
            throws DatabaseSystemException {
        boolean isTechnicalSupervisorRegistered = false;
        
        String newQueryDataBase = "INSERT INTO responsabletecnico (nombreCompleto,"
                + " correo, cargo, departamento, idEmpresa) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection newConnection = newDataBaseConnection.getConnection(); 
                PreparedStatement preparedStatement = newConnection.prepareStatement(newQueryDataBase,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, newTechnicalSupervisor.getTechnicalSupervisorFullName());
            preparedStatement.setString(2, newTechnicalSupervisor.getTechnicalSupervisorEmail());
            preparedStatement.setString(3, newTechnicalSupervisor.getJobTitle());
            preparedStatement.setString(4, newTechnicalSupervisor.getDepartment());
            preparedStatement.setInt(5, newTechnicalSupervisor.getLinkedOrganization().getIdLinkedOrganization());
            
            preparedStatement.executeUpdate();
            
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    newTechnicalSupervisor.setTechnicalSupervisorId(generatedId);
                    isTechnicalSupervisorRegistered = true;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error registering Technical supervisor", e);
            throw new DatabaseSystemException("Error registering Technical supervisor", e);
        }

        return isTechnicalSupervisorRegistered;
    }
    
    private TechnicalSupervisorDTO createTechnicalSupervisorDTO(ResultSet resultSet) 
               throws SQLException {
        TechnicalSupervisorDTO technicalSupervisorDTO = new TechnicalSupervisorDTO();
        technicalSupervisorDTO.setTechnicalSupervisorId(resultSet.getInt("idResponsable"));
        technicalSupervisorDTO.setTechnicalSupervisorFullName(resultSet.getString("nombreCompleto"));
        technicalSupervisorDTO.setTechnicalSupervisorEmail(resultSet.getString("correo"));
        technicalSupervisorDTO.setJobTitle(resultSet.getString("cargo"));
        technicalSupervisorDTO.setDepartment(resultSet.getString("departamento"));
        LinkedOrganizationDTO linkedOrganization = new LinkedOrganizationDTO();
        linkedOrganization.setIdLinkedOrganization(resultSet.getInt("idEmpresa"));
        technicalSupervisorDTO.setLinkedOrganization(linkedOrganization);
        
        return technicalSupervisorDTO;
        
    }

    @Override
    public List<TechnicalSupervisorDTO> getTechnicalSupervisorsList() throws DatabaseSystemException {
        List<TechnicalSupervisorDTO> listOfTechnicalSupervisors = new ArrayList<>();
        
        String newQueryDataBase = "SELECT idResponsable, nombreCompleto, correo, cargo,"
                + " departamento, idEmpresa FROM responsableTecnico";
        
        try (Connection newConnection = newDataBaseConnection.getConnection();
                PreparedStatement preparedStatement = newConnection.prepareStatement(newQueryDataBase);
                ResultSet resultSet = preparedStatement.executeQuery()) {
                   while(resultSet.next()) {
                       listOfTechnicalSupervisors.add(createTechnicalSupervisorDTO(resultSet));
                   }
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error showing list of Technical Supervisors in system", e);
                    throw new DatabaseSystemException("Error retrieving Technical Supervisor", e);
                }
        return listOfTechnicalSupervisors;
    }

    @Override
    public TechnicalSupervisorDTO getTechnicalSupervisorById(Integer technicalSupervisorId) 
            throws DatabaseSystemException {
        TechnicalSupervisorDTO foundTechnicalSupervisor = null;
        
        String newQueryDataBase = "SELECT idResponsable, nombreCompleto, correo, cargo,"
                + " departamento, idEmpresa FROM responsableTecnico WHERE idResponsable = ?";
        
        try (Connection newConnection = newDataBaseConnection.getConnection();
            PreparedStatement preparedStatement = newConnection.prepareStatement(newQueryDataBase)) {
            preparedStatement.setInt(1, technicalSupervisorId);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    foundTechnicalSupervisor = createTechnicalSupervisorDTO(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error searching Technical Supervisors by ID in system", e);
                    throw new DatabaseSystemException("Error retrieving Technical Supervisor by ID", e);
        }
        return foundTechnicalSupervisor;
    }   
}
