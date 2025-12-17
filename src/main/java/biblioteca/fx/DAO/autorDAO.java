package biblioteca.fx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import biblioteca.fx.DTO.autorDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class autorDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    @FXML
    public ResultSet listarAutores() throws SQLException {
        String sql = "select * from autor join pessoa p on p.idPessoa = autor.idautor";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
        } catch (SQLException e) {
            throw e;
        }
        return rs;
    }

    @FXML
    public void addAutor(autorDTO autor) throws SQLException {
        pessoaDAO pessoaDAO = new pessoaDAO();
        int id_pessoa = pessoaDAO.addPessoa(autor);

        if (id_pessoa == -1) {

        }
        autor.setId_pessoa(id_pessoa);
        String sql = "INSERT INTO autor (idautor, nacionalidade, dataInicio, dataFim) VALUES (?, ?, ?, ?)";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, autor.getId_pessoa());
            pstm.setString(2, autor.getNacionalidade());
            pstm.setDate(3, autor.getPeriodoVida() != null ? java.sql.Date.valueOf(autor.getPeriodoVida()) : null);
            pstm.setDate(4, autor.getPeriodoFim() != null ? java.sql.Date.valueOf(autor.getPeriodoFim()) : null);
            pstm.execute();
            rs = pstm.getGeneratedKeys();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    public ArrayList<autorDTO> getAutores() throws SQLException {
        ArrayList<autorDTO> autores = new ArrayList<>();
        String sql = "select * from autor join pessoa p on p.idPessoa = autor.idautor";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                autorDTO autor = new autorDTO();
                autor.setId_pessoa(rs.getInt("idPessoa"));
                autor.setNome(rs.getString("nomePessoa"));
                autor.setNacionalidade(rs.getString("nacionalidade"));
                autor.setPeriodoVida(rs.getDate("dataInicio") != null ? rs.getDate("dataInicio").toLocalDate() : null);
                autor.setPeriodoFim(rs.getString("dataFim") != null ? rs.getDate("dataFim").toLocalDate() : null);
                autores.add(autor);
            }
            pstm.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao buscar autores");
            alert.setContentText("Erro ao buscar autores: " + e.getMessage());
            alert.showAndWait();
        }
        return autores;
    }

    @FXML
    public void updateAutor(autorDTO autor) throws SQLException {
        String sql = "UPDATE autor a JOIN pessoa p ON p.idPessoa = a.idautor SET p.nomePessoa = ?, a.nacionalidade = ?, a.dataInicio = ?, a.dataFim = ? WHERE a.idautor = ?";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, autor.getNome());
            pstm.setString(2, autor.getNacionalidade());
            pstm.setDate(3, autor.getPeriodoVida() != null ? java.sql.Date.valueOf(autor.getPeriodoVida()) : null);
            pstm.setDate(4, autor.getPeriodoFim() != null ? java.sql.Date.valueOf(autor.getPeriodoFim() + "") : null);
            pstm.setInt(5, autor.getId_pessoa());

            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao atualizar autor");
            alert.setContentText("Erro ao atualizar autor: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void deleteAutor(autorDTO autor) throws SQLException {
        String sql = "delete from pessoa where idPessoa = ?";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, autor.getId_pessoa());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }
}
