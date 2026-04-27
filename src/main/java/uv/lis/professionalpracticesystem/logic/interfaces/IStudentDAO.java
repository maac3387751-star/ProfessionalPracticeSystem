package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.logic.dto.StudentDTO;
import uv.lis.professionalpracticesystem.Exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.Exceptions.EntityNotFoundException;
import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;



/**
 * Interface that defines the contract for student data operations.
 * @author Miguel Aguilar
 */
public interface IStudentDAO {

    /**
     * Registers a new Student in the data store.
     * 
     * @param student The StudentDTO containing the student details to register.
     * @return true if the student was registered successfully, false otherwise.
     * @throws DatabaseSystemException if a data access error occurs.
     */
    boolean registerStudent(StudentDTO student) throws DatabaseSystemException, DataIntegrityException;

    /**
     * Deactivates a Student by their enrollment ID.
     * 
     * @param enrollment The enrollment ID of the student to deactivate.
     * @return true if the student was deactivated successfully, false otherwise.
     * @throws DatabaseSystemException if a data access error occurs.
     */
    boolean deactivateStudent(String enrollment) throws DatabaseSystemException, EntityNotFoundException;
}
