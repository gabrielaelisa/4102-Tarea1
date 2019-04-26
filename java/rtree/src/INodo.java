import java.io.Serializable;

public interface INodo extends Serializable {

    boolean esHoja();

    int getId();

    MBR getMbr();

    IRectangulo getRectangulo(int pos);

    void setRectangulo(int pos, IRectangulo rect);

    void appendRectangulo(IRectangulo rec);

    int cantidadRectangulos();

    void guardar();

    boolean isfull();

    void addPadre(int id);
}
