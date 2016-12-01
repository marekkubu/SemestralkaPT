
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Marek Kubů and Tomáš Dubina
 */
public class Search {

    Dictionary dictionary = new Dictionary();

    /**
     * Metoda pro vyhledávání zadaného slova. Vypíše a uloží nalezená slova do
     * soubouru. Včetně jejich počátečního a koncového indexu.
     *
     * @param wordToFind Slovo, které máme vyhledat v textu.
     */
    public void indexSearching(String wordToFind) throws IOException {
        String str = UserInterface.textArea.getText().toLowerCase();
        Pattern word = Pattern.compile(wordToFind);
        Matcher match = word.matcher(str);

        dictionary.createTreeSet();
        UserInterface.BuffWriter = new BufferedWriter(new FileWriter("searchOut.txt"));
        while (match.find()) {
            UserInterface.textAreaIndex.appendText("Searched word '" + wordToFind + "' index: " + match.start() + "-" + (match.end() - 1) + "\n");
            UserInterface.BuffWriter.write("Searched word '" + wordToFind + "' index: " + match.start() + "-" + (match.end() - 1));
            UserInterface.BuffWriter.newLine();

        }

        if (UserInterface.BuffWriter != null) {
            UserInterface.BuffWriter.close();
        }

    }
}
