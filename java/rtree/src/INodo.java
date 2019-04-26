import java.io.Serializable;

public interface Nodo extends Serializable {

    boolean esHoja();

    int getId();

    MBR getMbr();

    Rectangulo getRectangulo(int pos);

    void setRectangulo(int pos, Rectangulo rect);

    void appendRectangulo(Rectangulo rec);

    int cantidadRectangulos();

    void guardar();

    boolean isfull();
}
