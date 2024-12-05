package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Tarefa;
import data.RepositorioTarefa;

public class segundaTelaController implements Initializable {

    @FXML
    private Button buttonAdicionarTarefa;

    @FXML
    private VBox VBoxListaDeTarefas;

    private RepositorioTarefa repositorioTarefa = new RepositorioTarefa();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarTarefas();
    }

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

            carregarTarefas();
            
            // Exibe a nova tela.
            dialogStage.showAndWait();
            
        } catch (IOException ex) {
            Logger.getLogger(segundaTelaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Carregar as tarefas salvas no repositório e gerar os containers estilizados
    private void carregarTarefas() {
        VBoxListaDeTarefas.getChildren().clear(); // Limpar a interface antes de atualizar
        ArrayList<Tarefa> tarefas = repositorioTarefa.getAllTarefas();

        for (Tarefa tarefa : tarefas) {
            VBoxListaDeTarefas.getChildren().add(criarContainerTarefa(tarefa));
        }
    }

    // Criar um container estilizado para exibir as tarefas
    private AnchorPane criarContainerTarefa(Tarefa tarefa) {
        AnchorPane container = new AnchorPane();
        container.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");
        container.setPrefHeight(100);

        Text titulo = new Text(tarefa.getNome());
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor(titulo, 10.0);
        AnchorPane.setLeftAnchor(titulo, 10.0);

        Text descricao = new Text("Descrição: " + tarefa.getDescricao());
        AnchorPane.setTopAnchor(descricao, 40.0);
        AnchorPane.setLeftAnchor(descricao, 10.0);

        Text categoria = new Text("Categoria: " + tarefa.getCategoria());
        AnchorPane.setTopAnchor(categoria, 70.0);
        AnchorPane.setLeftAnchor(categoria, 10.0);

        Button concluir = new Button("Concluir");
        concluir.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        AnchorPane.setTopAnchor(concluir, 10.0);
        AnchorPane.setRightAnchor(concluir, 10.0);

        concluir.setOnAction(event -> {
            tarefa.setFinalizada(true);
            repositorioTarefa.atualizarTarefa(tarefa); // Atualizar no repositório
            carregarTarefas(); // Atualizar a interface
        });

        container.getChildren().addAll(titulo, descricao, categoria, concluir);
        return container;
    }
}