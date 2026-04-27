package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.exceptions.EntityNotFoundException;
import uv.lis.professionalpracticesystem.logic.dto.StudentDTO;



/**
 * 
 * @author Miguel Aguilar
 */
public interface IStudentDAO {

    boolean registerStudent(StudentDTO student) throws DatabaseSystemException, DataIntegrityException;

    boolean deactivateStudent(String enrollment) throws DatabaseSystemException, EntityNotFoundException;
}
