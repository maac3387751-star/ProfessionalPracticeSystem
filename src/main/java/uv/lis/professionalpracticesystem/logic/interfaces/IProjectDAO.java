package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.logic.dto.ProjectDTO;
import uv.lis.professionalpracticesystem.Exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.Exceptions.EntityNotFoundException;
import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;



/** 
 * Interface that defines the contract for project data operations.
 * @author Miguel Aguilar 
 */
public interface IProjectDAO {

    /**
     * Registers a new Project in the data store.
     * 
     * @param project The ProjectDTO containing the project details to register.
     * @return true if the project was registered successfully, false otherwise.
     * @throws DatabaseSystemException if a data access error occurs.
     */
    boolean registerProject(ProjectDTO project) throws DatabaseSystemException, DataIntegrityException;

    /**
     * Updates an existing Project.
     * 
     * @param project The ProjectDTO containing the updated project details.
     * @return true if the project was updated successfully, false otherwise.
     * @throws DatabaseSystemException if a data access error occurs.
     */
    boolean updateProject(ProjectDTO project) throws DatabaseSystemException, EntityNotFoundException;

    /**
     * Retrieves a list of all Projects.
     * 
     * @return List of ProjectDTO objects.
     * @throws DatabaseSystemException if a data access error occurs.
     */
    java.util.List<ProjectDTO> getListProjects() throws DatabaseSystemException;
}
