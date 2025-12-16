package biblioteca.fx.Controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import biblioteca.fx.App;
import biblioteca.fx.DAO.funcionarioDAO;
import biblioteca.fx.DTO.funcionarioDTO;
import biblioteca.fx.util.AlertUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class funcionarioController {

    @FXML
    private TextField nomeFuncionarioField;
    @FXML
    private TextField telefoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField salarioField;
    @FXML
    private ComboBox<String> cargoComboBox;
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
    private TableView<funcionarioDTO> funcionarioTableView;
    @FXML
    private TableColumn<funcionarioDTO, Integer> funcionarioId;
    @FXML
    private TableColumn<funcionarioDTO, String> funcionarioNome;
    @FXML
    private TableColumn<funcionarioDTO, String> funcionarioTelefone;
    @FXML
    private TableColumn<funcionarioDTO, String> funcionarioEmail;
    @FXML
    private TableColumn<funcionarioDTO, Double> funcionarioSalario;
    @FXML
    private TableColumn<funcionarioDTO, String> funcionarioCargo;

    @FXML
    public void initialize() throws SQLException {

        funcionarioId.setCellValueFactory(new PropertyValueFactory<>("id_pessoa"));
        funcionarioNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        funcionarioTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        funcionarioEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        funcionarioSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        funcionarioCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        funcionarioDAO funcionarioDAO = new funcionarioDAO();
        funcionarioDAO.getFuncionarios();
        carregarBox();
        ArrayList<funcionarioDTO> funcionarios = funcionarioDAO.getFuncionarios();
        ObservableList<funcionarioDTO> funcionarioList = javafx.collections.FXCollections
                .observableArrayList(funcionarios);
        funcionarioTableView.setItems(funcionarioList);
        funcionarioTableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        nomeFuncionarioField.setText(newSelection.getNome());
                        telefoneField.setText(newSelection.getTelefone());
                        emailField.setText(newSelection.getEmail());
                        salarioField.setText(String.valueOf(newSelection.getSalario()));
                        int idx = Id.indexOf(newSelection.getId_cargo());
                        if (idx >= 0 && idx < cargoComboBox.getItems().size()) {
                            cargoComboBox.getSelectionModel().select(idx);
                        } else {
                            cargoComboBox.setValue(null);
                        }
                    }
                });
    }

    Vector<Integer> Id = new Vector<>();

    @FXML
    public void carregarBox() throws SQLException {
        try {
            cargoComboBox.getItems().clear();
            Id.clear();
            funcionarioDAO f = new funcionarioDAO();
            ResultSet rs = f.listarCargo();
            while (rs.next()) {
                Id.addElement(rs.getInt(1));
                cargoComboBox.getItems().add(rs.getString(2));
            }
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao carregar box", "Erro ao carregar box: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void salvarButton() throws SQLException {
        try {
            String nome, telefone, email;
            double salario;
            nome = nomeFuncionarioField.getText();
            telefone = telefoneField.getText();
            email = emailField.getText();
            salario = Double.parseDouble(salarioField.getText());
            int cargoIndex = cargoComboBox.getSelectionModel().getSelectedIndex();

            funcionarioDTO funcionario = new funcionarioDTO();
            funcionario.setNome(nome);
            funcionario.setTelefone(telefone);
            funcionario.setEmail(email);
            funcionario.setSalario(salario);
            if (cargoIndex < 0) {
                AlertUtils.aviso("Aviso", "Campos em branco", "Por favor, selecione um cargo.").showAndWait();
                return;
            }

            funcionario.setId_cargo(Id.get(cargoIndex));

            if (!nome.isEmpty() && !telefone.isEmpty() && !email.isEmpty()) {
                funcionarioDAO funcionarioDAO = new funcionarioDAO();
                funcionarioDAO.addFuncionario(funcionario);

                AlertUtils.info("Sucesso", "Funcionário adicionado", "Funcionário adicionado com sucesso!")
                        .showAndWait();
                nomeFuncionarioField.clear();
                telefoneField.clear();
                emailField.clear();
                salarioField.clear();
                listarButton.fire();
            } else {
                AlertUtils.aviso("Aviso", "Campos em branco", "Por favor, preencha todos os campos.").showAndWait();
            }

        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao adicionar funcionário", "Erro ao adicionar funcionário: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void listarButton() throws SQLException {
        try {
            funcionarioDAO funcionarioDAO = new funcionarioDAO();
            ArrayList<funcionarioDTO> funcionarios = funcionarioDAO.getFuncionarios();
            ObservableList<funcionarioDTO> funcionariolist = javafx.collections.FXCollections
                    .observableArrayList(funcionarios);
            funcionarioTableView.setItems(funcionariolist);
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao listar funcionários", "Erro ao listar funcionários: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void atualizarButton() throws SQLException {
        try {
            int selectedIndex = funcionarioTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                funcionarioDTO funcionario = funcionarioTableView.getSelectionModel().getSelectedItem();
                funcionario.setNome(nomeFuncionarioField.getText());
                funcionario.setTelefone(telefoneField.getText());
                funcionario.setEmail(emailField.getText());
                funcionario.setSalario(Double.parseDouble(salarioField.getText()));
                int cargoIndex = cargoComboBox.getSelectionModel().getSelectedIndex();
                if (cargoIndex >= 0) {
                    funcionario.setId_cargo(Id.get(cargoIndex));
                }

                if (!nomeFuncionarioField.getText().isEmpty() && !telefoneField.getText().isEmpty()
                        && !emailField.getText().isEmpty() && cargoIndex >= 0) {

                    funcionarioDAO funcionarioDAO = new funcionarioDAO();
                    funcionarioDAO.updateFuncionario(funcionario);
                    AlertUtils.info("Sucesso", "Funcionário atualizado", "Funcionário atualizado com sucesso!")
                            .showAndWait();
                    listarButton();
                }
            } else {
                AlertUtils.aviso("Aviso", "Nenhum funcionário selecionado",
                        "Por favor, selecione um funcionário para atualizar.").showAndWait();
            }
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao atualizar funcionário", "Erro ao atualizar funcionário: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void excluirButton() throws SQLException {
        try {
            int selectedIndex = funcionarioTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                funcionarioDTO funcionario = funcionarioTableView.getSelectionModel().getSelectedItem();

                funcionarioDAO funcionarioDAO = new funcionarioDAO();
                funcionarioDAO.deleteFuncionario(funcionario);
                AlertUtils.info("Sucesso", "Funcionário excluído", "Funcionário excluído com sucesso!").showAndWait();
                listarButton();
            } else {
                AlertUtils.aviso("Aviso", "Nenhum funcionário selecionado",
                        "Por favor, selecione um funcionário para excluir.").showAndWait();
            }
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao excluir funcionário", "Erro ao excluir funcionário: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void cancelarButton() {
        nomeFuncionarioField.clear();
        telefoneField.clear();
        emailField.clear();
        salarioField.clear();
        cargoComboBox.setValue(null);
    }

    @FXML
    private void voltarButton() throws IOException {
        App.setRoot("main");
    }
}
