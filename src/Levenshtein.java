import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marek on 29. 11. 2016.
 */
public class Levenshtein {

    static int levenshteinDistance (final String s,final  String t) {

        if (s.length()==0) return t.length();
        if (t.length() ==0) return s.length();

        return Math.min(
                Math.min((levenshteinDistance(s.substring(1), t) + 1),(levenshteinDistance(t.substring(1), s) + 1)),
                (levenshteinDistance(s.substring(1), t.substring(1)) + (s.charAt(0)!=t.charAt(0) ? 1:0))) ;
    }
    public static void vypis(){
       /* String searchWord = UserInterface.searchTextField.getText().toLowerCase().toString();
        for(String w : Dictionary.treeSet){
            System.out.println("Slovo: "+ w + " - "+Levenshtein.levenshteinDistance(w,searchWord));
        }*/

    }
    public static void bubbleSort(){
        ArrayList<String> array = new ArrayList<>(Dictionary.treeSet);
        String searchWord = UserInterface.searchTextField.getText().toLowerCase().toString();
        for (int i = 0; i < array.size() - 1; i++) {
            for (int j = 0; j < array.size() - i - 1; j++) {
                if(Levenshtein.levenshteinDistance(array.get(j),searchWord) > Levenshtein.levenshteinDistance(array.get(j+1),searchWord)){
                    String tmp = array.get(j);
                    array.set(j,array.get(j+1));
                    array.set(j+1,tmp);
                }
            }
        }
        if (array.size() < 10) {
            for (int i = 0; i <= array.size()-1; i++) {
                System.out.println(array.get(i).toString());
            }
        }
        else {
            for (int i = 0; i < 10; i++) {
                System.out.println(array.get(i).toString());
            }
        }
    }
}