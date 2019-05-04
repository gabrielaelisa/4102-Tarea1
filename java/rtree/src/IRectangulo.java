import java.io.Serializable;

public interface IRectangulo extends Serializable {

    int getId();

    boolean esDato();

    int getX();

    int getY();

    int ancho();

    int alto();

    boolean contains(IRectangulo rec);

    boolean intersects(IRectangulo rec);

    void ampliar(IRectangulo rec);

    double difArea(IRectangulo rec);

}
