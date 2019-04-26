import javafx.util.Pair;

public class Main {

    public static void main(String[] args) {
        RTree tree= new RTree("mi_tree");
        int idNodo = tree.popNextId();
        MBR mbrHoja= new MBR(idNodo, new Pair<>(0,1), new Pair<>(1,1), new Pair<>(1, 0), new Pair<>(0,0));
        Dato d1= new Dato(new Pair<>(0,1), new Pair<>(1,1), new Pair<>(1, 0), new Pair<>(0,0));
        Nodo nodo = new NodoHoja(idNodo, mbrHoja);
        ((NodoHoja) nodo).appendDato(d1);
        tree.saveNodo(nodo);
        idNodo= tree.popNextId();
        MBR mbrHoja2= new MBR(idNodo, new Pair<>(3,1), new Pair<>(4,1), new Pair<>(4, 0), new Pair<>(3,0));
        Dato d2= new Dato(new Pair<>(3,1), new Pair<>(4,1), new Pair<>(4, 0), new Pair<>(3,0));
        nodo= new NodoHoja(idNodo, mbrHoja2);
        ((NodoHoja) nodo).appendDato(d1);
        tree.saveNodo(nodo);
        idNodo= tree.popNextId();
        nodo= new NodoInterno(idNodo, null);
        tree.setIdRaiz(idNodo);
        ((NodoInterno) nodo).appendMBR(mbrHoja);
        ((NodoInterno) nodo).appendMBR(mbrHoja2);
        System.out.println(((NodoInterno) nodo).cantidadMBRs());
        try{
            nodo= tree.getNodo(mbrHoja.getIdNodo());
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(((NodoHoja) nodo).getDato(0).p1.getKey());

    }
}
