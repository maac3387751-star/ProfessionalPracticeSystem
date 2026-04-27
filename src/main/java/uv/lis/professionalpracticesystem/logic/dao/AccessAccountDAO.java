package uv.lis.professionalpracticesystem.logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import uv.lis.professionalpracticesystem.dataaccess.MySQLConnectionDataAccess;
import uv.lis.professionalpracticesystem.exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dto.AccessAccountDTO;
import uv.lis.professionalpracticesystem.logic.interfaces.IAccessAccountDAO;


/**
 *
 * @author Maria Jose
 */
public class AccessAccountDAO implements IAccessAccountDAO {
    private static final Logger LOGGER = Logger.getLogger(AccessAccountDAO.class.getName());
    private final MySQLConnectionDataAccess newDataBaseConnection;
    
    public AccessAccountDAO(){
        this.newDataBaseConnection = new MySQLConnectionDataAccess();
    }
    
    /**
     * Just for the unit test
     * @param testConnection 
     */
    public AccessAccountDAO(MySQLConnectionDataAccess testConnection) {
        this.newDataBaseConnection = testConnection;
    }

    @Override
    public boolean registerAccessAccount(AccessAccountDTO newAccessAccount) throws DatabaseSystemException {
       boolean isAccountRegistered = false;

        String newQueryDataBase = "INSERT INTO cuentaacceso (nombreUsuario,"
                + " contrasenia, idUsuario) VALUES (?, ?, ?)";

        try (Connection newConnection = newDataBaseConnection.getConnection(); 
                PreparedStatement preparedStatement = newConnection.prepareStatement(newQueryDataBase,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, newAccessAccount.getUserAccount());
            preparedStatement.setString(2, newAccessAccount.getPasswordAccount());
            preparedStatement.setInt(3, newAccessAccount.getUser().getIdUser());
            

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    newAccessAccount.setAccountId(generatedId);
                    isAccountRegistered = true;
                }
            }
        } catch (SQLException e) {
            if(e.getErrorCode() == 1062) {
                LOGGER.log(Level.SEVERE, "Error access account registration", e);
            throw new DataIntegrityException("Duplicate entry: The account is already registered", e);
        }
            throw new DatabaseSystemException("Error trying save the Access account.", e);
        }

        return isAccountRegistered;
    }

    @Override
    public boolean updateAccessAccount(AccessAccountDTO updateAccount) throws DatabaseSystemException {
         boolean isUpdate = false;
         
         String newQueryDataBase = "UPDATE cuentaacceso SET nombreUsuario = ?,"
                 + " contrasenia = ? WHERE idCuenta = ?";
         
         try (Connection newConnection = newDataBaseConnection.getConnection();
                 PreparedStatement preparedStatement = newConnection.prepareStatement(newQueryDataBase)) {
             preparedStatement.setString(1, updateAccount.getUserAccount());
             preparedStatement.setString(2, updateAccount.getPasswordAccount());
             preparedStatement.setInt(3, updateAccount.getAccountId());
             
             isUpdate = preparedStatement.executeUpdate() > 0;
             
         }catch (SQLException e) {
             if(e.getErrorCode() == 1062) {
             LOGGER.log(Level.SEVERE, "Error updating data from the Access account", e);
             throw new DataIntegrityException("Duplicate entry: the username already exists.", e);
            }
             throw new DatabaseSystemException("Account information could not be updated", e);
         }
         return isUpdate;
    }
}