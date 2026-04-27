package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dto.AccessAccountDTO;

/**
 *
 * @author Maria Jose
 */
public interface IAccessAccountDAO {
    boolean registerAccessAccount (AccessAccountDTO newAccessAccount)
            throws DatabaseSystemException;
    boolean updateAccessAccount (AccessAccountDTO updateAccount)
            throws DatabaseSystemException;
}
