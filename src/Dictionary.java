
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
     * createTreeSet() Pomocí této metody odstraníme z textu nežádoucí znaky a
     * načteme do datového formátu TreeSet
     */
    public void createTreeSet() {
        String s = UserInterface.textArea.getText().toLowerCase();
        s = s.replaceAll("[^a-z]", " ");
        words = s.split("[[ ]*|[,]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+");

        treeSet.addAll(Arrays.asList(words));
        Trie.uploadDataToTrie();
    }

    /**
     * Vytvoří pole unikátních slov, ze kterých se vytvoří slovník.
     */
    public static void nonDuplicatedFilledList() {
        list.clear();
        Dictionary.treeSet.stream().forEach((s) -> {
            if (!s.equals(""))
                list.add(s);
        });
        UserInterface.listView.setItems(list);
    }
}
