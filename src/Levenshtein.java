
import java.util.Comparator;

/**
 * Created by Marek on 29. 11. 2016.
 */
public class Levenshtein implements Comparator<String> {

    String searchWord;

    static int levenshteinDistance(final String s, final String t) {

        if (s.length() == 0) {
            return t.length();
        }
        if (t.length() == 0) {
            return s.length();
        }

        return Math.min(
                Math.min((levenshteinDistance(s.substring(1), t) + 1), (levenshteinDistance(t.substring(1), s) + 1)),
                (levenshteinDistance(s.substring(1), t.substring(1)) + (s.charAt(0) != t.charAt(0) ? 1 : 0)));
    }

    public Levenshtein() {
        this.searchWord = UserInterface.searchTextField.getText().toLowerCase();
    }


    @Override
    public int compare(String first, String second) {
        return (levenshteinDistance(first, searchWord) - levenshteinDistance(second, searchWord));
    }

}
