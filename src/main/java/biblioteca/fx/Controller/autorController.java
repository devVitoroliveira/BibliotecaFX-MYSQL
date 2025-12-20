package biblioteca.fx.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import biblioteca.fx.App;
import biblioteca.fx.DAO.autorDAO;
import biblioteca.fx.DTO.autorDTO;
import biblioteca.fx.util.AlertUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class autorController {

    @FXML
    private TextField nomeAutorField;

    @FXML
    private TextField nacionalidadeField;

    @FXML
    private TextField periodoVidaField;

    @FXML
    private TextField periodoFimField;

    @FXML
    private Button salvarButton;

    @FXML
    private Button cancelarButton;

    @FXML
    private Button listButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<autorDTO> autorTableView;

    @FXML
    private TableColumn<autorDTO, Integer> autorId;

    @FXML
    private TableColumn<autorDTO, String> autorNome;

    @FXML
    private TableColumn<autorDTO, String> autorNacionalidade;

    @FXML
    private TableColumn<autorDTO, String> autorPeriodoVida;

    @FXML
    private TableColumn<autorDTO, String> autorPeriodoFim;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @FXML
    public void initialize() throws SQLException {

        autorId.setCellValueFactory(new PropertyValueFactory<>("id_pessoa"));
        autorNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        autorNacionalidade.setCellValueFactory(new PropertyValueFactory<>("nacionalidade"));
        autorPeriodoVida.setCellValueFactory(new PropertyValueFactory<>("periodoVida"));
        autorPeriodoFim.setCellValueFactory(new PropertyValueFactory<>("periodoFim"));
        autorDAO autorDAO = new autorDAO();
        autorDAO.getAutores();
        ArrayList<autorDTO> autores = autorDAO.getAutores();
        ObservableList<autorDTO> autorList = javafx.collections.FXCollections.observableArrayList(autores);
        autorTableView.setItems(autorList);
        autorTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                LocalDate data = newSelection.getPeriodoVida();
                LocalDate fim = newSelection.getPeriodoFim();
                nomeAutorField.setText(newSelection.getNome());
                nacionalidadeField.setText(newSelection.getNacionalidade());
                periodoVidaField.setText(data == null ? "" : data.format(formatter));
                periodoFimField.setText(fim == null ? "" : fim.format(formatter));
            } else {
                nomeAutorField.clear();
                nacionalidadeField.clear();
                periodoVidaField.clear();
                periodoFimField.clear();
            }
        });
    }

    @FXML
    private void salvarButton() throws SQLException {
        try {
            String nome, nacionalidade, periodoVida, periodoFim;
            nome = nomeAutorField.getText();
            nacionalidade = nacionalidadeField.getText();
            periodoVida = periodoVidaField.getText();
            periodoFim = periodoFimField.getText();

            if (!nome.isEmpty() && !nacionalidade.isEmpty() && !periodoVida.isEmpty()) {
                if (!nome.matches("[a-zA-Z ]+")) {
                    AlertUtils
                            .aviso("Erro", "Nome inválido",
                                    "O nome deve conter apenas letras e espaços.")
                            .showAndWait();
                    return;
                }
                if (!nacionalidade.matches("[a-zA-Z ]+")) {
                    AlertUtils
                            .aviso("Erro", "Nacionalidade inválida",
                                    "A nacionalidade deve conter apenas letras e espaços.")
                            .showAndWait();
                    return;
                }
                if (!periodoVida.matches("\\d{2}-\\d{2}-\\d{4}")) {
                    AlertUtils
                            .aviso("Erro", "Data inválida",
                                    "A data de nascimento deve estar no formato dd-MM-yyyy.")
                            .showAndWait();
                    return;
                }
                autorDTO autor = new autorDTO();
                autor.setNome(nome);
                autor.setNacionalidade(nacionalidade);
                try {
                    autor.setPeriodoVida(LocalDate.parse(periodoVida, formatter));
                } catch (DateTimeParseException e) {
                    AlertUtils.erro("Erro", "Data inválida", "Uma das datas é inválida.")
                            .showAndWait();
                    return;
                }
                if (!periodoFim.isEmpty()) {
                    if (!periodoFim.matches("\\d{2}-\\d{2}-\\d{4}")) {
                        AlertUtils
                                .aviso("Erro", "Data inválida",
                                        "A data de falecimento deve estar no formato dd-MM-yyyy.")
                                .showAndWait();
                        return;
                    }
                    try {
                        autor.setPeriodoFim(LocalDate.parse(periodoFim, formatter));
                    } catch (DateTimeParseException e) {
                        AlertUtils.erro("Erro", "Data inválida", "Uma das datas é inválida.")
                                .showAndWait();
                        return;
                    }
                } else {
                    autor.setPeriodoFim(null);
                }
                autorDAO autorDAO = new autorDAO();
                autorDAO.addAutor(autor);
                AlertUtils.info("Successo", "Autor Cadastrado", "Autor cadastrado com sucesso!").showAndWait();
                nacionalidadeField.clear();
                nomeAutorField.clear();
                periodoVidaField.clear();
                periodoFimField.clear();
                listButton();
            } else {
                AlertUtils
                        .aviso("Erro", "Campos em branco",
                                "Por favor, preencha todos os campos(nome, nacionalidade, periodo de vida).")
                        .showAndWait();
            }
        } catch (SQLException e) {
            AlertUtils.erro("Error", "Erro ao cadastrar autor", "Erro ao cadastrar autor: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void listButton() throws SQLException {
        try {
            autorDAO autorDAO = new autorDAO();
            ArrayList<autorDTO> autores = autorDAO.getAutores();
            ObservableList<autorDTO> autorList = javafx.collections.FXCollections.observableArrayList(autores);
            autorTableView.setItems(autorList);
        } catch (SQLException e) {
            AlertUtils.erro("Error", "Error ao listar autores", "Erro ao listar autores: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void updateButton() {
        int selectedIndex = autorTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            autorDTO autor = autorTableView.getSelectionModel().getSelectedItem();

            try {
                if (!nomeAutorField.getText().isEmpty() && !nacionalidadeField.getText().isEmpty()
                        && !periodoVidaField.getText().isEmpty()) {
                    autor.setNome(nomeAutorField.getText());
                    autor.setNacionalidade(nacionalidadeField.getText());
                    if (!nomeAutorField.getText().matches("[a-zA-Z ]+")) {
                        AlertUtils
                                .aviso("Erro", "Nome inválido",
                                        "O nome deve conter apenas letras e espaços.")
                                .showAndWait();
                        return;
                    }
                    if (!nacionalidadeField.getText().matches("[a-zA-Z ]+")) {
                        AlertUtils
                                .aviso("Erro", "Nacionalidade inválida",
                                        "A nacionalidade deve conter apenas letras e espaços.")
                                .showAndWait();
                        return;
                    }
                    if (!periodoVidaField.getText().matches("\\d{2}-\\d{2}-\\d{4}")) {
                        AlertUtils
                                .aviso("Erro", "Data inválida",
                                        "A data de nascimento deve estar no formato dd-MM-yyyy.")
                                .showAndWait();
                        return;
                    }
                    try {
                        autor.setPeriodoVida(LocalDate.parse(periodoVidaField.getText(), formatter));
                    } catch (DateTimeParseException e) {
                        AlertUtils.erro("Erro", "Data inválida", "Uma das datas é inválida.")
                                .showAndWait();
                        return;
                    }
                    if (!periodoFimField.getText().trim().isEmpty()) {
                        if (!periodoFimField.getText().matches("\\d{2}-\\d{2}-\\d{4}")) {
                            AlertUtils
                                    .aviso("Erro", "Data inválida",
                                            "A data de falecimento deve estar no formato dd-MM-yyyy.")
                                    .showAndWait();
                            return;
                        }
                        try {
                            autor.setPeriodoFim(LocalDate.parse(periodoFimField.getText(), formatter));
                        } catch (DateTimeParseException e) {
                            AlertUtils.erro("Erro", "Data inválida", "Uma das datas é inválida.")
                                    .showAndWait();
                            return;
                        }
                    } else {
                        autor.setPeriodoFim(null);
                    }
                    autorDAO autorDAO = new autorDAO();
                    autorDAO.updateAutor(autor);
                    AlertUtils.info("Successo", "Autor atualizado", "Autor atualizado com sucesso!").showAndWait();
                    nacionalidadeField.clear();
                    nomeAutorField.clear();
                    periodoVidaField.clear();
                    periodoFimField.clear();
                    listButton();
                } else {
                    AlertUtils
                            .aviso("Erro", "Campos em branco",
                                    "Por favor, preencha todos os campos(nome, nacionalidade, periodo de vida).")
                            .showAndWait();
                }
            } catch (SQLException e) {
                AlertUtils.erro("Erro", "Erro ao atualizar autor", "Erro ao atualizar autor: " + e.getMessage())
                        .showAndWait();
            }

        } else {
            AlertUtils.aviso("Aviso", "Nehum autor selecionado", "Por favor, selecione um autor para autalizar.")
                    .showAndWait();
        }
    }

    @FXML
    private void deleteButton() {
        int selectedIndex = autorTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            autorDTO autor = autorTableView.getSelectionModel().getSelectedItem();
            try {
                autorDAO autorDAO = new autorDAO();
                autorDAO.deleteAutor(autor);
                AlertUtils.info("Successo", "Autor excluído", "Autor excluido com sucesso.").showAndWait();
                nacionalidadeField.clear();
                nomeAutorField.clear();
                periodoVidaField.clear();
                periodoFimField.clear();
                listButton();
            } catch (SQLException e) {
                AlertUtils.erro("Erro", "Erro ao excluir autor", "Erro ao excluir autor: " + e.getMessage())
                        .showAndWait();
            }
        } else {
            AlertUtils.aviso("Aviso", "Nehum autor selecionado", "Por favor, selecione um autor para excluir.")
                    .showAndWait();
        }
    }

    @FXML
    private void cancelarButton() {
        nomeAutorField.clear();
        nacionalidadeField.clear();
        periodoVidaField.clear();
        periodoFimField.clear();
        autorTableView.getSelectionModel().clearSelection();
    }

    @FXML
    private void backButton() throws IOException {
        App.setRoot("main");
    }
}
