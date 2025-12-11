package biblioteca.fx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import biblioteca.fx.DTO.funcionarioDTO;
import javafx.fxml.FXML;

public class funcionarioDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    @FXML
    public ResultSet listarCargo() throws SQLException {
        String sql = "SELECT * FROM cargo ORDER BY cargoNome";
        conn = new ConexaoDAO().conexaoBD();

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
        } catch (SQLException e) {
            throw e;
        }
        return rs;
    }

    @FXML
    public void addFuncionario(funcionarioDTO func) throws SQLException {
        pessoaDAO pessoaDAO = new pessoaDAO();
        int id_pessoa = pessoaDAO.addPessoa(func);

        if (id_pessoa == -1) {

        }
        func.setId_pessoa(id_pessoa);
        String sql = "INSERT INTO funcionario (idfuncionario, idCargo, salario, emailFunc, foneFunc) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, func.getId_pessoa());
            pstm.setInt(2, func.getId_cargo());
            pstm.setDouble(3, func.getSalario());
            pstm.setString(4, func.getEmail());
            pstm.setString(5, func.getTelefone());
            pstm.execute();
            rs = pstm.getGeneratedKeys();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    @FXML
    public ArrayList<funcionarioDTO> getFuncionarios() throws SQLException {
        ArrayList<funcionarioDTO> funcionarios = new ArrayList<>();
        String sql = "select f.idfuncionario, p.idPessoa, p.nomePessoa, f.foneFunc, f.emailFunc, f.salario, f.idCargo, c.cargoNome from funcionario f join pessoa p on p.idPessoa = f.idfuncionario join cargo c on c.idCargo = f.idCargo";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                funcionarioDTO func = new funcionarioDTO();
                func.setId_pessoa(rs.getInt("idPessoa"));
                func.setNome(rs.getString("nomePessoa"));
                func.setTelefone(rs.getString("foneFunc"));
                func.setEmail(rs.getString("emailFunc"));
                func.setSalario(rs.getDouble("salario"));
                func.setId_cargo(rs.getInt("idCargo"));
                func.setCargo(rs.getString("cargoNome"));
                funcionarios.add(func);
            }
        } catch (SQLException e) {
            throw e;
        }
        return funcionarios;
    }

    @FXML
    public void updateFuncionario(funcionarioDTO func) throws SQLException {
        String sql = "UPDATE funcionario f join pessoa p on p.idPessoa = f.idfuncionario join cargo c on c.idCargo = f.idCargo SET p.nomePessoa = ?,  f.idCargo = ?, f.salario = ?, f.emailFunc = ?, f.foneFunc = ? WHERE idfuncionario = ?";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, func.getNome());
            pstm.setInt(2, func.getId_cargo());
            pstm.setDouble(3, func.getSalario());
            pstm.setString(4, func.getEmail());
            pstm.setString(5, func.getTelefone());
            pstm.setInt(6, func.getId_pessoa());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    @FXML
    public void deleteFuncionario(funcionarioDTO func) throws SQLException {
        String sql = "DELETE FROM funcionario WHERE idfuncionario = ?";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, func.getId_pessoa());
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }
}
