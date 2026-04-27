package uv.lis.professionalpracticesystem.logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uv.lis.professionalpracticesystem.dataaccess.MySQLConnectionDataAccess;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dto.UserDTO;
import uv.lis.professionalpracticesystem.logic.interfaces.IUserDAO;

/**
 *
 * @author Maria Jose
 */
public class UserDAO implements IUserDAO {

    private final MySQLConnectionDataAccess newDataBaseConnection;

    public UserDAO() {
        this.newDataBaseConnection = new MySQLConnectionDataAccess();
    }

    @Override
    public int registerUser(UserDTO newUser) throws DatabaseSystemException {
        int generatedId = -1;

        String queryRegisterUser = "INSERT INTO usuario (nombres, apellidos, correo, telefono, estatus) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = newDataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(queryRegisterUser, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, newUser.getNames());
            statement.setString(2, newUser.getLastNames());
            statement.setString(3, newUser.getEmail());
            statement.setString(4, newUser.getPhone());
            statement.setBoolean(5, true);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            throw new DatabaseSystemException("Error saving the user to the database", e);
        }

        return generatedId;
    }

    @Override
    public boolean deactivateUser(Integer userId) throws DatabaseSystemException {
        boolean isDeactivated = false;
        String queryDeactivateUser = "UPDATE usuario SET estatus = false WHERE idUsuario = ?";

        try (Connection connection = newDataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(queryDeactivateUser)) {

            statement.setInt(1, userId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                isDeactivated = true;
            }

        } catch (SQLException e) {
            throw new DatabaseSystemException("Error deactivating the user in the database", e);
        }

        return isDeactivated;
    }
}