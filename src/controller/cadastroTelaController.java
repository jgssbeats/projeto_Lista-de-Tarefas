package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class cadastroTelaController implements Initializable {

    private Stage dialogStage; // Variável para armazenar o estágio do diálogo

    @FXML
    private Button buttonCancelarTarefa;

    @FXML
    private TextField textFieldNomeTarefa;

    @FXML
    private TextField textFieldDetalhes;


    // Método para definir o estágio do diálogo
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void limparTextField(){
        textFieldNomeTarefa.clear();
        textFieldDetalhes.clear();
        textFieldNomeTarefa.requestFocus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        limparTextField();
    }
    
    // Outros métodos do controlador, como manipulação de eventos e lógica da tela
    @FXML
    void handleButtonCancelarTarefa(ActionEvent event) {
        this.dialogStage.close();
    }
}

