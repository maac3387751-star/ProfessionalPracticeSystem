package uv.lis.professionalpracticesystem.presentation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Maria Jose
 */
public class PPSMainApp extends Application {
    private static final Logger LOGGER = Logger.getLogger(PPSMainApp.class.getName());
    
    @Override
    public void start(Stage primaryStage) throws Exception {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegisterStudent.fxml"));
           //FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegisterProject.fxml"));
           //FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegisterOrganizationLinked.fxml"));
           //FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InactivateProfessor.fxml"));
           Parent root = loader.load();
           
           Scene scene = new Scene(root);
           primaryStage.setTitle("SPP Registrar Estudiante");
           //primaryStage.setTitle("SPP Registrar Organización");
           //primaryStage.setTitle("SPP Inactivar Profesor");
           primaryStage.setScene(scene);
           primaryStage.setResizable(false);
           primaryStage.show();
       } catch (IOException e) {
           LOGGER.log(Level.SEVERE, "Error al iniciar la aplicación de registro", e);
       }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}