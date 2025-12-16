package biblioteca.fx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class ConexaoDAO {
    Connection conn;
    PreparedStatement pstm;

    @FXML
    public Connection conexaoBD() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/biblioteca?user=root&password=admin";
        try {
            conn = java.sql.DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro na conexaoBD");
            alert.setContentText("Erro na conexaoBD: " + e.getMessage());
            alert.showAndWait();
        }
        return null;
    }

}
