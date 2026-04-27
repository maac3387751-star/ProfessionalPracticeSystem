package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dto.UserDTO;

public interface IUserDAO {
    int registerUser(UserDTO user) throws DatabaseSystemException;

    boolean deactivateUser(Integer userId) throws DatabaseSystemException;
}