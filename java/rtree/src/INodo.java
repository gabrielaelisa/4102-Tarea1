import java.io.Serializable;

public interface INodo extends Serializable {

    boolean esHoja();

    int getId();

    IRectangulo getPadre();

    IRectangulo getMbr();

    IRectangulo getRectangulo(int pos);

    void setRectangulo(int pos, IRectangulo rect);

    void appendRectangulo(IRectangulo rec);

    int cantidadRectangulos();

    void eliminar();

    void guardar();

    boolean isfull();

    public boolean tienePadre();

    public void setPadre(IRectangulo rec);

    IRectangulo target_rectangulo(IRectangulo rec, RTree tree);

    void eliminarRectangulo( IRectangulo rec);

    public IRectangulo popRectangulo();

}
