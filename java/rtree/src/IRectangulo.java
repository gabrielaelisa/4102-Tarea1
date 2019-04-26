import java.io.Serializable;

public interface IRectangulo extends Serializable {

    int getX();

    int getY();

    int getIdNodo();

    int ancho();

    int alto();

    boolean contains2(int x, int y, int a);

    boolean contains(IRectangulo rec);

    boolean intersects(IRectangulo rec);

}
