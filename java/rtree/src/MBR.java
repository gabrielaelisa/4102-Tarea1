import javafx.util.Pair;

public class MBR extends AbstractRectangulo {

    private int idNodo; // id del Nodo al que apunta


    public MBR(int idNodo, Pair<Integer, Integer> p1, Pair<Integer, Integer> p2,
                  Pair<Integer, Integer> p3, Pair<Integer, Integer> p4) {
        super(p1, p2, p3, p4);
        this.idNodo= idNodo;
    }

    public int getIdNodo(){
        return idNodo;
    }
}
