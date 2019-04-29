import java.io.Serializable;
import java.util.ArrayList;

public interface INodo extends Serializable {

    boolean esHoja();

    int getId();

    IRectangulo getPadre();

    IRectangulo getRectangulo(int pos);

    public int getIndiceUltimo();

    void setRectangulo(int pos, IRectangulo rect);

    void appendRectangulo(IRectangulo rec);

    int cantidadRectangulos();

    public ArrayList<Integer> indices_hijos();

    void eliminar();

    void guardar();

    boolean isfull();

    boolean isEmpty();

    public boolean tienePadre();

    public void setPadre(int id);

    IRectangulo target_rectangulo(IRectangulo rec, RTree tree);

    void eliminarRectangulo( IRectangulo rec);

    public IRectangulo popRectangulo();

}
