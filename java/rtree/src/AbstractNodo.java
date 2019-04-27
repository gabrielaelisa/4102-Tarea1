import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public abstract class AbstractNodo implements INodo {

    protected int M= 1000000; // Cantidad maxima de datos (Rectangulos) en un nodo
    protected int m= 1000; // Cantidad minima de datos
    private int id;
    // corresponde al padre de este nodo
    private IRectangulo padre = null;
    private IRectangulo mbr;
    protected int indiceUltimo= -1; // Indice del ultimo Rectangulo
    protected ArrayList<IRectangulo> rectangulos= new ArrayList<>(M);


    protected AbstractNodo(int id, IRectangulo mbr){
        this.id= id;
        this.mbr= mbr;
        this.appendRectangulo(mbr);
    }

    @Override
    public int getId(){

        return id;
    }

    @Override
    public IRectangulo getMbr(){
        return mbr;
    }

    @Override
    public IRectangulo getRectangulo(int pos){

        return rectangulos.get(pos);
    }

    @Override
    public void setRectangulo(int pos, IRectangulo rect){

        rectangulos.set(pos, rect);
    }

    @Override
    public void appendRectangulo(IRectangulo rect){

        rectangulos.add(++indiceUltimo, rect);
    }

    @Override
    public int cantidadRectangulos(){

        return indiceUltimo+1;
    }

    @Override
    public void guardar() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(RTree.DIR + "n" + id + ".node"));
            out.writeObject(this);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public boolean isfull(){
        return cantidadRectangulos()>= M;
    }


}
