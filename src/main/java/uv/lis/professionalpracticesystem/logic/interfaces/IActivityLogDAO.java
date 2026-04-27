package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.exceptions.EntityNotFoundException;
import uv.lis.professionalpracticesystem.logic.dto.ActivityLogDTO;



/**
 * 
 * @author Miguel Aguilar
 */
public interface IActivityLogDAO {

    boolean registerActivityLog(ActivityLogDTO activityLog) throws DatabaseSystemException, DataIntegrityException;

    boolean updateActivityLogStatus(ActivityLogDTO activityLog) throws DatabaseSystemException, EntityNotFoundException;

    ActivityLogDTO getActivityLogById(int activityLogId) throws DatabaseSystemException, EntityNotFoundException;
}
