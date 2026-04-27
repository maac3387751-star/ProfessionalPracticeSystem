package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.logic.dto.CourseDTO;
import uv.lis.professionalpracticesystem.Exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.Exceptions.EntityNotFoundException;
import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;



/**
 * Interface that defines the contract for Course data operations.
 * @author Miguel Aguilar
 */
public interface ICourseDAO {

    /**
     * Registers a new Course in the data store.
     * 
     * @param course The CourseDTO containing the course details to register.
     * @return true if the course was registered successfully, false otherwise.
     * @throws DatabaseSystemException if a data access error occurs.
     */
    boolean registerCourse(CourseDTO course) throws DatabaseSystemException, DataIntegrityException;

    /**
     * Retrieves a Course by its NRC.
     * 
     * @param nrc The National Roster Code of the course to retrieve.
     * @return CourseDTO containing the data, or null if not found.
     * @throws DatabaseSystemException if a data access error occurs.
     */
    CourseDTO getCourseByNRC(int nrc) throws DatabaseSystemException, EntityNotFoundException;

    /**
     * Retrieves a list of all Courses.
     * 
     * @return List of CourseDTO objects.
     * @throws DatabaseSystemException if a data access error occurs.
     */
    java.util.List<CourseDTO> getListCourses() throws DatabaseSystemException;
}
