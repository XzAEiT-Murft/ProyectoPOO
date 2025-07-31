package views;

import controllers.PacienteValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Hospital;
import models.Paciente;
import utils.JPAUtil;

public class PacienteView {

    public static void mostrar(Stage stage, ObservableList<Hospital> hospitales) {
        // Formulario
        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField();

        Label lblApellido1 = new Label("Primer Apellido:");
        TextField txtApellido1 = new TextField();

        Label lblApellido2 = new Label("Segundo Apellido:");
        TextField txtApellido2 = new TextField();

        Label lblEdad = new Label("Edad:");
        TextField txtEdad = new TextField();

        Label lblHospital = new Label("Hospital:");
        ComboBox<Hospital> comboHospital = new ComboBox<>(hospitales);
        comboHospital.setPromptText("Selecciona un hospital");

        Label lblGenero = new Label("Género:");
        ComboBox<String> comboGenero = new ComboBox<>();
        comboGenero.getItems().addAll("Masculino", "Femenino", "Otro");
        comboGenero.setPromptText("Selecciona un género");

        Label lblMensaje = new Label();

        Button btnGuardar = new Button("Guardar");
        Button btnRegresar = new Button("Regresar");

        // Tabla de pacientes
        TableView<Paciente> tablaPacientes = new TableView<>();
        ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();
        cargarPacientesDesdeBD(listaPacientes);

        TableColumn<Paciente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombrePila"));

        TableColumn<Paciente, String> colApellido1 = new TableColumn<>("Apellido P.");
        colApellido1.setCellValueFactory(new PropertyValueFactory<>("apellido1"));

        TableColumn<Paciente, Integer> colEdad = new TableColumn<>("Edad");
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        TableColumn<Paciente, String> colGenero = new TableColumn<>("Género");
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));

        tablaPacientes.getColumns().addAll(colNombre, colApellido1, colEdad, colGenero);
        tablaPacientes.setItems(listaPacientes);
        tablaPacientes.setPrefHeight(200);

        // Botón guardar
        btnGuardar.setOnAction(e -> {
            String nombre = txtNombre.getText();
            String apellido1 = txtApellido1.getText();
            String apellido2 = txtApellido2.getText();
            String genero = comboGenero.getValue();
            String edadTexto = txtEdad.getText();

            int edad;
            try {
                edad = Integer.parseInt(edadTexto);
            } catch (NumberFormatException ex) {
                lblMensaje.setText("Edad inválida.");
                return;
            }

            Hospital hospital = comboHospital.getValue();
            if (hospital == null) {
                lblMensaje.setText("Selecciona un hospital.");
                return;
            }

            Paciente paciente = new Paciente(nombre, apellido1, apellido2, edad, genero, hospital);

            EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(paciente);
                tx.commit();
                lblMensaje.setText("Paciente registrado correctamente.");
                listaPacientes.add(paciente); // Agregar a la tabla
            } catch (Exception ex) {
                if (tx.isActive()) tx.rollback();
                lblMensaje.setText("Error al registrar: " + ex.getMessage());
            } finally {
                em.close();
            }
        });

        btnRegresar.setOnAction(e -> MainView.mostrar(stage));

        VBox root = new VBox(10,
                lblNombre, txtNombre,
                lblApellido1, txtApellido1,
                lblApellido2, txtApellido2,
                lblEdad, txtEdad,
                lblGenero, comboGenero,
                lblHospital, comboHospital,
                btnGuardar, btnRegresar,
                lblMensaje,
                new Label("Pacientes registrados:"),
                tablaPacientes
        );
        root.setPadding(new Insets(20));
        root.getStyleClass().add("formulario");

        Scene scene = new Scene(root, 500, 800);
        scene.getStylesheets().add(PacienteView.class.getResource("/styles/paciente.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Registrar Paciente");
    }

    private static void cargarPacientesDesdeBD(ObservableList<Paciente> lista) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Paciente> query = em.createQuery("SELECT p FROM Paciente p", Paciente.class);
            lista.setAll(query.getResultList());
        } finally {
            em.close();
        }
    }
}
