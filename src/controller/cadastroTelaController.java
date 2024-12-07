package controller;

import java.net.URL;
import java.util.ResourceBundle;

import data.RepositorioTarefa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Tarefa;

public class cadastroTelaController implements Initializable {

    private Stage dialogStage; 
    private RepositorioTarefa repositorioTarefa = new RepositorioTarefa();
    private Tarefa tarefaAtual; // Armazena a tarefa para edição, se for o caso

    @FXML
    private Button buttonCancelarTarefa;

    @FXML
    private TextField textFieldNomeTarefa;

    @FXML
    private MenuItem menuItemSaude;

    @FXML
    private MenuItem menuItemTrabalho;

    @FXML
    private TextField textFieldDetalhes;

    @FXML
    private MenuItem menuItemLazer;

    @FXML
    private CheckBox checkBoxImportante;

    @FXML
    private MenuItem menuItemEstudos;

    @FXML
    private MenuItem menuItemCompras;

    @FXML
    private MenuItem menuItemCasa;

    @FXML
    private MenuItem menuItemPessoal;

    @FXML
    private MenuButton menuButtonEscolherCategoria;

    @FXML
    private Button buttonSalvarTarefa;

    @FXML
    private Label labelStatusSalvarTarefa;

    private String categoriaSelecionada = "";

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTarefaAtual(Tarefa tarefa) {
        this.tarefaAtual = tarefa;

        // Preenche os campos com os dados da tarefa existente
        if (tarefa != null) {
            textFieldNomeTarefa.setText(tarefa.getNome());
            textFieldDetalhes.setText(tarefa.getDescricao());
            menuButtonEscolherCategoria.setText(tarefa.getCategoria());
            checkBoxImportante.setSelected(tarefa.isImportante());
            categoriaSelecionada = tarefa.getCategoria();
        }
    }

    private void limparTextField(){
        textFieldNomeTarefa.clear();
        textFieldDetalhes.clear();
        labelStatusSalvarTarefa.setText("");
        textFieldNomeTarefa.requestFocus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        limparTextField();
        configurarMenuButton();
    }

    @FXML
    void handleButtonSalvarTarefa(ActionEvent event) {
        String nome = textFieldNomeTarefa.getText();
        String detalhes = textFieldDetalhes.getText();

        if (nome.isEmpty()) {
            labelStatusSalvarTarefa.setText("O nome da tarefa é obrigatório.");
            return;
        }

        if (tarefaAtual == null) {
            // Criação de nova tarefa
            Tarefa novaTarefa = new Tarefa(nome, detalhes, categoriaSelecionada, checkBoxImportante.isSelected());
            repositorioTarefa.criarTarefa(novaTarefa);
        } else {
            // Atualização de tarefa existente
            tarefaAtual.setNome(nome);
            tarefaAtual.setDescricao(detalhes);
            tarefaAtual.setCategoria(categoriaSelecionada);
            tarefaAtual.setImportante(checkBoxImportante.isSelected());
            repositorioTarefa.atualizarTarefa(tarefaAtual);
        }

        dialogStage.close();
    }

    @FXML
    void handleButtonCancelarTarefa(ActionEvent event) {
        dialogStage.close();
    }

    private void configurarMenuButton() {
        menuItemCasa.setOnAction(event -> selecionarCategoria(menuItemCasa.getText()));
        menuItemCompras.setOnAction(event -> selecionarCategoria(menuItemCompras.getText()));
        menuItemLazer.setOnAction(event -> selecionarCategoria(menuItemLazer.getText()));
        menuItemPessoal.setOnAction(event -> selecionarCategoria(menuItemPessoal.getText()));
        menuItemSaude.setOnAction(event -> selecionarCategoria(menuItemSaude.getText()));
        menuItemTrabalho.setOnAction(event -> selecionarCategoria(menuItemTrabalho.getText()));
        menuItemEstudos.setOnAction(event -> selecionarCategoria(menuItemEstudos.getText()));
    }

    private void selecionarCategoria(String categoria) {
        categoriaSelecionada = categoria;
        menuButtonEscolherCategoria.setText(categoria);
    }
}