import java.io.Serializable;
import java.util.ArrayList;

public interface INodo extends Serializable {

    boolean esHoja();

    int getId();

    IRectangulo getPadre();

    IRectangulo getRectangulo(int pos);

    void setRectangulo(int pos, IRectangulo rect);

    void appendRectangulo(IRectangulo rec);

    int cantidadRectangulos();

    void eliminar();

    void guardar();

    boolean isfull();

    boolean isEmpty();

    void eliminarRectangulo( IRectangulo rec);

    public IRectangulo popRectangulo();

    int indexRectangulo(IRectangulo r);

    IRectangulo popRectangulo(int i);

}
