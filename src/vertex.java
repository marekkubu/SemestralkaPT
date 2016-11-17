/**
 * Created by Marek on 17. 11. 2016.
 */
public class Vertex {
    int words;
    int prefixes;
    String [] edges = new String[25];

    public Vertex(int words, int prefixes) {
        this.words = words;
        this.prefixes = prefixes;
        for (int i = 0; i <= edges.length; i++) {
        edges[i]= "";
        }
    }
    void printVertex(){
        System.out.println(prefixes+"");
    }
}
