package uv.lis.professionalpracticesystem.logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import uv.lis.professionalpracticesystem.dataaccess.MySQLConnectionDataAccess;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dto.ProfessorDTO;
import uv.lis.professionalpracticesystem.logic.interfaces.IProfessorDAO;


/**
 *
 * @author Maria Jose
 */
public class ProfessorDAO implements IProfessorDAO {

    private static final Logger LOGGER = Logger.getLogger(ProfessorDAO.class.getName());
    private final MySQLConnectionDataAccess newDataBaseConnection;

    public ProfessorDAO() {
        this.newDataBaseConnection = new MySQLConnectionDataAccess();
    }

    @Override
    public boolean registerUserProfessor(ProfessorDTO newProfessor)
            throws DatabaseSystemException {

        boolean isProfessorRegistered = false;

        String queryNewProfessor = "INSERT INTO profesor (numeroPersonal, turno, "
                + "esCoordinador,fechaRegistro, fechaBaja,idUsuario) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection newConnection = newDataBaseConnection.getConnection(); 
            PreparedStatement preparedStatement = newConnection.prepareStatement(queryNewProfessor)) {
            preparedStatement.setString(1, newProfessor.getFacultyId());
            preparedStatement.setString(2, newProfessor.getProfessorShift());
            preparedStatement.setBoolean(3, newProfessor.getIsUserCoordinator());
            preparedStatement.setDate(4, newProfessor.getRegistrationDate());
            preparedStatement.setDate(5, newProfessor.getDeactivationDate());
            preparedStatement.setInt(6, newProfessor.getIdUser());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                isProfessorRegistered = true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error registering Professor", e);
            throw new DatabaseSystemException("Error registering a new Professor", e);
        }
        return isProfessorRegistered;
    }

    private ProfessorDTO createProfessorDTO(ResultSet resultSet)
            throws SQLException {
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setFacultyId(resultSet.getString("numeroPersonal"));
        professorDTO.setProfessorShift(resultSet.getString("turno"));
        professorDTO.setIsUserCoordinator(resultSet.getBoolean("esCoordinador"));
        professorDTO.setRegistrationDate(resultSet.getDate("fechaRegistro"));
        professorDTO.setDeactivationDate(resultSet.getDate("fechaBaja"));
        professorDTO.setIdUser(resultSet.getInt("idUsuario"));

        return professorDTO;
    }
    @Override
    public boolean deactivateUserProfessor(String facultyId) throws DatabaseSystemException {
        boolean isDeactivated = false;

        String newQueryDataBase = "UPDATE usuario u "
                + "INNER JOIN profesor pr ON u.idUsuario = pr.idUsuario "
                + "SET u.estatus = ?, pr.fechaBaja = ? "
                + "WHERE pr.numeroPersonal = ?";
        try (Connection newConnection = newDataBaseConnection.getConnection(); 
            PreparedStatement preparedStatement = newConnection.prepareStatement(newQueryDataBase)) {

            preparedStatement.setBoolean(1, false);

            long currentTime = System.currentTimeMillis();
            preparedStatement.setDate(2, new java.sql.Date(currentTime));

            preparedStatement.setString(3, facultyId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                isDeactivated = true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deactivating professor, faculty ID: "
                    + facultyId, e);
            throw new DatabaseSystemException("Could not deactivate Professor", e);
        }
        return isDeactivated;
    }
    
    @Override
    public ProfessorDTO getProfessorByFacultyId(String facultyId) throws DatabaseSystemException {
        ProfessorDTO foundProfessor = null;

        String newQueryDataBase = "SELECT numeroPersonal,turno,esCoordinador, "
                + "fechaRegistro,fechaBaja,idUsuario FROM profesor WHERE numeroPersonal = ?";

        try (Connection newConnection = newDataBaseConnection.getConnection(); 
            PreparedStatement preparedStatement = newConnection.prepareStatement(newQueryDataBase)) {
            preparedStatement.setString(1, facultyId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    foundProfessor = createProfessorDTO(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving Professor by faculty ID", e);
            throw new DatabaseSystemException("Error retrieving Professor by faculty ID", e);
        }
        return foundProfessor;
    }
}
