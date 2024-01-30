package es.ieslosmontecillos.appinformes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author profesora
 */
public class AppInformes extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            VBox root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("app-informes-view.fxml"));
            AppInformesController controller = loader.getController();
            root = loader.load();

            Scene scene = new Scene(root, 300, 250);

            primaryStage.setTitle("AppInformes");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex){
            System.err.println("ERROR AL CARGAR LA APLICACIÓN");
        }
    }
    @Override
    public void stop() throws Exception {
        try {
            DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001/test/test;shutdown=true");
        } catch (Exception ex) {
            System.out.println("No se pudo cerrar la conexion a la BD");
            ex.printStackTrace();
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}