import java.lang.ref.Reference;

/**
 * Created by Marek on 17. 11. 2016.
 */
public class Trie {
    String[] edges= new String[25];

    public void addWord(Vertex vrchol, String word){
        if (word.isEmpty() == true) {
            vrchol.words=vrchol.words+1;
        }
        else {
            vrchol.prefixes=vrchol.prefixes+1;
            char k = word.charAt(0);

            for (int i = 0; i < edges.length; i++) {

             if (edges[i].equals(k)==false) {

             }
            }
        }

    }
}
