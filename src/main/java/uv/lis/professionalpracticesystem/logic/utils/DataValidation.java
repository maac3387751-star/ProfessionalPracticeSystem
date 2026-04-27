/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uv.lis.professionalpracticesystem.logic.utils;

/**
 *
 * @author Maria Jose
 */
public class DataValidation {
    private static final String NAME_PATTERN = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ](.*[a-zA-z0-9áéíóúÁÉÍÓÚñÑ])?$";
   
    private static final String EMAIL_PATTERN = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$";
    
    private static final String PHONENUMBER_PATTERN = "^\\d{7,15}$";

    private static final String FACULTY_ID_PATTERN = "^\\d+$";

    public static boolean isInvalidName(String name) {
        return name == null || !name.matches(NAME_PATTERN);
    }

    public static boolean isInvalidEmail(String email) {
        return email == null || !email.trim().matches(EMAIL_PATTERN);
    }

    public static boolean isInvalidPhoneNumber(String phone) {
        return phone == null || !phone.trim().matches(PHONENUMBER_PATTERN);
    }
    
    public static boolean isInvalidFacultyId(String facultyId) {
        return facultyId == null || !facultyId.trim().matches(FACULTY_ID_PATTERN);
    }

    public static boolean isInvalidText(String text, int minLength) {
        if (text == null) {
            return true;
        }
        String cleanText = trimInternalSpaces(text);
        return cleanText.length() < minLength;
    }
    
    public static String trimInternalSpaces(String text) {
        if (text == null) {
            return "";
        }
        return text.trim().replaceAll("\\s{2,}", " ");
    }
    
    public static String formatPhone(String phone) {
        if (phone == null) { 
            return null; 
        }
        String cleanPhone = phone.replaceAll("[^0-9]", "");
        if (cleanPhone.length() == 10) {
            return cleanPhone;
        }
        return null;
    }
    
    public static String formatEnrollment(String enrollment) {
        if (enrollment == null) { 
            return null; 
        }
        String cleanEnrollment = trimInternalSpaces(enrollment);
        if (cleanEnrollment.matches("^[sSzZ]\\d{8}$")) {
            return "S" + cleanEnrollment.substring(1);
        }
        return null;
    }
}