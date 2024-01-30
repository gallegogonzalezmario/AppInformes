package es.ieslosmontecillos.appinformes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AppInformesController implements Initializable {

    @FXML
    private MenuItem listaFacturas;
    @FXML
    private MenuItem ventasTotales;
    @FXML
    private MenuItem facturasCliente;
    @FXML
    private MenuItem subListaFacturas;
    @FXML
    private Label lblInforme;
    @FXML
    private TextField textFieldInforme;
    @FXML
    private Button btInforme;
    private Connection conexion = null;

    // OnAction MenuItems
    // Listado Facturas
    @FXML
    public void onActionListaFacturas(ActionEvent actionEvent) {
        visibilidadContactoInforme(false);

        try {
            JasperReport jr =
                    (JasperReport) JRLoader.loadObject(getClass().getResource("Factura.jasper"));

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr,
                    null, conexion);
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    // Ventas totales
    @FXML
    public void onActionVentasTotales(ActionEvent actionEvent) {
        visibilidadContactoInforme(false);

        try {
            JasperReport jr =
                    (JasperReport) JRLoader.loadObject(getClass().getResource("Ventas_totales.jasper"));

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr,
                    null, conexion);
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    // Facturas por Cliente
    @FXML
    public void onActionFacturasCliente(ActionEvent actionEvent) {
        visibilidadContactoInforme(true);
    }

    // Botón facturas cliente
    @FXML
    public void onActionBtInforme(ActionEvent actionEvent) {
        try {

            JasperReport jr =
                        (JasperReport) JRLoader.loadObject(getClass().getResource("Facturas_cliente.jasper"));

            //Map de parámetros
            Map<String, Object> parametros = new HashMap();

            int nCliente = Integer.valueOf(textFieldInforme.getText());
            parametros.put("NUM_CLIENTE", nCliente);

            visibilidadContactoInforme(false);

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
            JasperViewer.viewReport(jp, false);

        } catch (NumberFormatException ex){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("ERROR");
            alerta.setHeaderText("FALLO AL GENERAR INFORME");
            alerta.setContentText("Solo se permiten valores numéricos.");
            alerta.showAndWait();

        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    // Listado Facturas con subinformes
    @FXML
    public void onActionSubListaFacturas(ActionEvent actionEvent) {
        visibilidadContactoInforme(false);

        try {
            JasperReport jr =
                    (JasperReport) JRLoader.loadObject(getClass().getResource("Informe_factura.jasper"));
            JasperReport jsr =
                    (JasperReport) JRLoader.loadObject((getClass().getResource("Subinforme_factura.jasper")));

            Map<String, Object> parametros = new HashMap();
            parametros.put("subInformeFactura", jsr);
            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr,
                    parametros, conexion);
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void visibilidadContactoInforme(boolean b){
        if(!textFieldInforme.isVisible())
            textFieldInforme.clear();

        textFieldInforme.setVisible(b);
        lblInforme.setVisible(b);
        btInforme.setVisible(b);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conectaBD();
    }

    private void conectaBD(){
        //Establecemos conexión con la BD
        String baseDatos = "jdbc:hsqldb:hsql://localhost:9001/test/test";
        String usuario = "sa";
        String clave = "";
        try{
            Class.forName("org.hsqldb.jdbc.JDBCDriver").newInstance();
            conexion = DriverManager.getConnection(baseDatos,usuario,clave);
        }
        catch (ClassNotFoundException cnfe){
            System.err.println("Fallo al cargar JDBC");
            cnfe.printStackTrace();
            System.exit(1);
        }
        catch (SQLException sqle){System.err.println("No se pudo conectar a BD");
            sqle.printStackTrace();
            System.exit(1);
        }
        catch (Exception ex){
            System.err.println("Imposible Conectar");
            ex.printStackTrace();
            System.exit(1);
        }
    }
}