package biblioteca.fx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import biblioteca.fx.DTO.userDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class userDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    @FXML
    public ResultSet autenticarUsuario(userDTO user) {
        String sql = "SELECT * FROM user WHERE userName = ? AND userPassword = ?";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getPassword());
            rs = pstm.executeQuery();
            return rs;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Connection Error");
            alert.setHeaderText("Database Connection Error");
            alert.setContentText("Error in autenticarUsuario: " + e.getMessage());
            alert.showAndWait();
            return null;
        }
    }
}
