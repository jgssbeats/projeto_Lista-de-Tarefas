package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class segundaTelaController {

    @FXML
    private Button buttonAdicionarTarefa;

    @FXML
    void handleButtonAdicionarTarefa(ActionEvent event) {
        try {
            // Corrigindo o caminho do FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cadastroTela.fxml"));
            AnchorPane page = loader.load();
            
            // Cria o palco dialogStage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastrar Tarefa");
            dialogStage.initModality(Modality.APPLICATION_MODAL); // Impede interação com a janela principal enquanto essa estiver aberta.
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Configura o controlador da nova tela.
            cadastroTelaController controller = loader.getController();
            controller.setDialogStage(dialogStage); // Define o estágio do diálogo para o controlador.
            
            // Exibe a nova tela.
            dialogStage.showAndWait();
            
        } catch (IOException ex) {
            Logger.getLogger(segundaTelaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}