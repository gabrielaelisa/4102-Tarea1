import java.io.Serializable;

public interface IRectangulo extends Serializable {

    boolean esDato();

    int getX();

    int getY();

    int getIdNodo();

    int ancho();

    int alto();

    void SetContainer(int id);

    boolean contains2(int x, int y, int a);

    boolean contains(IRectangulo rec);

    boolean intersects(IRectangulo rec);

    int interseccion(IRectangulo rec);

    void ampliar(IRectangulo rec);

    IRectangulo popNextRectangulo();

}
