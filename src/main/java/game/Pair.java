package game;

public class Pair<P1, P2> {
    private P1 left;
    private P2 right;


    public Pair(P1 p1, P2 p2) {
        left = p1;
        right = p2;
    }

    public P1 getLeft() {
        return left;
    }

    public void setLeft(P1 left) {
        this.left = left;
    }

    public P2 getRight() {
        return right;
    }

    public void setRight(P2 right) {
        this.right = right;
    }
}
