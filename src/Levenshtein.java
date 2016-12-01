
import java.util.Comparator;

/**
 * Created by Marek on 29. 11. 2016.
 */
public class Levenshtein implements Comparator<String> {

    String searchWord;

    /**
     * Metoda levenshteinDistance vypočítává levenštejnovu metriku ze dvou zadaných slov
     * @param s první slovo
     * @param t druhé slovo 
     * @return vrací se hodnota metriky
     */
    static int levenshteinDistance(final String s, final String t) {

        if (s == t) {
            return 0;
        }
        if (s.length() == 0) {
            return t.length();
        }
        if (t.length() == 0) {
            return s.length();
        }

        int[] v0 = new int[t.length() + 1];
        int[] v1 = new int[t.length() + 1];

        for (int i = 0; i < v0.length; i++) {
            v0[i] = i;
        }

        for (int i = 0; i < s.length(); i++) {
            v1[0] = i + 1;
            for (int j = 0; j < t.length(); j++) {
                int cost = (s.charAt(i) == t.charAt(j)) ? 0 : 1;
                v1[j + 1] = Math.min(Math.min(v1[j] + 1, v0[j + 1] + 1), v0[j] + cost);
            }
            System.arraycopy(v1, 0, v0, 0, v0.length);
        }

        return v1[t.length()];

    }

    /**
     *Metoda  Levenshtein() vloží do proměnné searchWord obsah text labelu
     */
    public Levenshtein() {
        this.searchWord = UserInterface.searchTextField.getText().toLowerCase();
    }

    /**
     * Metoda compare porovnává dvě hodnoty Levenštejnovi metriky a vrací jejich rozdíl
     * @param first první slovo 
     * @param second druhé slovo 
     * @return vrací se rozdíl hodnot Lev. metriky
     */
    @Override
    public int compare(String first, String second) {
        return (levenshteinDistance(first, searchWord) - levenshteinDistance(second, searchWord));
    }

}
