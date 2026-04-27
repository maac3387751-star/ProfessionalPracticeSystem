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

import uv.lis.professionalpracticesystem.dataaccess.MySQLConnectionDataAccess;
import uv.lis.professionalpracticesystem.exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.exceptions.DataValidationException;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dto.LinkedOrganizationDTO;
import uv.lis.professionalpracticesystem.logic.interfaces.ILinkedOrganizationDAO;

/**
 *
 * @author MariaJose
 */
public class LinkedOrganizationDAO implements ILinkedOrganizationDAO {

    private static final Logger LOGGER = Logger.getLogger(LinkedOrganizationDAO.class.getName());
    private final MySQLConnectionDataAccess newDataBaseConnection;

    public LinkedOrganizationDAO() {
        this.newDataBaseConnection = new MySQLConnectionDataAccess();
    }

    @Override
    public boolean registerOrganizationLinked(LinkedOrganizationDTO newLinkedOrganization)
            throws DataValidationException, DataIntegrityException, DatabaseSystemException {

        boolean isRegistered = false;

        String newQueryDataBase = "INSERT INTO organizacionvinculada (nombreEmpresa,"
                + "sector,direccion,ciudad,estado,telefono,correo,usuariosDirectos,"
                + "usuariosIndirectos) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection newConnection = newDataBaseConnection.getConnection(); 
                PreparedStatement preparedStatement = newConnection.prepareStatement(newQueryDataBase,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, newLinkedOrganization.getOrganizationName());
            preparedStatement.setString(2, newLinkedOrganization.getOrganizationSector());
            preparedStatement.setString(3, newLinkedOrganization.getAddressOrganization());
            preparedStatement.setString(4, newLinkedOrganization.getOrganizationCity());
            preparedStatement.setString(5, newLinkedOrganization.getOrganizationState());
            preparedStatement.setString(6, newLinkedOrganization.getPhoneNumberOrganization());
            preparedStatement.setString(7, newLinkedOrganization.getOrganizationEmail());
            preparedStatement.setInt(8, newLinkedOrganization.getPrimaryUsers());
            preparedStatement.setInt(9, newLinkedOrganization.getSecondaryUsers());

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    newLinkedOrganization.setIdLinkedOrganization(generatedId);
                    isRegistered = true;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error registering linked organization", e);
            throw new DatabaseSystemException("Error registering Linked Organization", e);
        }

        return isRegistered;
    }

    @Override
    public List<LinkedOrganizationDTO> getLinkedOrganization() throws DatabaseSystemException {
        List<LinkedOrganizationDTO> listLinkedOrganizations = new ArrayList<>();

        String newQueryDataBase = "SELECT idEmpresa,nombreEmpresa,sector,direccion,"
                + "ciudad,estado,telefono,correo,usuariosDirectos,usuariosIndirectos "
                + "FROM organizacionvinculada";

        try (Connection newConnection = newDataBaseConnection.getConnection(); 
                PreparedStatement preparedStatement = newConnection.prepareStatement(newQueryDataBase); 
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                listLinkedOrganizations.add(createLinkedOrganizationDTO(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving linked organizations", e);
            throw new DatabaseSystemException("Error retrieving linked organizations", e);
        }
        return listLinkedOrganizations;
    }

    private LinkedOrganizationDTO createLinkedOrganizationDTO(ResultSet resultSet)
            throws SQLException {
        LinkedOrganizationDTO linkedOrganizationDTO = new LinkedOrganizationDTO();
        linkedOrganizationDTO.setIdLinkedOrganization(resultSet.getInt("idEmpresa"));
        linkedOrganizationDTO.setOrganizationName(resultSet.getString("nombreEmpresa"));
        linkedOrganizationDTO.setOrganizationSector(resultSet.getString("sector"));
        linkedOrganizationDTO.setAddressOrganization(resultSet.getString("direccion"));
        linkedOrganizationDTO.setOrganizationCity(resultSet.getString("ciudad"));
        linkedOrganizationDTO.setOrganizationState(resultSet.getString("estado"));
        linkedOrganizationDTO.setPhoneNumberOrganization(resultSet.getString("telefono"));
        linkedOrganizationDTO.setOrganizationEmail(resultSet.getString("correo"));
        linkedOrganizationDTO.setPrimaryUsers(resultSet.getInt("usuariosDirectos"));
        linkedOrganizationDTO.setSecondaryUsers(resultSet.getInt("usuariosIndirectos"));

        return linkedOrganizationDTO;
    }

    @Override
    public LinkedOrganizationDTO getLinkedOrganizationById(Integer idLinkedOrganization)
            throws DatabaseSystemException {
        LinkedOrganizationDTO foundLinkedOrganization = null;

        String newQueryDataBase = "SELECT idEmpresa,nombreEmpresa,sector,direccion,"
                + "ciudad,estado,telefono,correo,usuariosDirectos,usuariosIndirectos "
                + "FROM organizacionvinculada WHERE idEmpresa = ?";

        try (Connection newConnection = newDataBaseConnection.getConnection(); 
                PreparedStatement preparedStatement = newConnection.prepareStatement(newQueryDataBase)) {
            preparedStatement.setInt(1, idLinkedOrganization);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    foundLinkedOrganization = createLinkedOrganizationDTO(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving linked organizations by ID", e);
            throw new DatabaseSystemException("Error retrieving linked organizations by ID", e);
        }

        return foundLinkedOrganization;
    }
}
