package uv.lis.professionalpracticesystem.logic.interfaces;

import java.util.List;

import uv.lis.professionalpracticesystem.exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.exceptions.DataValidationException;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dto.LinkedOrganizationDTO;

/**
 *
 * @author Maria Jose
 */
public interface ILinkedOrganizationDAO {
    boolean registerOrganizationLinked (LinkedOrganizationDTO newLinkedOrganization)
            throws DataValidationException, DataIntegrityException,DatabaseSystemException;
    List<LinkedOrganizationDTO> getLinkedOrganization()
            throws DatabaseSystemException;
    LinkedOrganizationDTO getLinkedOrganizationById(Integer idLinkedOrganization)
            throws DatabaseSystemException;
}
