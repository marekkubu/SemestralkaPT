
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

/**
 * Created by Marek on 14. 10. 2016.
 */
public class Dictionary {

    static Set<String> treeSet = new TreeSet<>();
    static String[] words;
    static ObservableList<String> list = FXCollections.observableArrayList();

    /**
     * createArray()
     * Pomocí této metody odstraníme z textu nežádoucí znaky a načteme do datového formátu TreeSet
     */
    public void createArray() {
        String s = UserInterface.textArea.getText().toLowerCase();
        s = s.replaceAll("[^a-zěščřžýáíéůú]", " ");
        words = s.split("[[ ]*|[,]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+");

        treeSet.addAll(Arrays.asList(words));
        System.out.println(treeSet);
    }

    /**
     * Vytvoří pole unikátních slov, ze kterých se vytvoří slovník.
     * @return
     */
    public static void nonDuplicatedArrayString() {
         list.clear();
        for (String s: Dictionary.treeSet) {
            list.add(s);
        }
        UserInterface.listView.setItems(list);
    }
}
