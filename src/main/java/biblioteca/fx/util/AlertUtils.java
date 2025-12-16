package biblioteca.fx.util;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import biblioteca.fx.App;

/**
 * Utilitário para criar Alerts com ícone padronizado da aplicação
 */
public class AlertUtils {

    /**
     * Cria um Alert com o ícone da aplicação
     */
    public static Alert criarAlert(Alert.AlertType tipo, String titulo, String header, String conteudo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(conteudo);

        // Adiciona o ícone da aplicação ao Alert
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(App.getAppIcon());

        return alert;
    }

    /**
     * Atalho para Alert de ERRO
     */
    public static Alert erro(String titulo, String header, String conteudo) {
        return criarAlert(Alert.AlertType.ERROR, titulo, header, conteudo);
    }

    /**
     * Atalho para Alert de AVISO
     */
    public static Alert aviso(String titulo, String header, String conteudo) {
        return criarAlert(Alert.AlertType.WARNING, titulo, header, conteudo);
    }

    /**
     * Atalho para Alert de INFORMAÇÃO
     */
    public static Alert info(String titulo, String header, String conteudo) {
        return criarAlert(Alert.AlertType.INFORMATION, titulo, header, conteudo);
    }

    /**
     * Atalho para Alert de CONFIRMAÇÃO
     */
    public static Alert confirmacao(String titulo, String header, String conteudo) {
        return criarAlert(Alert.AlertType.CONFIRMATION, titulo, header, conteudo);
    }
}
