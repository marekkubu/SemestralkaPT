
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

/**
 * Created by Marek on 14. 10. 2016.
 */
public class Dictionary {

    Set<String> treeSet = new TreeSet<String>();
    static String[] words;
    static ObservableList<String> dictionaryWords = FXCollections.observableArrayList();

    /**
     * createArray()
     * Pomocí této metody odstraníme z textu nežádoucí znaky a načteme do datového formátu TreeSet
     */
    public void createArray() {
        String s = UserInterface.textArea.getText().toLowerCase();
        s = s.replaceAll("[^a-zěščřžýáíéůú]", " ");
        words = s.split("[[ ]*|[,]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+");

        for (String token : words) {
            treeSet.add(token);
        }
        System.out.println(treeSet);
    }

    /**
     * Vytvoří pole unikátních slov, ze kterých se vytvoří slovník.
     * @return
     */
    public static String[] nonDuplicatedArrayString() {
        dictionaryWords.clear();
        Arrays.sort(words);
        Set<String> set = new LinkedHashSet<>(Arrays.asList(words));
        String[] result = new String[set.size()];
        set.toArray(result);

        for (int i = 0; i < set.size(); i++) {
            dictionaryWords.add(result[i]);
        }

        UserInterface.listView.setItems(dictionaryWords);
        return set.toArray(result);
    }
}
