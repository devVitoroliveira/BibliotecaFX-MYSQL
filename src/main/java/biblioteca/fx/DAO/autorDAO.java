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
            pstm.setString(3, autor.getPeriodoVida());
            pstm.setString(4, autor.getPeriodoFim());
            pstm.execute();
            rs = pstm.getGeneratedKeys();
            pstm.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao adicionar autor");
            alert.setContentText("Erro ao adicionar autor: " + e.getMessage());
            alert.showAndWait();
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
                autor.setPeriodoVida(rs.getString("dataInicio"));
                autor.setPeriodoFim(rs.getString("dataFim"));
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
        String sql = "UPDATE autor SET nacionalidade = ?, dataInicio = ?, dataFim = ? WHERE idautor = ?";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, autor.getNacionalidade());
            pstm.setString(2, autor.getPeriodoVida());
            pstm.setString(3, autor.getPeriodoFim());
            pstm.setInt(4, autor.getId_pessoa());

            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao atualizar autor");
            alert.setContentText("Erro ao atualizar autor: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
