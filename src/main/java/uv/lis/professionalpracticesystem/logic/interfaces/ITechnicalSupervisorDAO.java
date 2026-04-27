/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package uv.lis.professionalpracticesystem.logic.interfaces;

import java.util.List;
import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dto.TechnicalSupervisorDTO;

/**
 *
 * @author Maria Jose
 */
public interface ITechnicalSupervisorDAO {
    boolean registerTechnicalSupervisor(TechnicalSupervisorDTO newTechnicalSupervisor)
            throws DatabaseSystemException;
    List<TechnicalSupervisorDTO> getTechnicalSupervisorsList() 
            throws DatabaseSystemException;
    TechnicalSupervisorDTO getTechnicalSupervisorById(Integer technicalSupervisorId) 
            throws DatabaseSystemException;
}
