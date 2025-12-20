package biblioteca.fx.DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

import biblioteca.fx.DTO.Pessoa;
import javafx.fxml.FXML;

public class pessoaDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    @FXML
    public int addPessoa(Pessoa p) throws SQLException {
        String sql = "INSERT INTO pessoa (nomePessoa) VALUES (?)";
        conn = new ConexaoDAO().conexaoBD();
        pstm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        pstm.setString(1, p.getNome());
        pstm.execute();
        ResultSet rs = pstm.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }
}
