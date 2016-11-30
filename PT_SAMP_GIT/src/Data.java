
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.*;

/**
 * Created by Marek on 14. 10. 2016.
 */
public class Data {

    /**
     * uploadDictionary() metoda, která otevře soubor.txt a načte z něj data do
     * metody uploadFile()
     */
    public void uploadDictionary() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(UserInterface.primaryStage);
        uploadMyFile(file);

    }

    public static void uploadMyFile(File file) {
        if (file != null) {
            ObservableList<String> data = uploadFile(file);
            if ((data != null) && (data.size() > 0)) {
                UserInterface.listView.setItems(data);
                Dictionary.treeSet.addAll(data);
                Trie.uploadDataToTrie();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Click OK and continue");
                alert.setTitle("Loading error");
                alert.setHeaderText("Dictionary.txt not found!");
                alert.showAndWait();
            }
        }
    }

    /**
     * Metoda otevře dialogové okno, pro pro načtení textového souboru do
     * textArea.
     */
    public void uploadText() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(UserInterface.primaryStage);
        if (file != null) {
            ObservableList<String> data = uploadFile(file);
            if ((data != null) && (data.size() > 0)) {
                UserInterface.textArea.setText(data.toString());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Nepodarilo se nacist zadna data ze souboru!");
                alert.setTitle("Loading error");
                alert.setHeaderText("Chyba souboru!");
                alert.showAndWait();
            }
        }

    }

    /**
     * uploadFile() Metoda vytvoří pole, do kterého po řádcích nahraje vstupní
     * data.
     *
     * @param file
     * @return
     */
    public static ObservableList<String> uploadFile(File file) {
        ObservableList<String> str = FXCollections.observableArrayList();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), "windows-1250"))) {

            String line;

            while ((line = in.readLine()) != null) {
                if(!line.equals(""))
                str.add(line);
            }

        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return str;
    }

    /**
     * Metoda, která uloží obsah slovníku do Dictionary.txt.
     *
     * @throws java.io.IOException
     */
    public void saveDictionary() throws IOException {
        Dictionary dictionary = new Dictionary();
        // dictionary.createTreeSet();
        UserInterface.BuffWriter = new BufferedWriter(new FileWriter("Dictionary.txt"));
        for (String s : Dictionary.treeSet) {
            UserInterface.BuffWriter.write(s);
            UserInterface.BuffWriter.newLine();
        }
        if (UserInterface.BuffWriter != null) {
            UserInterface.BuffWriter.close();
        }
    }
}
