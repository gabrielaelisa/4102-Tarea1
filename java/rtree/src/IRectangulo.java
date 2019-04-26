import java.io.Serializable;

public interface IRectangulo extends Serializable {

    int getX();

    int getY();

    int ancho();

    int alto();

    boolean isContain(IRectangulo rec);

}
