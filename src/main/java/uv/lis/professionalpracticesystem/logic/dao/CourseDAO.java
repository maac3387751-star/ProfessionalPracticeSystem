package uv.lis.professionalpracticesystem.logic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uv.lis.professionalpracticesystem.dataaccess.MySQLConnectionDataAccess;
import uv.lis.professionalpracticesystem.exceptions.DataIntegrityException;
import uv.lis.professionalpracticesystem.exceptions.DatabaseSystemException;
import uv.lis.professionalpracticesystem.exceptions.EntityNotFoundException;
import uv.lis.professionalpracticesystem.logic.dto.CourseDTO;
import uv.lis.professionalpracticesystem.logic.dto.ProfessorDTO;
import uv.lis.professionalpracticesystem.logic.interfaces.ICourseDAO;

/**
 * 
 * @author Miguel Aguilar
 */
public class CourseDAO implements ICourseDAO {

    private final MySQLConnectionDataAccess newDataBaseConnection;
    public CourseDAO() {
        this.newDataBaseConnection = new MySQLConnectionDataAccess();
    }


    @Override
    public boolean registerCourse(CourseDTO newCourse) throws DatabaseSystemException, DataIntegrityException {
        boolean isRegistered = false;

        String queryRegisterCourse = "INSERT INTO ExperienciaEducativa (NRC, nombre, programaEducativo, idPeriodo, numeroPersonal) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = newDataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(queryRegisterCourse)) {

            statement.setInt(1, newCourse.getNrc());
            statement.setString(2, newCourse.getCourseName());
            statement.setString(3, newCourse.getAcademicProgram());

            if (newCourse.getPeriodId() != null) {
                statement.setInt(4, newCourse.getPeriodId());
            } else {
                statement.setNull(4, java.sql.Types.INTEGER);
            }

            if (newCourse.getProfessor() != null && newCourse.getProfessor().getFacultyId() != null) {
                statement.setString(5, newCourse.getProfessor().getFacultyId());
            } else {
                statement.setNull(5, java.sql.Types.VARCHAR);
            }

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                isRegistered = true;
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062 || e.getErrorCode() == 1452) {
                throw new DataIntegrityException("Error: Duplicated NRC or invalid academic period/professor.", e);
            }
            throw new DatabaseSystemException("Error saving the course to the database", e);
        }

        return isRegistered;
    }


    @Override
    public CourseDTO getCourseByNRC(int nrc) throws DatabaseSystemException, EntityNotFoundException {
        CourseDTO course = null;

        String queryGetCourse = "SELECT * FROM ExperienciaEducativa WHERE NRC = ?";

        try (Connection connection = newDataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(queryGetCourse)) {

            statement.setInt(1, nrc);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    course = new CourseDTO();

                    course.setNrc(resultSet.getInt("NRC"));
                    course.setCourseName(resultSet.getString("nombre"));
                    course.setAcademicProgram(resultSet.getString("programaEducativo"));
                    course.setPeriodId(resultSet.getInt("idPeriodo"));

                    ProfessorDTO professor = new ProfessorDTO();
                    professor.setFacultyId(resultSet.getString("numeroPersonal"));
                    course.setProfessor(professor);
                } else {
                    throw new EntityNotFoundException("Course not found with the provided NRC.");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseSystemException("Error retrieving the course from the database", e);
        }

        return course;
    }


    @Override
    public java.util.List<CourseDTO> getListCourses() throws DatabaseSystemException {
        java.util.List<CourseDTO> listCourses = new java.util.ArrayList<>();
        String queryGetListCourses = "SELECT NRC, nombre FROM ExperienciaEducativa";
        
        try (Connection connection = newDataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryGetListCourses);
             java.sql.ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                CourseDTO course = new CourseDTO();
                course.setNrc(resultSet.getInt("NRC"));
                course.setCourseName(resultSet.getString("nombre"));
                listCourses.add(course);
            }
            
        } catch (SQLException e) {
            throw new DatabaseSystemException("Error retrieving the list of courses from the database", e);
        }
        
        return listCourses;
    }
}
