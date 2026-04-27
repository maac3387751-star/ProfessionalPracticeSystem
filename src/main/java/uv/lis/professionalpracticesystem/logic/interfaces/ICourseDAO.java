package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.exceptions.EntityNotFoundException;
import uv.lis.professionalpracticesystem.logic.dto.CourseDTO;



/**
 * 
 * @author Miguel Aguilar
 */
public interface ICourseDAO {

    boolean registerCourse(CourseDTO course) throws DatabaseSystemException, DataIntegrityException;

    CourseDTO getCourseByNRC(int nrc) throws DatabaseSystemException, EntityNotFoundException;

    java.util.List<CourseDTO> getListCourses() throws DatabaseSystemException;
}
