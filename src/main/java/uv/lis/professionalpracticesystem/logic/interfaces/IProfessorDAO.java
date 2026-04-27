/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uv.lis.professionalpracticesystem.logic.interfaces;

import uv.lis.professionalpracticesystem.Exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.logic.dto.ProfessorDTO;

/**
 *
 * @author Maria Jose
 */
public interface IProfessorDAO {
    boolean registerUserProfessor(ProfessorDTO newProfessor)
            throws DatabaseSystemException;
    boolean deactivateUserProfessor (String facultyId)
            throws DatabaseSystemException;
    ProfessorDTO getProfessorByFacultyId(String facultyId) 
            throws DatabaseSystemException;
}


