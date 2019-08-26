package parser;

public class Node {

    private int open;
    private int close;

    public Node(int open, int close) {
        this.open = open;
        this.close = close;
    }

    public int getOpen() {
        return open;
    }

    public int getClose() {
        return close;
    }
}