package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.exceptions.EntityNotFoundException;
import uv.lis.professionalpracticesystem.logic.dto.ProjectDTO;



/** 
 * 
 * @author Miguel Aguilar 
 */
public interface IProjectDAO {

    boolean registerProject(ProjectDTO project) throws DatabaseSystemException, DataIntegrityException;

    boolean updateProject(ProjectDTO project) throws DatabaseSystemException, EntityNotFoundException;

    java.util.List<ProjectDTO> getListProjects() throws DatabaseSystemException;
}
