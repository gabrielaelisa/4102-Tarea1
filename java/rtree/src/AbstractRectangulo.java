import javafx.util.Pair;

public abstract class AbstractRectangulo {

    public Pair<Integer, Integer> p1;
    public Pair<Integer, Integer> p2;
    public Pair<Integer, Integer> p3;
    public Pair<Integer, Integer> p4;


    protected AbstractRectangulo(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2,
                                 Pair<Integer, Integer> p3, Pair<Integer, Integer> p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
    }
}
