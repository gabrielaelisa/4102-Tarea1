import java.io.Serializable;

public interface IRectangulo extends Serializable {
    int getId();

    boolean esDato();

    int getX();

    int getY();


    int getIdContainer();

    int ancho();

    int alto();

    void setContainer(int id);

    boolean contains2(int x, int y, int a);

    boolean contains(IRectangulo rec);

    public boolean intersects(IRectangulo rec);

    void ampliar(IRectangulo rec);

    void setidNodo(int id);

    double difArea(IRectangulo rec);

}
