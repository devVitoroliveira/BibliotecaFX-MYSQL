package biblioteca.fx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import biblioteca.fx.DTO.clienteDTO;
import javafx.fxml.FXML;

public class clienteDAO {

    Connection conn;
    ResultSet rs;
    PreparedStatement pstm;

    @FXML
    public void addCliente(clienteDTO cliente) throws SQLException {
        pessoaDAO pessoaDAO = new pessoaDAO();
        int id_pessoa = pessoaDAO.addPessoa(cliente);

        if (id_pessoa == -1) {

        }
        cliente.setId_pessoa(id_pessoa);
        String sql = "INSERT INTO cliente (idcliente, telefone, email) VALUES (?, ?, ?)";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, cliente.getId_pessoa());
            pstm.setString(2, cliente.getTelefone());
            pstm.setString(3, cliente.getEmail());
            pstm.execute();
            rs = pstm.getGeneratedKeys();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    @FXML
    public ArrayList<clienteDTO> getClientes() throws SQLException {
        ArrayList<clienteDTO> clientes = new ArrayList<>();
        String sql = "select * from cliente join pessoa p on p.idPessoa = cliente.idcliente";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                clienteDTO cliente = new clienteDTO();
                cliente.setId_pessoa(rs.getInt("idPessoa"));
                cliente.setNome(rs.getString("nomePessoa"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw e;
        }
        return clientes;
    }

    @FXML
    public void updateCliente(clienteDTO cliente) throws SQLException {
        String sql = "UPDATE cliente c JOIN pessoa p ON p.idPessoa = c.idcliente SET p.nomePessoa = ?, c.telefone = ?, c.email = ? WHERE c.idcliente = ?";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getTelefone());
            pstm.setString(3, cliente.getEmail());
            pstm.setInt(4, cliente.getId_pessoa());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    @FXML
    public void deleteCliente(clienteDTO cliente) throws SQLException {
        String sql = "DELETE FROM cliente WHERE idcliente = ?";
        try {
            conn = new ConexaoDAO().conexaoBD();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, cliente.getId_pessoa());
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw e;
        }
    }
}
