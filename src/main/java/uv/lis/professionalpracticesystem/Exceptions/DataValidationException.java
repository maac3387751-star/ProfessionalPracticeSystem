/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uv.lis.professionalpracticesystem.Exceptions;

/**
 *
 * @author Maria Jose
 */
public class DataValidationException extends Exception {
    public DataValidationException(String message) {
        super(message);
    }
    public DataValidationException(String message, Throwable cause){
        super(message,cause);
    }
}
