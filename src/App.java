import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Label etiqueta = new Label("Hola desde JavaFX");
        Button boton = new Button("Haz clic aquí");

        boton.setOnAction(e -> etiqueta.setText("¡Hola, Luis! Has hecho clic."));

        VBox root = new VBox(10, etiqueta, boton);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Mi Primera App JavaFX en VS Code");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}