package biblioteca.fx;

import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

import biblioteca.fx.DAO.ConexaoDAO;
import biblioteca.fx.DAO.userDAO;
import biblioteca.fx.DTO.userDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Connection Error");
            alert.setHeaderText("Database Connection Error");
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void login() {
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setHeaderText("Login Successful");
                alert.setContentText("Welcome, " + name + "!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Login Failed");
                alert.setContentText("Invalid username or password.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Database Error");
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
