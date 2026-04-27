package uv.lis.professionalpracticesystem.logic.dto;

import java.time.LocalDate;



/** 
 * 
 * @author Miguel Aguilar
 */
public class ActivityLogDTO {
    private Integer activityLogId;
    private String reportType;
    private LocalDate submissionDate;
    private String signedReportPath;
    private String activityLogNotes;
    private String activityLogOutcomes;
    private String activityLogStatus;
    private String validationNotes;
    private StudentDTO student;
    private ProjectDTO project;



    public ActivityLogDTO() {
    }


    public Integer getActivityLogId() {
        return activityLogId;
    }

    public void setActivityLogId(Integer activityLogId) {
        this.activityLogId = activityLogId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getSignedReportPath() {
        return signedReportPath;
    }

    public void setSignedReportPath(String signedReportPath) {
        this.signedReportPath = signedReportPath;
    }

    public String getActivityLogNotes() {
        return activityLogNotes;
    }

    public void setActivityLogNotes(String activityLogNotes) {
        this.activityLogNotes = activityLogNotes;
    }

    public String getActivityLogOutcomes() {
        return activityLogOutcomes;
    }

    public void setActivityLogOutcomes(String activityLogOutcomes) {
        this.activityLogOutcomes = activityLogOutcomes;
    }

    public String getActivityLogStatus() {
        return activityLogStatus;
    }

    public void setActivityLogStatus(String activityLogStatus) {
        this.activityLogStatus = activityLogStatus;
    }

    public String getValidationNotes() {
        return validationNotes;
    }

    public void setValidationNotes(String validationNotes) {
        this.validationNotes = validationNotes;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }
}
