package uv.lis.professionalpracticesystem.logic.dto;

import java.time.LocalDate;

/** 
 * 
 * @author Miguel Aguilar 
 */
public class TaskDTO {
    private Integer taskId;
    private String taskDescription;
    private LocalDate plannedStartDate;
    private LocalDate plannedEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private Double hoursLogged;
    private Double progressPercentage;
    private String tasksNotes;
    private ProjectDTO project; 



    public TaskDTO() {
    }


    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDate getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedStartDate(LocalDate plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public LocalDate getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(LocalDate plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public LocalDate getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(LocalDate actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public LocalDate getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(LocalDate actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public Double getHoursLogged() {
        return hoursLogged;
    }

    public void setHoursLogged(Double hoursLogged) {
        this.hoursLogged = hoursLogged;
    }

    public Double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getTasksNotes() {
        return tasksNotes;
    }

    public void setTasksNotes(String tasksNotes) {
        this.tasksNotes = tasksNotes;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }
}
