
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
     * uploadDictionary()
     * metoda, která otevře soubor.txt a načte z něj data do metody uploadFile()
     */
    public void uploadDictionary() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(UserInterface.primaryStage);
        if (file != null) {
            ObservableList<String> data = uploadFile(file);
            if ((data != null) && (data.size() > 0)) {
                UserInterface.listView.setItems(data);
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
     * Metoda otevře dialogové okno, pro pro načtení textového souboru do textArea.
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
     * uploadFile()
     * Metoda vytvoří pole, do kterého po řádcích nahraje vstupní data.
     * @param file
     * @return
     */
    public ObservableList<String> uploadFile(File file) {
        ObservableList<String> str = FXCollections.observableArrayList();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), "windows-1250"))) {

            String line;

            while ((line = in.readLine()) != null) {
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
     */
    public void saveDictionary(){
        try {
            UserInterface.BuffWriter = new BufferedWriter(new FileWriter("Dictionary.txt"));
            for (int i = 0; i < Dictionary.nonDuplicatedArrayString().length; i++) {
                UserInterface.BuffWriter.write(Dictionary.nonDuplicatedArrayString()[i]);
                UserInterface.BuffWriter.newLine();

            }

        } catch (IOException e) {
        } finally {
            try {
                if (UserInterface.BuffWriter != null) {
                    UserInterface.BuffWriter.close();
                }
            } catch (IOException e) {
            }
        }

    }

    public void saveFile() {

        try {
            UserInterface.BuffWriter = new BufferedWriter(new FileWriter("Dictionary.txt"));
            for (int i = 0; i < Dictionary.nonDuplicatedArrayString().length; i++) {
                UserInterface.BuffWriter.write(Dictionary.nonDuplicatedArrayString()[i]);
                UserInterface.BuffWriter.newLine();

            }

        } catch (IOException e) {
        } finally {
            try {
                if (UserInterface.BuffWriter != null) {
                    UserInterface.BuffWriter.close();
                }
            } catch (IOException e) {
            }
        }



    }
}
