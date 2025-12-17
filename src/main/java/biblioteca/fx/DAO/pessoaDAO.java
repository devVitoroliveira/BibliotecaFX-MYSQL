package biblioteca.fx.DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import biblioteca.fx.DTO.Pessoa;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class pessoaDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    @FXML
    public int addPessoa(Pessoa p) {
        String sql = "INSERT INTO pessoa (nomePessoa) VALUES (?)";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            pstm.setString(1, p.getNome());
            pstm.execute();
            ResultSet rs = pstm.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao adicionar pessoa");
            alert.setContentText("Erro ao adicionar pessoa: " + e.getMessage());
            alert.showAndWait();
        }
        return -1;
    }
}
