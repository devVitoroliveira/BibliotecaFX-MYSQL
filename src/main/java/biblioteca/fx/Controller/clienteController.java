package biblioteca.fx.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import biblioteca.fx.App;
import biblioteca.fx.DAO.clienteDAO;
import biblioteca.fx.DTO.clienteDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
                clienteDTO clienteDTO = new clienteDTO();
                clienteDTO.setNome(nome);
                clienteDTO.setTelefone(telefone);
                clienteDTO.setEmail(email);
                clienteDAO clienteDAO = new clienteDAO();
                clienteDAO.addCliente(clienteDTO);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Client Added");
                alert.setContentText("The client was added successfully.");
                alert.showAndWait();
                nomeClienteField.clear();
                telefoneField.clear();
                emailField.clear();
                listarButton();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Missing Information");
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Client");
            alert.setContentText("There was an error adding the client: " + e.getMessage());
            alert.showAndWait();
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Listing Clients");
            alert.setContentText("There was an error listing the clients: " + e.getMessage());
            alert.showAndWait();
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
                    clienteDAO clienteDAO = new clienteDAO();
                    clienteDAO.updateCliente(cliente);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Client Updated");
                    alert.setContentText("The client was updated successfully.");
                    alert.showAndWait();
                    listarButton();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Incomplete Data");
                    alert.setContentText("Please fill in all required fields.");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Updating Client");
                alert.setContentText("There was an error updating the client: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Client Selected");
            alert.setContentText("Please select a client in the table.");
            alert.showAndWait();
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Client Deleted");
                alert.setContentText("The client was deleted successfully.");
                alert.showAndWait();
                cancelarButton();
                listarButton();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Deleting Client");
                alert.setContentText("There was an error deleting the client: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Client Selected");
            alert.setContentText("Please select a client in the table.");
            alert.showAndWait();
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
