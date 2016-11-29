
import java.io.IOException;
import java.lang.ref.Reference;

/**
 * Created by Marek on 17. 11. 2016.
 */
import java.util.ArrayList;
import java.util.List;

public class Trie {

    public static Node root = new Node("", false);

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
            //child.isLeaf = false;
            insert(data.substring(1, data.length()), child);
        }
    }

    static void vypis(Node root) {
        for (int i = 0; i < root.children.length; i++) {
            if (root.children[i] != null) {
                if (root.children[i].isLeaf == true) {
                    System.out.println(root.children[i].data + " is Leaf" + " i=" + i);
                }
            }

            Node child = root.children[i];
            if (root.children[i] != null) {
                //System.out.println(child.data);
                if (child.children != null) {
                    vypis(child);
                }
            }
        }

    }

    public static int pocetPotomku(Node root) {
        int pocet = 0;
        for (int i = 0; i < root.children.length; i++) {
            if (root.children[i] != null) {
                pocet++;
            }
        }
        return pocet;
    }

    static void komprimace(Node root) {
        for (Node children : root.children) {
            if (children != null) {
                Node child = children;
                if (child.children != null) {
                    for (Node children1 : child.children) {
                        if (pocetPotomku(child) == 1 && child.isLeaf == false) {
                            if (children1 != null) {
                                if (children1.data == null) {
                                    return;
                                }
                                children1.data = child.data.concat(children1.data);
                                child.isLeaf = children1.isLeaf;
                                child.data = null;
                                komprimace(child);
                            } else if (child.children != null && pocetPotomku(child) == 1 && child.isLeaf == true) {
                                if (children1 != null) {
                                    komprimace(children1);
                                }
                            }
                        } else if (pocetPotomku(child) > 1) {
                            if (children1 != null && children1.isLeaf == false) {
                                komprimace(child);
                            }
                        }
                    }
                }
            } //                       bear bell bid bull buy sell stock stop
        }
    }

    static boolean find(String data, Node root) {
        if (data == null || data.length() == 0) {
            return true;
        }
        char x = data.charAt(0);
        //note that first node ie root is just dummy, it just holds important
        Node node = root.children[getIndex(x)];
        if (node == null) {
            return false;
        } else if (data.length() == 1) {
            return node.isLeaf;
        } else {
            return find(data.substring(1, data.length()), node);
        }
    }

    public static void uploadDataToTrie() {

        for (int i = 0; i < Dictionary.treeSet.size(); i++) {
            Trie.insert(Dictionary.treeSet.toArray()[i].toString(), Trie.root);
        }
    }


    /* static boolean delete(String data, Node root) {
        if (data == null || data.length() == 0) {
            return false;
        }
        char x = data.charAt(0);
        //note that first node ie root is just dummy, it just holds important
        Node node = root.children[getIndex(x)];
        if (node == null) {
            return false;
        } else {
            if (data.length() == 1) {
                node.isLeaf = false;
                boolean allNull = true;
                for (Node node1 : node.children) {
                    allNull = allNull && node1 == null;
                }
                return allNull;
            } else {
                boolean delete = delete(data.substring(1, data.length()), node);
                if (delete) {
                    node.children[getIndex(x)] = null;
                    if(node.isLeaf){
                        return false;
                    }
                    boolean allNull = true;
                    for (Node node1 : node.children) {
                        allNull = allNull && node1 == null;
                    }
                    return allNull;                }
            }
        }
        return false;
    }*/
    private static List<String> strings = new ArrayList<>();

    public static List<String> getAll() {
        strings = new ArrayList<String>();
        findAllDFS(root, "");
        return strings;
    }

    private static void findAllDFS(Node node, String old) {
        if (node != null) {
            if (node.data != "") {
                old = old + node.data;
            }
            if (node.isLeaf) {
                strings.add(old);
            }
            for (Node node1 : node.children) {
                findAllDFS(node1, old);
            }
        }
    }

}
