package biblioteca.fx.Controller;

import java.io.IOException;

import biblioteca.fx.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class mainController {

    @FXML
    private Button autor;

    @FXML
    private void abrirTelaAutor() throws IOException {
        App.setRoot("autor");
    }
}
