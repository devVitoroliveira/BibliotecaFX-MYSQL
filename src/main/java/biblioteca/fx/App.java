package biblioteca.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Image appIcon;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("connect"), 650, 450);
        scene.getStylesheets().add(App.class.getResource("styles.css").toExternalForm());
        stage.setTitle("Biblioteca - Sistema de Gestão");
        stage.setScene(scene);
        stage.setMinWidth(650);
        stage.setMinHeight(450);
        stage.setMaximized(true);

        // Carrega o ícone do arquivo PNG
        appIcon = new Image(App.class.getResourceAsStream("biblioteca-icon.png"));
        stage.getIcons().add(appIcon);

        stage.show();
    }

    /**
     * Retorna a imagem do ícone da aplicação para uso em Alerts e Dialogs
     */
    public static Image getAppIcon() {
        if (appIcon == null) {
            appIcon = new Image(App.class.getResourceAsStream("biblioteca-icon.png"));
        }
        return appIcon;
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}