package uv.lis.professionalpracticesystem.logic.dto;



/** 
 * Data Transfer Object for Student.
 * @author Miguel Aguilar 
 */
public class StudentDTO extends UserDTO {
    private String enrollment;
    private String gender;
    private String indigenousLanguage;
    private CourseDTO course;
    private ProjectDTO project; 



    public StudentDTO() {
        super();
    }


    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIndigenousLanguage() {
        return indigenousLanguage;
    }

    public void setIndigenousLanguage(String indigenousLanguage) {
        this. indigenousLanguage = indigenousLanguage;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }
}
