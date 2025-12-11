package biblioteca.fx.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import biblioteca.fx.App;
import biblioteca.fx.DAO.autorDAO;
import biblioteca.fx.DAO.pessoaDAO;
import biblioteca.fx.DTO.autorDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
                nomeAutorField.setText(newSelection.getNome());
                nacionalidadeField.setText(newSelection.getNacionalidade());
                periodoVidaField.setText(newSelection.getPeriodoVida());
                periodoFimField.setText(newSelection.getPeriodoFim() == null ? "" : newSelection.getPeriodoFim());
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
                String fim = periodoFim.trim().isEmpty() ? null : periodoFim.trim();

                autorDTO autor = new autorDTO();
                autor.setNome(nome);
                autor.setNacionalidade(nacionalidade);
                autor.setPeriodoVida(periodoVida);
                autor.setPeriodoFim(fim);
                pessoaDAO pessoaDAO = new pessoaDAO();
                pessoaDAO.addPessoa(autor);
                autorDAO autorDAO = new autorDAO();
                autorDAO.addAutor(autor);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Author Added");
                alert.setContentText("The author was added successfully.");
                alert.showAndWait();
                nacionalidadeField.clear();
                nomeAutorField.clear();
                periodoVidaField.clear();
                periodoFimField.clear();
                listButton();
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
            alert.setHeaderText("Error Adding Author");
            alert.setContentText("There was an error adding the author: " + e.getMessage());
            alert.showAndWait();
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Getting Authors");
            alert.setContentText("There was an error getting the authors: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void updateButton() {
        int selectedIndex = autorTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            autorDTO autor = autorTableView.getSelectionModel().getSelectedItem();
            autor.setNome(nomeAutorField.getText());
            autor.setNacionalidade(nacionalidadeField.getText());
            autor.setPeriodoVida(periodoVidaField.getText());
            autor.setPeriodoFim(periodoFimField.getText());
            try {
                if (!nomeAutorField.getText().isEmpty() && !nacionalidadeField.getText().isEmpty()
                        && !periodoVidaField.getText().isEmpty()) {
                    String fim = null;
                    if (!periodoFimField.getText().trim().isEmpty()) {
                        fim = periodoFimField.getText().trim();
                    }
                    autor.setPeriodoFim(fim);
                    autorDAO autorDAO = new autorDAO();
                    autorDAO.updateAutor(autor);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Author Updated");
                    alert.setContentText("The author was updated successfully.");
                    alert.showAndWait();
                    nacionalidadeField.clear();
                    nomeAutorField.clear();
                    periodoVidaField.clear();
                    periodoFimField.clear();
                    listButton();
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Updating Author");
                alert.setContentText("There was an error updating the author: " + e.getMessage());
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Author Selected");
            alert.setContentText("Please select an author in the table.");
            alert.showAndWait();
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Author Deleted");
                alert.setContentText("The author was deleted successfully.");
                alert.showAndWait();
                nacionalidadeField.clear();
                nomeAutorField.clear();
                periodoVidaField.clear();
                periodoFimField.clear();
                listButton();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Deleting Author");
                alert.setContentText("There was an error deleting the author: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Author Selected");
            alert.setContentText("Please select an author in the table.");
            alert.showAndWait();
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
