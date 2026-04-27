package uv.lis.professionalpracticesystem.logic.dto;

import java.time.LocalDate;

/** 
 * 
 * @author Miguel Aguilar 
 */
public class ProjectDTO {
    private Integer idProject;
    private String projectName;
    private String description;
    private String generalObjective;
    private String immediateObjectives;
    private String mediateObjectives;
    private String methodology;
    private String resources;
    private String duration;
    private String scheduleDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String responsibilities;
    private LinkedOrganizationDTO linkedOrganization;
    private TechnicalSupervisorDTO technicalSupervisor;



    public ProjectDTO() {
    }


    public Integer getIdProject() {
        return idProject;
    }

    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGeneralObjective() {
        return generalObjective;
    }

    public void setGeneralObjective(String generalObjective) {
        this.generalObjective = generalObjective;
    }

    public String getImmediateObjectives() {
        return immediateObjectives;
    }

    public void setImmediateObjectives(String immediateObjectives) {
        this.immediateObjectives = immediateObjectives;
    }

    public String getMediateObjectives() {
        return mediateObjectives;
    }

    public void setMediateObjectives(String mediateObjectives) {
        this.mediateObjectives = mediateObjectives;
    }

    public String getMethodology() {
        return methodology;
    }

    public void setMethodology(String methodology) {
        this.methodology = methodology;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getScheduleDays() {
        return scheduleDays;
    }

    public void setScheduleDays(String scheduleDays) {
        this.scheduleDays = scheduleDays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public LinkedOrganizationDTO getLinkedOrganization() {
        return linkedOrganization;
    }

    public void setLinkedOrganization(LinkedOrganizationDTO linkedOrganization) {
        this.linkedOrganization = linkedOrganization;
    }

    public TechnicalSupervisorDTO getTechnicalSupervisor() {
        return technicalSupervisor;
    }

    public void setTechnicalSupervisor(TechnicalSupervisorDTO technicalSupervisor) {
        this.technicalSupervisor = technicalSupervisor;
    }
}
