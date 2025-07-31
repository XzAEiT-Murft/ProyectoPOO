package views;

import controllers.HospitalController;
import controllers.HospitalValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.Hospital;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class HospitalView {

    private static final ObservableList<Hospital> hospitales = FXCollections.observableArrayList();
    private static final HospitalController controller = new HospitalController();

    public static ObservableList<Hospital> getListaHospitales() {
        return hospitales;
    }

    public static void mostrar(Stage stage) {
        // Campos de entrada
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");

        TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Dirección");

        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");

        // Checkbox para mostrar solo activos
        CheckBox chkSoloActivos = new CheckBox("Mostrar solo activos");
        chkSoloActivos.setSelected(false); // Mostrar todos por defecto

        // Botones
        Button btnAgregar = new Button("Agregar");
        Button btnEliminar = new Button("Eliminar");
        Button btnBaja = new Button("Dar de Baja");
        Button btnReactivar = new Button("Reactivar");
        Button btnBuscar = new Button("Buscar");
        Button btnRegresar = new Button("Regresar");

        // Cargar hospitales desde la base de datos al iniciar
        recargarHospitalesDesdeBD();

        // Tabla
        TableView<Hospital> tabla = new TableView<>();
        tabla.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tabla.setItems(hospitales);

        TableColumn<Hospital, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Hospital, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Hospital, String> colDireccion = new TableColumn<>("Dirección");
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        TableColumn<Hospital, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<Hospital, LocalDate> colFechaAlta = new TableColumn<>("Fecha de Alta");
        colFechaAlta.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));

        TableColumn<Hospital, Boolean> colActivo = new TableColumn<>("Activo");
        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));

        tabla.getColumns().addAll(colId, colNombre, colDireccion, colTelefono, colFechaAlta, colActivo);

        // Funciones

        btnAgregar.setOnAction(e -> {
            Hospital h = new Hospital(
                    txtNombre.getText(),
                    txtDireccion.getText(),
                    txtTelefono.getText()
            );
            String error = HospitalValidator.validar(h);
            if (error != null && !error.isEmpty()) {
                mostrarAlerta("Error de validación", error);
                return;
            }
            controller.crearHospital(h); // Guarda en BD
            limpiarCampos(txtNombre, txtDireccion, txtTelefono);
            recargarHospitalesDesdeBD();
            actualizarTabla(tabla, chkSoloActivos.isSelected(), "");
        });

        btnEliminar.setOnAction(e -> {
            ObservableList<Hospital> seleccionados = tabla.getSelectionModel().getSelectedItems();
            if (seleccionados.isEmpty()) {
                mostrarAlerta("Sin selección", "Selecciona al menos un hospital para eliminar.");
                return;
            }
            controller.eliminarHospitales(seleccionados); // Borra en la BD
            recargarHospitalesDesdeBD();
            actualizarTabla(tabla, chkSoloActivos.isSelected(), txtNombre.getText());
        });

        btnBaja.setOnAction(e -> {
            ObservableList<Hospital> seleccionados = tabla.getSelectionModel().getSelectedItems();
            if (seleccionados.isEmpty()) {
                mostrarAlerta("Sin selección", "Selecciona al menos un hospital para dar de baja.");
                return;
            }
            for (Hospital h : seleccionados) {
                h.setActivo(false);
                controller.actualizarHospital(h);
            }
            recargarHospitalesDesdeBD();
            actualizarTabla(tabla, chkSoloActivos.isSelected(), txtNombre.getText());
        });

        btnReactivar.setOnAction(e -> {
            ObservableList<Hospital> seleccionados = tabla.getSelectionModel().getSelectedItems();
            if (seleccionados.isEmpty()) {
                mostrarAlerta("Sin selección", "Selecciona al menos un hospital para reactivar.");
                return;
            }
            for (Hospital h : seleccionados) {
                h.setActivo(true);
                controller.actualizarHospital(h);
            }
            recargarHospitalesDesdeBD();
            actualizarTabla(tabla, chkSoloActivos.isSelected(), txtNombre.getText());
        });

        btnBuscar.setOnAction(e -> {
            actualizarTabla(tabla, chkSoloActivos.isSelected(), txtNombre.getText());
        });

        chkSoloActivos.setOnAction(e -> {
            actualizarTabla(tabla, chkSoloActivos.isSelected(), txtNombre.getText());
        });

        btnRegresar.setOnAction(e -> MainView.mostrar(stage));

        // Layout
        VBox campos = new VBox(10, txtNombre, txtDireccion, txtTelefono, chkSoloActivos);
        HBox botones = new HBox(10, btnAgregar, btnBaja, btnReactivar, btnEliminar, btnBuscar, btnRegresar);
        VBox contenedor = new VBox(15, campos, botones, tabla);
        contenedor.setPadding(new Insets(20));
        contenedor.setAlignment(Pos.CENTER);

        Scene scene = new Scene(contenedor, 800, 500);
        scene.getStylesheets().add(HospitalView.class.getResource("/styles/hospital.css").toExternalForm());

        stage.setTitle("Gestión de Hospitales");
        stage.setScene(scene);
        stage.show();
    }

    private static void actualizarTabla(TableView<Hospital> tabla, boolean soloActivos, String filtro) {
        List<Hospital> filtrados = hospitales.stream()
                .filter(h -> (!soloActivos || h.isActivo()))
                .filter(h -> h.getNombre().toLowerCase().contains(filtro.toLowerCase()))
                .sorted((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()))
                .collect(Collectors.toList());
        tabla.setItems(FXCollections.observableArrayList(filtrados));
    }

    private static void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private static void limpiarCampos(TextField... campos) {
        for (TextField campo : campos) {
            campo.clear();
        }
    }

    private static void recargarHospitalesDesdeBD() {
        hospitales.clear();
        hospitales.addAll(controller.obtenerTodos());
    }
}