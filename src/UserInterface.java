
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
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
    private FileWriter fileWriter;

    private VirtualFlow flow;

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int LIST_MARGIN = 5;
    private final int LIST_PADDING = 2;
    TextField searchTextField;
    int i = 3;

    Data data = new Data();
    Dictionary dictionary = new Dictionary();
    Search search = new Search();

    @Override
    public void start(Stage primaryStage) throws Exception {

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

        return borderPane;

    }

    private Node dictionaryControl() throws IOException {

        HBox butts = new HBox();
        BuffWriter = new BufferedWriter(new FileWriter("Dictionary.txt"));
        listView.setCellFactory(TextFieldListCell.forListView());
        listView.setEditable(true);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> data.saveDictionary());

        saveButton.setPrefWidth(60);
        
        Button openButton = new Button("Open");
        openButton.setPrefWidth(60);
        openButton.setOnAction(event -> data.uploadDictionary());

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {

        });

        addButton.setPrefWidth(60);
        Button deleteButton = new Button("Delete");
        deleteButton.setPrefWidth(60);

        butts.getChildren().addAll(openButton, saveButton, addButton, deleteButton);
        butts.setPadding(new Insets(3));
        butts.setSpacing(10);

        return butts;
    }


    private Node textArea() {
        BorderPane borderPane = new BorderPane();
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPromptText("Write or upload your text.");
        textArea.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue) {

            } else {
                dictionary.createArray();
                Dictionary.nonDuplicatedArrayString();
            }
        });
        BorderPane.setMargin(textArea, new Insets(LIST_MARGIN, LIST_MARGIN / 2, LIST_MARGIN, LIST_MARGIN));
        borderPane.setCenter(textArea);
        borderPane.setBottom(textAreaControl());
        return borderPane;

    }


    private Node textAreaControl() {

        HBox controls = new HBox();

        Button searchButton = new Button("Search");

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Word for searching");

        searchButton.setOnAction(event -> {
            if (searchTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Write word for searching!");
                alert.setTitle("Error");
                alert.setHeaderText("Nothing for searching!");
                alert.showAndWait();

            } else {
                System.out.println(Trie.find(searchTextField.getText().toLowerCase(), Trie.root));
                search.indexSearching(searchTextField.getText().toLowerCase());
            }
        });
        HBox.setMargin(searchButton, new Insets(5));
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
