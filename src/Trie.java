
/**
 * Created by Marek on 17. 11. 2016.
 */
import java.util.ArrayList;
import java.util.List;

public class Trie {

    public static Node root = new Node(null, false);
    public static int indexPotomka =0;

    static int getIndex(char x) {
        return ((int) x) - ((int) 'a');
    }

    static class Node {

        String data;
        boolean isLeaf;
        Node[] children;

        Node(String data, boolean leaf) {
            this.data = data;
            this.isLeaf = leaf;
            this.children = new Node[26];
        }

    }

    static void insert(String data, Node root) {
        if (data == null || data.length() == 0) {
            return;
        }
        Node child = root.children[getIndex(data.charAt(0))];
        if (child == null) {
            Node node = new Node(data.substring(0, 1), data.length() == 1);
            root.children[getIndex(data.charAt(0))] = node;

            if (data.length() > 1) {
                insert(data.substring(1, data.length()), node);

            }
        } else if (data.length() == 1) {
            child.isLeaf = true;
        } else {
            insert(data.substring(1, data.length()), child);
        }
    }

    static void vypis(Node root) {
        for (int i = 0; i < root.children.length; i++) {
            if (root.children[i] != null && root.children[i].isLeaf) {
                System.out.println(root.children[i].data + " is Leaf" + " i=" + i);
            }

            Node child = root.children[i];
            if (root.children[i] != null && child.children != null) {
                vypis(child);
            }
        }

    }

    public static int pocetPotomku(Node root) {
        int pocet = 0;
        for (int i = 0; i < root.children.length; i++) {
            if (root.children[i] != null) {
                pocet++;
                indexPotomka = i;
            }
        }
        return pocet;
    }

    /**
     *
     * @param root
     */
   /* static void komprimace(Node root) {
        for (Node children : root.children) {
            if (children != null) {
                Node child = children;
                if (child.children != null) {
                    for (Node children1 : child.children) {
                        if (pocetPotomku(child) == 1 && !child.isLeaf) {
                            if (children1 != null) {
                                if (children1.data == null) {
                                    return;
                                }
                                children1.data = child.data.concat(children1.data);
                                child.isLeaf = children1.isLeaf;
                                child.data = null;
                                komprimace(child);
                            } else if (child.children != null && pocetPotomku(child) == 1
                                    && child.isLeaf && children1 != null) {
                                komprimace(children1);
                            }
                        } else if (pocetPotomku(child) > 1 && children1 != null
                                && !children1.isLeaf) {
                            komprimace(child);
                        }
                    }
                }
            } //                       bear bell bid bull buy sell stock stop
        }
    }*/
    public static void komprimace(Node root){
        for (int i = 0; i < root.children.length; i++) {
            if (root.children[i] != null) {
               // System.out.println(root.children[0].data);
                linkNode(root.children[i]);

            }
        }

    }
    public static void linkNode(Node child ){
        if (child != null && !child.isLeaf && pocetPotomku(child)==1) {
            child.data=child.data.concat(child.children[indexPotomka].data);
            child.isLeaf=child.children[indexPotomka].isLeaf;
            child.children=child.children[indexPotomka].children;
            System.out.println(child.data +" kontrola");
            System.out.println(root.children[0].data + " child.data");

           /* child.children[indexPotomka].data=child.data.concat(child.children[indexPotomka].data);
            child.data="";
            child=child.children[indexPotomka];
            System.out.println(child.data +" kontrola");
            System.out.println(root.children[0].data + " child.data");*/

            linkNode(child);

        }
        else if (child != null && child.isLeaf && pocetPotomku(child)==1) {
            linkNode(child.children[indexPotomka]);
        }
        else if (pocetPotomku(child)>1){
            komprimace(child);
        }
        return;
    }

    /*static boolean find(String data, Node root) {
        if (data == null || data.length() == 0) {
            return true;
        }
        char x = data.charAt(0);
        Node node = root.children[getIndex(x)];
        if (node == null) {
            return false;
        } else if (data.length() == 1) {
            return node.isLeaf;
        } else {
            return find(data.substring(1, data.length()), node);
        }
    }*/
    static boolean find (String data, Node root){
        if (data == null || data.length() == 0) {
            return true;
        }
        char x = data.charAt(0);
        Node node = root.children[getIndex(x)];
       // System.out.println(node.data + " - kontrola");

        for (int i = 1; i < data.length()+1; i++) {
            String s = data.substring(0,i);
            System.out.println(s);
            if (node.data.equals(s) && node.isLeaf && data.length()==node.data.length()){
                System.out.println("Shoda");
                return true;
            }
            else if (node.data.equals(s) && node.isLeaf) {
                System.out.println(data.substring(i) + " hledame");
                find(data.substring(i),node);
            }
            else {
                return false;
            }
        }
        return node.isLeaf;
    }

    public static void uploadDataToTrie() {

        for (int i = 0; i < Dictionary.treeSet.size(); i++) {
            Trie.insert(Dictionary.treeSet.toArray()[i].toString(), Trie.root);
        }

        komprimace(root);
        System.out.println(Trie.getAll().toString());
    }

    private static List<String> strings = new ArrayList<>();

    public static List<String> getAll() {
        strings = new ArrayList<>();
        findAllDFS(root, "");
        return strings;
    }

    private static void findAllDFS(Node node, String old) {
        String sOld = old;
        if (node != null) {
            if (!"".equals(node.data)) {
                sOld = sOld + node.data;
            }
            if (node.isLeaf) {
                strings.add(sOld);
            }
            for (Node node1 : node.children) {
                findAllDFS(node1, sOld);
            }
        }
    }

}
