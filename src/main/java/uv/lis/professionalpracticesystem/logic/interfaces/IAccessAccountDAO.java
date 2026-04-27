/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dto.AccessAccountDTO;

/**
 *
 * @author Maria Jose
 */
public interface IAccessAccountDAO {
    boolean registerAccessAccount (AccessAccountDTO newAccessAccount)
            throws DatabaseSystemException;
    boolean updateAccessAccount (AccessAccountDTO updateAccount)
            throws DatabaseSystemException;
}
