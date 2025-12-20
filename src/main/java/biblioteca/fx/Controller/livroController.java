package biblioteca.fx.Controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Vector;

import biblioteca.fx.App;
import biblioteca.fx.DAO.autorDAO;
import biblioteca.fx.DAO.livroDAO;
import biblioteca.fx.DTO.livroDTO;
import biblioteca.fx.util.AlertUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class livroController {

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
    private TextField livroField;
    @FXML
    private TextField generoField;
    @FXML
    private TextField anoField;
    @FXML
    private TextField ibsnField;
    @FXML
    private ComboBox<String> livroComboBox;
    @FXML
    private TableView<livroDTO> livroTableView;
    @FXML
    private TableColumn<livroDTO, Integer> livroId;
    @FXML
    private TableColumn<livroDTO, String> livroNome;
    @FXML
    private TableColumn<livroDTO, String> livroGenero;
    @FXML
    private TableColumn<livroDTO, String> livroAno;
    @FXML
    private TableColumn<livroDTO, String> livroAutor;
    @FXML
    private TableColumn<livroDTO, String> livroIbsn;

    @FXML
    private void initialize() throws SQLException {
        livroId.setCellValueFactory(new PropertyValueFactory<>("idLivro"));
        livroNome.setCellValueFactory(new PropertyValueFactory<>("nomeLivro"));
        livroGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        livroAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        livroAutor.setCellValueFactory(new PropertyValueFactory<>("nomeAutor"));
        livroIbsn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        livroDAO l = new livroDAO();
        l.getLivros();
        carregarAutores();
        ArrayList<livroDTO> livros = l.getLivros();
        ObservableList<livroDTO> livroList = javafx.collections.FXCollections.observableArrayList(livros);
        livroTableView.setItems(livroList);
        livroTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int autor = idAutores.indexOf(newSelection.getIdautor());
                if (autor >= 0 && autor < livroComboBox.getItems().size()) {
                    livroComboBox.getSelectionModel().select(autor);
                } else {
                    livroComboBox.setValue(null);
                }
                livroField.setText(newSelection.getNomeLivro());
                generoField.setText(newSelection.getGenero());
                anoField.setText(newSelection.getAno().toString());
                ibsnField.setText(newSelection.getIsbn());
            }
        });
    }

    Vector<Integer> idAutores = new Vector<>();

    @FXML
    public void carregarAutores() throws SQLException {
        try {
            livroComboBox.getItems().clear();
            idAutores.clear();
            autorDAO a = new autorDAO();
            ResultSet rs = a.listarAutores();
            while (rs.next()) {
                idAutores.addElement(rs.getInt(1));
                livroComboBox.getItems().add(rs.getString(6));
            }
        } catch (Exception e) {
            AlertUtils.erro("Erro", "Erro ao carregar autores", "Erro ao carregar autores: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void salvarButton() throws SQLException {
        try {
            String livro, genero, anoStr, ibsn;
            livro = livroField.getText();
            genero = generoField.getText();
            anoStr = anoField.getText();
            ibsn = ibsnField.getText();
            int livroIdx = livroComboBox.getSelectionModel().getSelectedIndex();
            Year ano = Year.of(Integer.parseInt(anoStr));
            livroDTO l = new livroDTO();
            if (livroIdx < 0) {
                AlertUtils.erro("Erro", "Campos em branco", "Por favor, selecione um autor.").showAndWait();
                return;
            }
            l.setNomeLivro(livro);
            l.setGenero(genero);
            l.setAno(ano);
            l.setIsbn(ibsn);
            l.setIdautor(idAutores.get(livroIdx));
            if (!livro.isEmpty() && !genero.isEmpty() && !anoStr.isEmpty() && !ibsn.isEmpty()) {
                livroDAO ldao = new livroDAO();
                ldao.addLivro(l);
                AlertUtils.info("Sucesso", "Livro cadastrado", "Livro cadastrado com sucesso!").showAndWait();
                livroField.clear();
                generoField.clear();
                anoField.clear();
                ibsnField.clear();
                livroComboBox.getSelectionModel().clearSelection();
                carregarAutores();
                listarButton();
            } else {
                AlertUtils.erro("Erro", "Campos em branco", "Por favor, preencha todos os campos.").showAndWait();
            }
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao cadastrar livro", "Erro ao cadastrar livro: " + e.getMessage())
                    .showAndWait();
        } catch (NumberFormatException e) {
            AlertUtils.erro("Erro", "Ano inválido", "O ano deve conter apenas numeros.").showAndWait();
            return;
        }
    }

    @FXML
    private void listarButton() throws SQLException {
        try {
            livroDAO ldao = new livroDAO();
            ArrayList<livroDTO> livros = ldao.getLivros();
            ObservableList<livroDTO> livroList = javafx.collections.FXCollections.observableArrayList(livros);
            livroTableView.setItems(livroList);
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao listar livros", "Erro ao listar livros: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void atualizarButton() throws SQLException {
        try {
            int idx = livroTableView.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                livroDTO l = livroTableView.getSelectionModel().getSelectedItem();
                int autorIdx = livroComboBox.getSelectionModel().getSelectedIndex();

                if (autorIdx >= 0) {
                    l.setIdautor(idAutores.get(autorIdx));
                }
                l.setNomeLivro(livroField.getText());
                l.setGenero(generoField.getText());
                try {
                    l.setAno(Year.of(Integer.parseInt(anoField.getText())));
                } catch (NumberFormatException e) {
                    AlertUtils.erro("Erro", "Ano inválido", "O ano deve conter apenas numeros.").showAndWait();
                    return;
                }

                l.setIsbn(ibsnField.getText());
                if (!livroField.getText().isEmpty() && !generoField.getText().isEmpty() && !anoField.getText().isEmpty()
                        && !ibsnField.getText().isEmpty()) {
                    livroDAO ldao = new livroDAO();
                    ldao.updateLivro(l);
                    AlertUtils.info("Sucesso", "Livro atualizado", "Livro atualizado com sucesso!").showAndWait();
                    livroField.clear();
                    generoField.clear();
                    anoField.clear();
                    ibsnField.clear();
                    livroComboBox.getSelectionModel().clearSelection();
                    carregarAutores();
                    listarButton();
                } else {
                    AlertUtils.erro("Erro", "Campos em branco", "Por favor, preencha todos os campos.").showAndWait();
                }
            } else {
                AlertUtils.erro("Erro", "Campos em branco", "Por favor, selecione um livro.").showAndWait();
            }
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao atualizar livro", "Erro ao atualizar livro: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void excluirButton() throws SQLException {
        try {
            int idx = livroTableView.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                livroDTO l = livroTableView.getSelectionModel().getSelectedItem();
                livroDAO ldao = new livroDAO();
                ldao.deleteLivro(l);
                AlertUtils.info("Sucesso", "Livro excluído", "Livro excluído com sucesso!").showAndWait();
                livroField.clear();
                generoField.clear();
                anoField.clear();
                ibsnField.clear();
                livroComboBox.getSelectionModel().clearSelection();
                carregarAutores();
                listarButton();
            }
        } catch (SQLException e) {
            AlertUtils.erro("Erro", "Erro ao excluir livro", "Erro ao excluir livro: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void voltarButton() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void cancelarButton() {
        livroField.clear();
        generoField.clear();
        anoField.clear();
        ibsnField.clear();
        livroComboBox.getSelectionModel().clearSelection();
    }
}
