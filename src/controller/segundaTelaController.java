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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
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
    private Button buttonImportante;

    @FXML
    private Button buttonBuscarTarefa;

    @FXML
    private Button buttonTarefasPendentes, buttonTarefasRealizadas, buttonLazer,
    buttonCasa, buttonCompras, buttonSaude, buttonTrabalho, buttonEstudos, buttonPessoal;

    @FXML
    private VBox VBoxListaDeTarefas;

    @FXML
    private TextField textFieldBuscarTarefa;

    private RepositorioTarefa repositorioTarefa = new RepositorioTarefa();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        limparTextField();
        carregarTarefas();
    }

    private void limparTextField(){
        textFieldBuscarTarefa.clear();
    }

    @FXML
    void handleButtonBuscarTarefa(ActionEvent event) {
        String textoBusca = textFieldBuscarTarefa.getText().trim().toLowerCase();

        // Se o campo de busca está vazio e nenhum filtro está aplicado
        if (textoBusca.isEmpty() && filtroAtual.isEmpty()) {
            carregarTarefas(); // Mostra todas as tarefas
        } else if (textoBusca.isEmpty()) {
            aplicarFiltroAtual(); // Mostra as tarefas do filtro atual
        } else {
            VBoxListaDeTarefas.getChildren().clear(); // Limpa a lista de tarefas exibidas
            ArrayList<Tarefa> tarefas = repositorioTarefa.getAllTarefas();

            // Aplica os filtros de busca e categoria
            for (Tarefa tarefa : tarefas) {
                boolean pertenceAoFiltro = filtroAtual.isEmpty() || // Sem filtro ou...
                        (filtroAtual.equals("Importante") && tarefa.isImportante()) || // Filtro de importantes
                        (tarefa.getCategoria().equalsIgnoreCase(filtroAtual)); // Filtro por categoria

                boolean contemBuscaNaOrdem = tarefa.getNome().toLowerCase().contains(textoBusca) &&
                                            tarefa.getNome().toLowerCase().indexOf(textoBusca) == 0;

                if (pertenceAoFiltro && contemBuscaNaOrdem) {
                    VBoxListaDeTarefas.getChildren().add(criarContainerTarefa(tarefa));
                }
            }
        }
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
            
            // Exibe a nova tela.
            dialogStage.showAndWait();

            carregarTarefas();
            
        } catch (IOException ex) {
            Logger.getLogger(segundaTelaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Carregar as tarefas salvas no repositório e gerar os containers estilizados
    private void carregarTarefas() {
        VBoxListaDeTarefas.getChildren().clear(); // Limpar a interface antes de atualizar
        ArrayList<Tarefa> tarefas = repositorioTarefa.getAllTarefas();
    
        // Adicionar tarefas não concluídas primeiro
        tarefas.stream()
            .filter(tarefa -> !tarefa.isFinalizada())
            .forEach(tarefa -> VBoxListaDeTarefas.getChildren().add(criarContainerTarefa(tarefa)));
    
        // Adicionar tarefas concluídas depois
        tarefas.stream()
            .filter(Tarefa::isFinalizada)
            .forEach(tarefa -> VBoxListaDeTarefas.getChildren().add(criarContainerTarefa(tarefa)));
    }
    

    // Criar um container estilizado para exibir as tarefas
    private AnchorPane criarContainerTarefa(Tarefa tarefa) {
        AnchorPane container = new AnchorPane();
        container.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 16; -fx-background-radius: 16;");
        container.setPrefHeight(110); // Aumentado para acomodar o botão de editar

        //Título
        Text titulo = null;
        if (tarefa.isImportante()) {
            // Adiciona a estrela se a tarefa for importante
            Text estrela = new Text("★");
            estrela.setStyle("-fx-font-size: 20px; -fx-fill: #FFA500;"); // Tamanho e cor dourada
            AnchorPane.setTopAnchor(estrela, 5.0);
            AnchorPane.setLeftAnchor(estrela, 70.0);
            container.getChildren().add(estrela); // Adiciona ao container

            // O título é deslocado para a direita quando a tarefa é importante
            titulo = new Text(tarefa.getNome());
            titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            AnchorPane.setTopAnchor(titulo, 5.0);
            AnchorPane.setLeftAnchor(titulo, 97.0);
        } else {
            // O título fica como padrão
            titulo = new Text(tarefa.getNome());
            titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            AnchorPane.setTopAnchor(titulo, 5.0);
            AnchorPane.setLeftAnchor(titulo, 70.0);
        }
    
        // Descrição
        Text descricao = new Text("Descrição: " + tarefa.getDescricao());
        AnchorPane.setTopAnchor(descricao, 40.0);
        AnchorPane.setLeftAnchor(descricao, 70.0);
    
        // Categoria
        Text categoria = new Text("Categoria: " + (tarefa.getCategoria().isEmpty() ? "Geral" : tarefa.getCategoria()));
        AnchorPane.setTopAnchor(categoria, 70.0);
        AnchorPane.setLeftAnchor(categoria, 70.0);
    
        // CheckBox para marcar como concluída
        CheckBox checkConcluir = new CheckBox("");
        checkConcluir.setSelected(tarefa.isFinalizada());
        AnchorPane.setTopAnchor(checkConcluir, 33.0);
        AnchorPane.setLeftAnchor(checkConcluir, 10.0);
    
        checkConcluir.setOnAction(event -> {
            tarefa.setFinalizada(checkConcluir.isSelected()); // Atualiza o estado da tarefa
            repositorioTarefa.atualizarTarefa(tarefa); // Atualiza no repositório
            carregarTarefas(); // Atualiza a lista reorganizando
        });

        
    
        // Botão para remover tarefa
        Button buttonRemover = new Button("Remover");
        buttonRemover.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
        AnchorPane.setTopAnchor(buttonRemover, 10.0);
        AnchorPane.setRightAnchor(buttonRemover, 10.0);
    
        buttonRemover.setOnAction(event -> {
            repositorioTarefa.deletarTarefa(tarefa); // Remover do repositório
            carregarTarefas(); // Atualizar a interface
        });
    
        // Botão para editar tarefa
        Button buttonEditar = new Button("  Editar   ");
        buttonEditar.setStyle("-fx-background-color: #4682B4; -fx-text-fill: white;");
        AnchorPane.setBottomAnchor(buttonEditar, 10.0); // Posicionado abaixo do botão de remover
        AnchorPane.setRightAnchor(buttonEditar, 10.0);
    
        buttonEditar.setOnAction(event -> abrirTelaEdicao(tarefa)); // Chamar método para abrir a tela de edição
    
        // Adiciona elementos ao container
        container.getChildren().addAll(titulo, descricao, categoria, checkConcluir, buttonRemover, buttonEditar);
        return container;
    }
    
    private void abrirTelaEdicao(Tarefa tarefa) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cadastroTela.fxml"));
            AnchorPane page = loader.load();
    
            // Configura o estágio de diálogo
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Tarefa");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
    
            // Passa a tarefa para o controlador (se existir)
            cadastroTelaController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            if (tarefa != null) {
                controller.setTarefaAtual(tarefa);
            }
    
            dialogStage.showAndWait();
            carregarTarefas(); // Atualiza a interface após salvar
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void aplicarFiltroAtual() {
        VBoxListaDeTarefas.getChildren().clear(); // Limpa a lista de tarefas exibidas
        ArrayList<Tarefa> tarefas = repositorioTarefa.getAllTarefas();
        
        for (Tarefa tarefa : tarefas) {
            if (filtroAtual.isEmpty() || // Sem filtro
            (filtroAtual.equals("Importante") && tarefa.isImportante()) || // Filtro de importantes
            (tarefa.getCategoria().equalsIgnoreCase(filtroAtual))) { // Filtro por categoria
                VBoxListaDeTarefas.getChildren().add(criarContainerTarefa(tarefa));
            }
        }
    }    
    
    private String filtroAtual = ""; 

    @FXML
    void handleButtonCategoria(ActionEvent event) {
        String categoriaSelecionada = "";

        if (event.getSource() == buttonLazer) {
            categoriaSelecionada = "Lazer";
        } else if (event.getSource() == buttonCasa) {
            categoriaSelecionada = "Casa";
        } else if (event.getSource() == buttonCompras) {
            categoriaSelecionada = "Compras";
        } else if (event.getSource() == buttonSaude) {
            categoriaSelecionada = "Saúde";
        } else if (event.getSource() == buttonTrabalho) {
            categoriaSelecionada = "Trabalho";
        } else if (event.getSource() == buttonEstudos) {
            categoriaSelecionada = "Estudos";
        } else if (event.getSource() == buttonPessoal) {
            categoriaSelecionada = "Pessoal";
        } else if (event.getSource() == buttonImportante) {
            categoriaSelecionada = "Importante";
        }

        // Se o mesmo filtro for clicado novamente, limpa o filtro
        if (filtroAtual.equals(categoriaSelecionada)) {
            filtroAtual = ""; // Remove o filtro
            carregarTarefas(); // Recarrega todas as tarefas
        } else {
            filtroAtual = categoriaSelecionada; // Aplica o novo filtro
            aplicarFiltroAtual(); // Aplica o filtro
        }

        handleButtonBuscarTarefa(null); // Atualiza a busca dentro do filtro
    }

}