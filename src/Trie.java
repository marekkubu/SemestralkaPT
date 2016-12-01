
/**
 * Created by Marek on 17. 11. 2016.
 */

public class Trie {
    /**
     * Hlavni uzel TRIE
     */
    public static Node root;

    /**
     * Globalni promenna, ktera zaznamenava aktualni pozici nalezeneho potomka uzlu
     */
    public static int indexPotomka = 0;

    /**
     * Ze znaku ziskame pozici
     * @param x
     * @return 0-25
     */
    static int getIndex(char x) {
        return ((int) x) - ((int) 'a');
    }

    /**
     * Vytvoření uzle
     */
    static class Node {

        String data;
        boolean isLeaf;
        Node[] children;

        /**
         * Konstruktor uzle
         * @param data Cast nebo cele slovo
         * @param leaf Oznaceni konce slova
         */
        Node(String data, boolean leaf) {
            this.data = data;
            this.isLeaf = leaf;
            this.children = new Node[26];
        }

    }

    /**
     * Vlozeni slova do trie
     * @param data Slovo k vlozeni
     * @param root Aktualni uzel
     */
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

    /**
     * Zjisti pocet potomku
     * @param root Aktualni uzel
     * @return Pocet potomku
     */
    static int pocetPotomku(Node root) {
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
     * Komprimuje trii, vola funci linkNode
     * @param root Aktualni uzel
     */
    static void komprimace(Node root) {
        for (Node children : root.children) {
            if (children != null) {
                linkNode(children);
            }
        }

    }

    /**
     * Vnorena metoda v metode komprimace
     * @param child Aktualni uzel
     */
    static void linkNode(Node child) {
        if (child != null && !child.isLeaf && pocetPotomku(child) == 1) {
            child.data = child.data.concat(child.children[indexPotomka].data);
            child.isLeaf = child.children[indexPotomka].isLeaf;
            child.children = child.children[indexPotomka].children;
            linkNode(child);

        } else if (child != null && child.isLeaf && pocetPotomku(child) == 1) {
            linkNode(child.children[indexPotomka]);
        } else if (pocetPotomku(child) > 1) {
            komprimace(child);
        }
    }

    /**
     * Vyhledavani pozadovaneho slova v komprimovane trii
     * @param data Vyhledavane slovo
     * @param root Aktualni uzel
     * @return true or false
     */
    static boolean find(String data, Node root) {
        Node node;

        char x = data.charAt(0);

        if (root.children[getIndex(x)] == null) {
            return false;
        } else {
            node = root.children[getIndex(x)];
            for (int i = 1; i < data.length() + 1; i++) {
                String s = data.substring(0, i);
                if (node.data.equals(s) && node.isLeaf && data.length() == node.data.length()) {
                    return true;
                } else if (node.data.equals(s) && pocetPotomku(node) >= 1) {
                    return find(data.substring(i), node);
                }

            }
            return false;
        }
    }

    /**
     * Nacte data ze slovniku do trie a zavola komprimaci
     */
    public static void uploadDataToTrie() {
        root = new Node(null, false);
        for (int i = 0; i < Dictionary.treeSet.size(); i++) {
            Trie.insert(Dictionary.treeSet.toArray()[i].toString(), Trie.root);
        }
        komprimace(root);
    }
}
