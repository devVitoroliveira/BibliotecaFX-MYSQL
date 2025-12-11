package biblioteca.fx.Controller;

import java.io.IOException;

import biblioteca.fx.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class mainController {

    @FXML
    private Button autor;
    @FXML
    private Button cliente;
    @FXML
    private Button func;

    @FXML
    private void abrirTelaAutor() throws IOException {
        App.setRoot("autor");
    }

    @FXML
    private void abrirTelaCliente() throws IOException {
        App.setRoot("cliente");
    }

    @FXML
    private void abrirTelaFuncionario() throws IOException {
        App.setRoot("funcionario");
    }
}
