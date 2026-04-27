package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.logic.dto.TaskDTO;
import uv.lis.professionalpracticesystem.Exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.Exceptions.EntityNotFoundException;
import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;



/**
 * Interface that defines the contract for task data operations.
 * @author Miguel Aguilar
 */
public interface ITaskDAO {

    /**
     * Registers a new Task in the data store.
     * 
     * @param task The TaskDTO containing the task details to register.
     * @return true if the task was registered successfully, false otherwise.
     * @throws DatabaseSystemException if a data access error occurs.
     */
    boolean registerTask(TaskDTO task) throws DatabaseSystemException, DataIntegrityException;

    /**
     * Updates the progress and notes of an existing Task.
     * 
     * @param task The TaskDTO containing the updated task details.
     * @return true if the task progress was updated successfully, false otherwise.
     * @throws DatabaseSystemException if a data access error occurs.
     */
    boolean updateTaskProgress(TaskDTO task) throws DatabaseSystemException, EntityNotFoundException;
}
