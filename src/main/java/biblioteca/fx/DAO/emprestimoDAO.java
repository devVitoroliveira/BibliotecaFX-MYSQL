package biblioteca.fx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import biblioteca.fx.DTO.emprestimoDTO;
import javafx.fxml.FXML;

public class emprestimoDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    @FXML
    public void addEmprestimo(emprestimoDTO emprestimo) throws SQLException {
        String sql = "insert into emprestimo (idCliente, idLivro, dataInicio, dataFim) values (?,?,?,?)";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, emprestimo.getId_cliente());
            pstm.setInt(2, emprestimo.getId_livro());
            pstm.setDate(3,
                    emprestimo.getData_emprestimo() != null ? java.sql.Date.valueOf(emprestimo.getData_emprestimo())
                            : null);
            pstm.setDate(4,
                    emprestimo.getData_devolucao() != null ? java.sql.Date.valueOf(emprestimo.getData_devolucao())
                            : null);
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    @FXML
    public ArrayList<emprestimoDTO> getEmprestimos() throws SQLException {
        ArrayList<emprestimoDTO> emprestimos = new ArrayList<>();
        String sql = "select e.idEmprestimo, c.idCliente, l.idLivro, p.idPessoa, p.nomePessoa, l.nomeLivro, e.dataInicio, e.dataFim from emprestimo e join cliente c on c.idCliente = e.idCliente join livro l on l.idLivro = e.idLivro join pessoa p on p.idPessoa = c.idCliente";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                emprestimoDTO emprestimo = new emprestimoDTO();
                emprestimo.setId_emprestimo(rs.getInt("idEmprestimo"));
                emprestimo.setId_cliente(rs.getInt("idCliente"));
                emprestimo.setId_livro(rs.getInt("idLivro"));
                emprestimo.setNomeCliente(rs.getString("nomePessoa"));
                emprestimo.setNomeLivro(rs.getString("nomeLivro"));
                emprestimo.setData_emprestimo(
                        rs.getString("dataInicio") != null ? rs.getDate("dataInicio").toLocalDate() : null);
                emprestimo.setData_devolucao(
                        rs.getString("dataFim") != null ? rs.getDate("dataFim").toLocalDate() : null);
                emprestimos.add(emprestimo);
            }
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
        return emprestimos;
    }

    @FXML
    public void updateEmprestimo(emprestimoDTO emprestimo) throws SQLException {
        String sql = "UPDATE emprestimo SET idLivro = ?, dataInicio = ?, dataFim = ? WHERE idEmprestimo = ?";

        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, emprestimo.getId_livro());
            pstm.setDate(2,
                    emprestimo.getData_emprestimo() != null ? java.sql.Date.valueOf(emprestimo.getData_emprestimo())
                            : null);
            pstm.setDate(3,
                    emprestimo.getData_devolucao() != null ? java.sql.Date.valueOf(emprestimo.getData_devolucao())
                            : null);
            pstm.setInt(4, emprestimo.getId_emprestimo());
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    @FXML
    public void deleteEmprestimo(emprestimoDTO emprestimo) throws SQLException {
        String sql = "delete from emprestimo where idEmprestimo = ?";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, emprestimo.getId_emprestimo());
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }
}
