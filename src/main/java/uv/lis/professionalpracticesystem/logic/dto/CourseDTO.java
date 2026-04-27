package uv.lis.professionalpracticesystem.logic.dto;

/** 
 * 
 * @author Miguel Aguilar
 */
public class CourseDTO {
    private Integer nrc;
    private String courseName;
    private String academicProgram;
    private ProfessorDTO professor;
    private Integer periodId;



    public CourseDTO() {
    }


    public Integer getNrc() {
        return nrc;
    }

    public void setNrc(Integer nrc) {
        this.nrc = nrc;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAcademicProgram() {
        return academicProgram;
    }

    public void setAcademicProgram(String academicProgram) {
        this.academicProgram = academicProgram;
    }

    public ProfessorDTO getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorDTO professor) {
        this.professor = professor;
    }
    
    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }
}
