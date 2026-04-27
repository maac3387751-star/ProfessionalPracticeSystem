/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uv.lis.professionalpracticesystem.logic.interfaces;

import java.util.List;
import uv.lis.professionalpracticesystem.Exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.Exceptions.DataValidationException;
import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;
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
