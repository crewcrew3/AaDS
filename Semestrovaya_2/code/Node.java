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

    public int find(int k) {
        for (int i = 0; i < this.size; i++) {
            if (this.keys[i] == k) {
                return i;
            }
        }
        return -1;
    }

    public Integer findIndexOfChild(Node node) {
        for (int i = 0; i < this.children.length; i++) {
            if (children[i] == node) {
                return i;
            }
        }
        return null;
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

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String toString() {
        return Arrays.toString(keys);
    }
}
