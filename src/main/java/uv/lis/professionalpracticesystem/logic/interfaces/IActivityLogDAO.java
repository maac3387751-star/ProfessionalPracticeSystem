package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.logic.dto.ActivityLogDTO;
import uv.lis.professionalpracticesystem.Exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.Exceptions.EntityNotFoundException;
import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;



/**
 * Interface that defines the contract for Activity Log data operations.
 * @author Miguel Aguilar
 */
public interface IActivityLogDAO {

    /**
     * Registers a new Activity Log in the data store.
     * 
     * @param activityLog The ActivityLogDTO containing the log details to register.
     * @return true if the activity log was registered successfully, false otherwise.
     * @throws DatabaseSystemException if a data access error occurs.
     */
    boolean registerActivityLog(ActivityLogDTO activityLog) throws DatabaseSystemException, DataIntegrityException;

    /**
     * Updates the validation status and notes of an Activity Log.
     * 
     * @param activityLog The ActivityLogDTO containing the updated status.
     * @return true if the activity log status was updated successfully, false otherwise.
     * @throws DatabaseSystemException if a data access error occurs.
     */
    boolean updateActivityLogStatus(ActivityLogDTO activityLog) throws DatabaseSystemException, EntityNotFoundException;

    /**
     * Retrieves an Activity Log by its ID.
     * 
     * @param activityLogId The unique identifier of the activity log to retrieve.
     * @return ActivityLogDTO containing the data, or null if not found.
     * @throws DatabaseSystemException if a data access error occurs.
     */
    ActivityLogDTO getActivityLogById(int activityLogId) throws DatabaseSystemException, EntityNotFoundException;
}
