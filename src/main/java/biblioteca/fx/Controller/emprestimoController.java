package biblioteca.fx.Controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import biblioteca.fx.App;
import biblioteca.fx.DAO.clienteDAO;
import biblioteca.fx.DAO.emprestimoDAO;
import biblioteca.fx.DAO.livroDAO;
import biblioteca.fx.DTO.emprestimoDTO;
import biblioteca.fx.util.AlertUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class emprestimoController {

    @FXML
    private ComboBox<String> livroBox;
    @FXML
    private TextField inicioField;
    @FXML
    private TextField fimField;
    @FXML
    private ComboBox<String> emprestimoComboBox;
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
    private TableView<emprestimoDTO> emprestimoTableView;
    @FXML
    private TableColumn<emprestimoDTO, Integer> emprestimoId;
    @FXML
    private TableColumn<emprestimoDTO, String> emprestimoLivro;
    @FXML
    private TableColumn<emprestimoDTO, String> emprestimoCliente;
    @FXML
    private TableColumn<emprestimoDTO, String> emprestimoInicio;
    @FXML
    private TableColumn<emprestimoDTO, String> emprestimoFim;

    @FXML
    public void initialize() throws SQLException {
        emprestimoId.setCellValueFactory(new PropertyValueFactory<>("id_emprestimo"));
        emprestimoLivro.setCellValueFactory(new PropertyValueFactory<>("nomeLivro"));
        emprestimoCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        emprestimoInicio.setCellValueFactory(new PropertyValueFactory<>("data_emprestimo"));
        emprestimoFim.setCellValueFactory(new PropertyValueFactory<>("data_devolucao"));
        emprestimoDAO emprestimoDAO = new emprestimoDAO();
        emprestimoDAO.getEmprestimos();
        carregarClientes();
        carregarLivros();
        ArrayList<emprestimoDTO> emprestimos = emprestimoDAO.getEmprestimos();
        ObservableList<emprestimoDTO> emprestimoList = javafx.collections.FXCollections
                .observableArrayList(emprestimos);
        emprestimoTableView.setItems(emprestimoList);
        emprestimoTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int livroIdx = IdLivros.indexOf(newValue.getId_livro());
                if (livroIdx >= 0 && livroIdx < livroBox.getItems().size()) {
                    livroBox.getSelectionModel().select(livroIdx);
                } else {
                    livroBox.setValue(null);
                }
                inicioField.setText(newValue.getData_emprestimo());
                fimField.setText(newValue.getData_devolucao());
                int idx = Id.indexOf(newValue.getId_cliente());
                if (idx >= 0 && idx < emprestimoComboBox.getItems().size()) {
                    emprestimoComboBox.getSelectionModel().select(idx);
                } else {
                    emprestimoComboBox.setValue(null);
                }
            }
        });
    }

    Vector<Integer> Id = new Vector<>();
    Vector<Integer> IdLivros = new Vector<>();

    @FXML
    public void carregarClientes() throws SQLException {

        try {
            emprestimoComboBox.getItems().clear();
            Id.clear();
            clienteDAO c = new clienteDAO();
            ResultSet rs = c.listarClientes();
            while (rs.next()) {
                Id.addElement(rs.getInt(1));
                emprestimoComboBox.getItems().add(rs.getString(5));
            }
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao carregar clientes", "Erro ao carregar clientes: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    public void carregarLivros() throws SQLException {
        try {
            livroBox.getItems().clear();
            IdLivros.clear();
            livroDAO l = new livroDAO();
            ResultSet livros = l.listarLivros();
            while (livros.next()) {
                IdLivros.addElement(livros.getInt(1));
                livroBox.getItems().add(livros.getString(2));
            }
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao carregar livros", "Erro ao carregar livros: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void salvarButton() throws SQLException {
        try {
            String inicio, fim;
            inicio = inicioField.getText();
            fim = fimField.getText();
            int livroIdx = livroBox.getSelectionModel().getSelectedIndex();
            int clienteIdx = emprestimoComboBox.getSelectionModel().getSelectedIndex();
            emprestimoDTO emprestimo = new emprestimoDTO();

            if (livroIdx < 0) {
                AlertUtils.erro("Erro", "Campos em branco", "Por favor, selecione um livro.").showAndWait();
                return;
            }

            if (clienteIdx < 0) {
                AlertUtils.erro("Erro", "Campos em branco", "Por favor, selecione um cliente.").showAndWait();
                return;
            }

            emprestimo.setId_livro(IdLivros.get(livroIdx));
            emprestimo.setData_emprestimo(inicio);
            emprestimo.setData_devolucao(fim);
            emprestimo.setId_cliente(Id.get(clienteIdx));

            if (!inicio.isEmpty() && !fim.isEmpty()) {
                emprestimoDAO emprestimoDAO = new emprestimoDAO();
                emprestimoDAO.addEmprestimo(emprestimo);
                AlertUtils.info("Sucesso", "Empréstimo realizado", "Empréstimo realizado com sucesso!").showAndWait();
                inicioField.clear();
                fimField.clear();
                livroBox.getSelectionModel().clearSelection();
                emprestimoComboBox.getSelectionModel().clearSelection();
                listarButton();
            } else {
                AlertUtils.aviso("Aviso", "Campos em branco", "Por favor, preencha as datas de empréstimo e devolução.")
                        .showAndWait();
            }
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao realizar empréstimo", "Erro ao realizar empréstimo: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void listarButton() throws SQLException {
        try {
            emprestimoDAO emprestimoDAO = new emprestimoDAO();
            emprestimoDAO.getEmprestimos();
            ArrayList<emprestimoDTO> emprestimos = emprestimoDAO.getEmprestimos();
            ObservableList<emprestimoDTO> emprestimoList = javafx.collections.FXCollections
                    .observableArrayList(emprestimos);
            emprestimoTableView.setItems(emprestimoList);
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao listar empréstimos", "Erro ao listar empréstimos: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void atualizarButton() throws SQLException {
        try {
            int selectedIndex = emprestimoTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                emprestimoDTO emp = emprestimoTableView.getSelectionModel().getSelectedItem();
                int livroIdx = livroBox.getSelectionModel().getSelectedIndex();
                int clienteIdx = emprestimoComboBox.getSelectionModel().getSelectedIndex();

                if (livroIdx >= 0) {
                    emp.setId_livro(IdLivros.get(livroIdx));
                }
                emp.setData_emprestimo(inicioField.getText());
                emp.setData_devolucao(fimField.getText());
                if (clienteIdx >= 0) {
                    emp.setId_cliente(Id.get(clienteIdx));
                }

                if (!inicioField.getText().isEmpty() && !fimField.getText().isEmpty() && livroIdx >= 0
                        && clienteIdx >= 0) {
                    emprestimoDAO emprestimoDAO = new emprestimoDAO();
                    emprestimoDAO.updateEmprestimo(emp);
                    AlertUtils.info("Sucesso", "Empréstimo atualizado", "Empréstimo atualizado com sucesso!")
                            .showAndWait();
                    inicioField.clear();
                    fimField.clear();
                    listarButton();
                }
            }
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao atualizar empréstimo", "Erro ao atualizar empréstimo: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void excluirButton() throws SQLException {
        try {
            int selectedIndex = emprestimoTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                emprestimoDTO emp = emprestimoTableView.getSelectionModel().getSelectedItem();
                emprestimoDAO emprestimoDAO = new emprestimoDAO();
                emprestimoDAO.deleteEmprestimo(emp);
                AlertUtils.info("Sucesso", "Empréstimo excluído", "Empréstimo excluído com sucesso!").showAndWait();
                emprestimoComboBox.getSelectionModel().clearSelection();
                listarButton();
            }
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao excluir empréstimo", "Erro ao excluir empréstimo: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void voltarButton() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void cancelarButton() {
        inicioField.clear();
        fimField.clear();
        livroBox.getSelectionModel().clearSelection();
        emprestimoComboBox.getSelectionModel().clearSelection();
    }
}
