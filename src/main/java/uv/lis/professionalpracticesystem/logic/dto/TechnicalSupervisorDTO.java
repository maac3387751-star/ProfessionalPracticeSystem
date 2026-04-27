/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uv.lis.professionalpracticesystem.logic.dto;

/**
 *
 * @author Maria Jose
 */
public class TechnicalSupervisorDTO {
    private Integer technicalSupervisorId;
    private String technicalSupervisorFullName;
    private String technicalSupervisorEmail;
    private String jobTitle;
    private String department;
    private LinkedOrganizationDTO linkedOrganization;
    
    public TechnicalSupervisorDTO() {}

    public Integer getTechnicalSupervisorId() {
        return technicalSupervisorId;
    }

    public void setTechnicalSupervisorId(Integer technicalSupervisorId) {
        this.technicalSupervisorId = technicalSupervisorId;
    }

    public String getTechnicalSupervisorFullName() {
        return technicalSupervisorFullName;
    }

    public void setTechnicalSupervisorFullName(String technicalSupervisorFullName) {
        this.technicalSupervisorFullName = technicalSupervisorFullName;
    }

    public String getTechnicalSupervisorEmail() {
        return technicalSupervisorEmail;
    }

    public void setTechnicalSupervisorEmail(String technicalSupervisorEmail) {
        this.technicalSupervisorEmail = technicalSupervisorEmail;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LinkedOrganizationDTO getLinkedOrganization() {
        return linkedOrganization;
    }

    public void setLinkedOrganization(LinkedOrganizationDTO linkedOrganization) {
        this.linkedOrganization = linkedOrganization;
    }

}
