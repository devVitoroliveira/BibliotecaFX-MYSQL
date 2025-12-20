package biblioteca.fx.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import biblioteca.fx.App;
import biblioteca.fx.DAO.clienteDAO;
import biblioteca.fx.DTO.clienteDTO;
import biblioteca.fx.util.AlertUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class clienteController {

    @FXML
    private TextField nomeClienteField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField telefoneField;
    @FXML
    private Button salvarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private Button listarButton;
    @FXML
    private Button atualizarButton;
    @FXML
    private Button excluirButton;
    @FXML
    private Button voltarButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<clienteDTO> clienteTableView;
    @FXML
    private TableColumn<clienteDTO, Integer> clienteId;
    @FXML
    private TableColumn<clienteDTO, String> clienteNome;
    @FXML
    private TableColumn<clienteDTO, String> clienteTelefone;
    @FXML
    private TableColumn<clienteDTO, String> clienteEmail;

    @FXML
    public void initialize() throws SQLException {
        clienteId.setCellValueFactory(new PropertyValueFactory<>("id_pessoa"));
        clienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        clienteTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        clienteTelefone.setCellFactory(col -> new TableCell<clienteDTO, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.replaceFirst("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3"));
                }
            }
        });
        clienteEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clienteDAO clienteDAO = new clienteDAO();
        clienteDAO.getClientes();
        ArrayList<clienteDTO> clientes = clienteDAO.getClientes();
        ObservableList<clienteDTO> clienteList = javafx.collections.FXCollections.observableArrayList(clientes);
        clienteTableView.setItems(clienteList);
        clienteTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nomeClienteField.setText(newSelection.getNome());
                telefoneField.setText(newSelection.getTelefone());
                emailField.setText(newSelection.getEmail());
            }
        });
    }

    @FXML
    private void salvarButton() throws SQLException {
        try {
            String nome, telefone, email;
            nome = nomeClienteField.getText();
            telefone = telefoneField.getText();
            email = emailField.getText();
            if (!nome.isEmpty() && !telefone.isEmpty() && !email.isEmpty()) {
                if (!nome.matches("[a-zA-Z ]+")) {
                    AlertUtils
                            .aviso("Erro", "Nome inválido",
                                    "O nome deve conter apenas letras e espaços.")
                            .showAndWait();
                    return;
                }
                clienteDTO clienteDTO = new clienteDTO();
                clienteDTO.setNome(nome);
                clienteDTO.setTelefone(telefone);
                clienteDTO.setEmail(email);
                clienteDAO clienteDAO = new clienteDAO();
                clienteDAO.addCliente(clienteDTO);
                AlertUtils.info("Sucesso", "Cliente Cadastrado", "Cliente cadastrado com sucesso.").showAndWait();
                nomeClienteField.clear();
                telefoneField.clear();
                emailField.clear();
                listarButton();
            } else {
                AlertUtils.erro("Erro", "Campos em Branco", "Por favor, preencha todos os campos.").showAndWait();
            }
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao cadastrar cliente", "Erro ao cadastrar cliente: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void listarButton() throws SQLException {
        try {
            clienteDAO clienteDAO = new clienteDAO();
            ArrayList<clienteDTO> clientes = clienteDAO.getClientes();
            ObservableList<clienteDTO> clientelist = javafx.collections.FXCollections.observableArrayList(clientes);
            clienteTableView.setItems(clientelist);
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao listar clientes", "Erro ao listar clientes: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void updateButton() throws SQLException {
        int selectedIndex = clienteTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            clienteDTO cliente = clienteTableView.getSelectionModel().getSelectedItem();
            cliente.setNome(nomeClienteField.getText());
            cliente.setTelefone(telefoneField.getText());
            cliente.setEmail(emailField.getText());
            try {
                if (!nomeClienteField.getText().isEmpty() && !telefoneField.getText().isEmpty()
                        && !emailField.getText().isEmpty()) {
                    if (!nomeClienteField.getText().matches("[a-zA-Z ]+")) {
                        AlertUtils
                                .aviso("Erro", "Nome inválido",
                                        "O nome deve conter apenas letras e espaços.")
                                .showAndWait();
                        return;
                    }
                    clienteDAO clienteDAO = new clienteDAO();
                    clienteDAO.updateCliente(cliente);
                    AlertUtils.info("Sucesso", "Cliente atualizado", "Cliente atualizado com sucesso.").showAndWait();
                    cancelarButton();
                    listarButton();
                } else {
                    AlertUtils.erro("Erro", "Campos em Branco", "Por favor, preencha todos os campos.").showAndWait();
                }
            } catch (SQLException e) {
                AlertUtils.erro("Erro", "Erro ao atualizar cliente", "Erro ao atualizar cliente: " + e.getMessage())
                        .showAndWait();
            }
        } else {
            AlertUtils.aviso("Aviso", "Nenhum Cliente Selecionado", "Por favor, selecione um cliente para atualizar.")
                    .showAndWait();
        }
    }

    @FXML
    private void deleteButton() throws SQLException {
        int selectedIndex = clienteTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            clienteDTO cliente = clienteTableView.getSelectionModel().getSelectedItem();
            try {
                clienteDAO clienteDAO = new clienteDAO();
                clienteDAO.deleteCliente(cliente);
                AlertUtils.info("Sucesso", "Cliente excluido", "Cliente excluido com sucesso.").showAndWait();
                cancelarButton();
                listarButton();
            } catch (SQLException e) {
                AlertUtils.erro("Erro", "Erro ao excluir cliente", "Erro ao excluir cliente: " + e.getMessage())
                        .showAndWait();
            }
        } else {
            AlertUtils.aviso("Aviso", "Nenhum Cliente Selecionado", "Por favor, selecione um cliente para excluir.")
                    .showAndWait();
        }
    }

    @FXML
    private void voltarButton() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void cancelarButton() {
        nomeClienteField.clear();
        telefoneField.clear();
        emailField.clear();
        clienteTableView.getSelectionModel().clearSelection();
    }
}
