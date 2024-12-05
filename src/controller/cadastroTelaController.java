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

    private Stage dialogStage; // Variável para armazenar o estágio do diálogo
    private RepositorioTarefa repositorioTarefa = new RepositorioTarefa();

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

    private String categoriaSelecionada = ""; // Armazena a categoria escolhida


    // Método para definir o estágio do diálogo
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
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
        if (!textFieldNomeTarefa.getText().isEmpty()){
            String nome = textFieldNomeTarefa.getText().toUpperCase();
            String detalhes = textFieldDetalhes.getText().toUpperCase();
            boolean importante = checkBoxImportante.isSelected(); // Captura o estado da CheckBox
            repositorioTarefa.criarTarefa(new Tarefa (nome, detalhes, getCategoriaSelecionada(), importante));
            this.dialogStage.close();
        }
        else{
            labelStatusSalvarTarefa.setText("Você deve inserir um nome para a tarefa.");
            textFieldNomeTarefa.requestFocus();
        }
    }
    
    // Outros métodos do controlador, como manipulação de eventos e lógica da tela
    @FXML
    void handleButtonCancelarTarefa(ActionEvent event) {
        this.dialogStage.close();
    }

    // Configura o comportamento dos MenuItems
    private void configurarMenuButton() {
        menuItemCasa.setOnAction(event -> selecionarCategoria(menuItemCasa.getText()));
        menuItemCompras.setOnAction(event -> selecionarCategoria(menuItemCompras.getText()));
        menuItemLazer.setOnAction(event -> selecionarCategoria(menuItemLazer.getText()));
        menuItemPessoal.setOnAction(event -> selecionarCategoria(menuItemPessoal.getText()));
        menuItemSaude.setOnAction(event -> selecionarCategoria(menuItemSaude.getText()));
        menuItemTrabalho.setOnAction(event -> selecionarCategoria(menuItemTrabalho.getText()));
        menuItemEstudos.setOnAction(event -> selecionarCategoria(menuItemEstudos.getText()));
    }

    // Atualiza o texto do MenuButton e armazena a categoria selecionada
    private void selecionarCategoria(String categoria) {
        categoriaSelecionada = categoria;
        menuButtonEscolherCategoria.setText(categoria); // Atualiza o texto do MenuButton
    }

    // Getter para obter a categoria selecionada
    public String getCategoriaSelecionada() {
        return categoriaSelecionada;
    }
}

