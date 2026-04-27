package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.exceptions.EntityNotFoundException;
import uv.lis.professionalpracticesystem.logic.dto.TaskDTO;



/**
 * 
 * @author Miguel Aguilar
 */
public interface ITaskDAO {

    boolean registerTask(TaskDTO task) throws DatabaseSystemException, DataIntegrityException;

    boolean updateTaskProgress(TaskDTO task) throws DatabaseSystemException, EntityNotFoundException;
}
