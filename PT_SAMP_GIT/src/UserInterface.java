
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Thomas
 */
public class UserInterface extends Application {

    
    public static Stage primaryStage;
    public static ListView listView;
    public static TextArea textArea;
    public static BufferedWriter BuffWriter;
    public  static TextArea textAreaWords;
    public  static TextArea textAreaIndex;

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int LIST_MARGIN = 5;
    private final int LIST_PADDING = 2;
    private final int BUTTON_SIZE = 80;
    public static TextField searchTextField;
    int i = 3;

    Data data = new Data();
    Dictionary dictionary = new Dictionary();
    Search search = new Search();

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Semestrální práce PT");
        primaryStage.setScene(createScene());
        primaryStage.show();

    }

    private Scene createScene() throws IOException {
        Scene scene = new Scene(getRoot(), WIDTH, HEIGHT);
        return scene;
    }

    public Parent getRoot() throws IOException {
        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(textArea());
        rootPane.setRight(dictionaryListView());
        rootPane.setTop(menu());
        return rootPane;
    }

    private Node dictionaryListView() throws IOException {
        BorderPane borderPane = new BorderPane();
        listView = new ListView();
        listView.setEditable(true);
        listView.setPadding(new Insets(LIST_PADDING));

        borderPane.setCenter(listView);
        BorderPane.setMargin(listView, new Insets(LIST_MARGIN, LIST_MARGIN, LIST_MARGIN, LIST_MARGIN / 2));
        borderPane.setBottom(dictionaryControl());

        File file = new File("Dictionary.txt");
        Data.uploadMyFile(file);

        return borderPane;

    }

    private Node dictionaryControl() throws IOException {

        HBox butts = new HBox();
        listView.setCellFactory(TextFieldListCell.forListView());
        listView.setEditable(true);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                data.saveDictionary();
            } catch (IOException ex) {
                Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        saveButton.setPrefWidth(BUTTON_SIZE);
        
        Button openButton = new Button("Open");
        openButton.setPrefWidth(BUTTON_SIZE);
        openButton.setOnAction(event -> data.uploadDictionary());

    
        Button refreshButton = new Button("Refresh");
        refreshButton.setPrefWidth(BUTTON_SIZE);

        refreshButton.setOnAction(event -> {dictionary.createArray();
        Dictionary.nonDuplicatedArrayString();
        });

        butts.getChildren().addAll(refreshButton, openButton, saveButton);
        butts.setPadding(new Insets(2));
        butts.setSpacing(10);

        return butts;
    }


    private Node textArea() {
        BorderPane borderPane = new BorderPane();
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPromptText("Write or upload your text.");
        /*textArea.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue) {

            } else {
                dictionary.createArray();
                Dictionary.nonDuplicatedArrayString();
            }
        });*/
        BorderPane.setMargin(textArea, new Insets(LIST_MARGIN, LIST_MARGIN / 2, LIST_MARGIN, LIST_MARGIN));
        borderPane.setCenter(textArea);
        borderPane.setBottom(textAreaControl());
        return borderPane;

    }


    private Node textAreaControl() {

        HBox controls = new HBox();

        Button searchButton = new Button("Search");

        searchTextField = new TextField();
        searchTextField.setPromptText("Word for searching");

        searchButton.setOnAction(event -> {
            if (searchTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Write word for searching!");
                alert.setTitle("Error");
                alert.setHeaderText("Nothing for searching!");
                alert.showAndWait();

            } else {
                if (Trie.find(searchTextField.getText().toLowerCase(), Trie.root) == false) {
                    //System.out.println("Chcete pridat slovo do slovniku?");
                    Label labelWords = new Label("10 WORDS");
                    textAreaWords = new TextArea();
                    textAreaWords.setEditable(false);
                    textAreaWords.setPromptText("Any similar words...");
                    Levenshtein.bubbleSort();
                    //textAreaWords.setText(Levenshtein.bubbleSort());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Search word is not in your dictionary!");
                    alert.setContentText("Do you want Add this word: " + searchTextField.getText().toLowerCase() +"?");
                    alert.setTitle("Search word");

                    ButtonType buttonAdd = new ButtonType("Add");
                    ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonAdd, buttonCancel);
                    GridPane expContent = new GridPane();
                    expContent.setMaxWidth(Double.MAX_VALUE);
                    expContent.add(labelWords,0,0);
                    expContent.add(textAreaWords,0,1);

                    alert.getDialogPane().setExpandableContent(expContent);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonAdd) {
                        Dictionary.treeSet.add(searchTextField.getText().toLowerCase());
                        Trie.uploadDataToTrie();
                        Dictionary.nonDuplicatedArrayString();
                        // ... user chose "Add"
                    }// else {
                        // ... user chose CANCEL or closed the dialog
                    //}
                   // Levenshtein.bubbleSort();
                }
                else {
                    textAreaIndex=new TextArea();
                    textAreaIndex.setPromptText("Searching word is not in you text!");
                    textAreaIndex.setEditable(false);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Search word was founded!");
                    alert.setContentText("The founded word: '" + searchTextField.getText().toLowerCase() +"' is in you dictionary.");
                    alert.setTitle("Search word");

                    GridPane gridPane = new GridPane();
                    gridPane.setMaxWidth(Double.MAX_VALUE);
                    gridPane.add(textAreaIndex,0,0);
                    try {
                        search.indexSearching(searchTextField.getText().toLowerCase());
                    } catch (IOException ex) {
                        Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    alert.getDialogPane().setExpandableContent(gridPane);
                    alert.showAndWait();
                    //System.out.println("Hledane slovo "+searchTextField.getText().toLowerCase()+" se nachazi ve slovniku.");

                }
            }
        });
        HBox.setMargin(searchButton, new Insets(2));
        HBox.setMargin(searchTextField, new Insets(2, 5, 5, 5));
        controls.getChildren().addAll(searchButton, searchTextField);

        return controls;

    }

    private Node menu() {

        MenuBar bar = new MenuBar();

        Menu text = new Menu("_File");
        MenuItem textitem1 = new MenuItem("E_xit");
        textitem1.setOnAction(event -> Platform.exit());

        MenuItem textitem2 = new MenuItem("_Open File");
        textitem2.setOnAction(event -> data.uploadText());

        MenuItem textItem3 = new MenuItem("_Save File");

        text.getItems().addAll(textitem2, textItem3, textitem1);

        Menu dic = new Menu("_Dictionary");
        MenuItem dicItem1 = new MenuItem("_Open");
        dicItem1.setOnAction(event -> data.uploadDictionary());

        MenuItem dicItem2 = new MenuItem("_Save");

        dic.getItems().addAll(dicItem1, dicItem2);

        bar.getMenus().addAll(text, dic);

        return bar;
    }

}
