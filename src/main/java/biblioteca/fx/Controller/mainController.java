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
    private Button emprestimoButton;

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

    @FXML
    private void abrirTelaEmp() throws IOException {
        App.setRoot("emprestimo");
    }

    @FXML
    private void abrirTelaLivro() throws IOException {
        App.setRoot("livro");
    }
}
