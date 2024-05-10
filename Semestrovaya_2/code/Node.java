package Semestrovka_2.code;

import java.util.Arrays;

public class Node {

    private final int M = 4;
    private int[] keys = new int[2 * M - 1];
    private Node[] children = new Node[2 * M];
    private int size;
    private boolean leaf;
    private Node parent;

    public Node(boolean leaf) {
        this.leaf = leaf;
    }

    public int[] getKeys() {
        return keys;
    }

    public Node[] getChildren() {
        return children;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String toString() {
        return Arrays.toString(keys);
    }
}
