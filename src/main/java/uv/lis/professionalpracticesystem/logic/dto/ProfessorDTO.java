/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uv.lis.professionalpracticesystem.logic.dto;

import java.sql.Date;

/**
 *
 * @author MAC33
 */
public class ProfessorDTO extends UserDTO{
private String facultyId;
    private String professorShift;
    private Boolean isUserCoordinator;
    private Date registrationDate;
    private Date deactivationDate;
    private String professorStatus;
    
    public ProfessorDTO () {}

    public String getProfessorStatus() {
        return professorStatus;
    }

    public void setProfessorStatus(String professorStatus) {
        this.professorStatus = professorStatus;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getProfessorShift() {
        return professorShift;
    }

    public void setProfessorShift(String professorShift) {
        this.professorShift = professorShift;
    }

    public Boolean getIsUserCoordinator() {
        return isUserCoordinator;
    }

    public void setIsUserCoordinator(Boolean isUserCoordinator) {
        this.isUserCoordinator = isUserCoordinator;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(Date deactivationDate) {
        this.deactivationDate = deactivationDate;
    }
    
}
