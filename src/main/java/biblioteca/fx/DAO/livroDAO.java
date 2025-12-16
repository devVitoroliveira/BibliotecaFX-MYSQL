package biblioteca.fx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import biblioteca.fx.DTO.livroDTO;
import javafx.fxml.FXML;

public class livroDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    @FXML
    public ResultSet listarLivros() throws SQLException {
        String sql = "select * from livro order by nomeLivro";
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
    public void addLivro(livroDTO l) throws SQLException {
        String sql = "insert into livro (idlivro, idAutor, nomeLivro, ano, genero, ibsn) values (?,?,?,?,?,?)";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, l.getIdLivro());
            pstm.setInt(2, l.getIdautor());
            pstm.setString(3, l.getNomeLivro());
            pstm.setString(4, l.getAno());
            pstm.setString(5, l.getGenero());
            pstm.setString(6, l.getIbsn());
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    @FXML
    public ArrayList<livroDTO> getLivros() throws SQLException {
        ArrayList<livroDTO> livros = new ArrayList<>();
        String sql = "select * from livro join autor a on a.idautor = livro.idautor join pessoa p on p.idPessoa = a.idautor";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                livroDTO livro = new livroDTO();
                livro.setIdLivro(rs.getInt("idLivro"));
                livro.setIdautor(rs.getInt("idAutor"));
                livro.setNomeLivro(rs.getString("nomeLivro"));
                livro.setNomeAutor(rs.getString("nomePessoa"));
                livro.setAno(rs.getString("ano"));
                livro.setGenero(rs.getString("genero"));
                livro.setIbsn(rs.getString("ibsn"));
                livros.add(livro);
            }
        } catch (SQLException e) {
            throw e;
        }
        return livros;
    }

    @FXML
    public void updateLivro(livroDTO l) throws SQLException {
        String sql = "update livro l join autor a on a.idautor = l.idautor join pessoa p on p.idPessoa = a.idautor set l.idLivro = ?, l.idAutor = ?, l.nomeLivro = ?, l.ano = ?, l.genero = ?, l.ibsn = ? where l.idLivro = ?";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, l.getIdLivro());
            pstm.setInt(2, l.getIdautor());
            pstm.setString(3, l.getNomeLivro());
            pstm.setString(4, l.getAno());
            pstm.setString(5, l.getGenero());
            pstm.setString(6, l.getIbsn());
            pstm.setInt(7, l.getIdLivro());
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    @FXML
    public void deleteLivro(livroDTO l) throws SQLException {
        String sql = "delete from livro where idLivro = ?";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, l.getIdLivro());
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }
}
