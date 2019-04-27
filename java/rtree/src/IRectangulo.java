import java.io.Serializable;

public interface IRectangulo extends Serializable {
    int getId();

    int getX();

    int getY();

    int ancho();

    int alto();

    public boolean contains(IRectangulo rec);

    public boolean intersects(IRectangulo rec);

}
