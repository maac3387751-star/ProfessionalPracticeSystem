package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dto.ProfessorDTO;

/**
 *
 * @author Maria Jose
 */
public interface IProfessorDAO {
    boolean registerUserProfessor(ProfessorDTO newProfessor)
            throws DatabaseSystemException;
    boolean deactivateUserProfessor (String facultyId)
            throws DatabaseSystemException;
    ProfessorDTO getProfessorByFacultyId(String facultyId) 
            throws DatabaseSystemException;
}


