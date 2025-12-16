package biblioteca.fx.Controller;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import biblioteca.fx.App;
import biblioteca.fx.DAO.ConexaoDAO;
import biblioteca.fx.DAO.userDAO;
import biblioteca.fx.DTO.userDTO;
import biblioteca.fx.util.AlertUtils;
import javafx.fxml.FXML;

public class connectController {

    @FXML
    private TextField user;
    @FXML
    private TextField password;

    @FXML
    private void initialize() {
        try {
            ConexaoDAO conexaoDAO = new ConexaoDAO();
            conexaoDAO.conexaoBD();
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro de conexão com o banco de dados", "Erro: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void login() throws IOException {
        try {

            String name, pass;

            name = user.getText();
            pass = password.getText();

            userDTO user = new userDTO();
            user.setUsername(name);
            user.setPassword(pass);

            userDAO userDAO = new userDAO();
            ResultSet rs = userDAO.autenticarUsuario(user);

            if (rs.next()) {
                AlertUtils.info("Login bem-sucedido", "Login bem-sucedido", "Bem-vindo, " + name + "!").showAndWait();
                App.setRoot("main");
            } else {
                AlertUtils.erro("Login mal-sucedido", "Login mal-sucedido", "Usuario ou senha incorretos.")
                        .showAndWait();
            }
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro de autenticação", "Erro: " + e.getMessage()).showAndWait();
        }
    }
}
