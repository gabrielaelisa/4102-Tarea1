import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class NodoUtils {

    public static Nodo leerNodo(int id) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(RTree.DIR + "n" + id + ".node"));
            return (Nodo) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

}
