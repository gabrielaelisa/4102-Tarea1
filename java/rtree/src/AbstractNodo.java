import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public abstract class AbstractNodo implements INodo {

    protected int M= 1000000; // Cantidad maxima de datos (Rectangulos) en un nodo
    protected int m= 1000; // Cantidad minima de datos
    private int id;
    private MBR mbr;
    protected int indiceUltimo= -1; // Indice del ultimo Rectangulo
    protected ArrayList<IRectangulo> rectangulos= new ArrayList<>(M);
    private int idPadre;


    protected AbstractNodo(int id, MBR mbr){
        this.id= id;
        this.mbr= mbr;
    }

    @Override
    public int getId(){

        return id;
    }

    @Override
    public MBR getMbr(){
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

    boolean isfool(){
        return cantidadRectangulos()>= M;
    }
    
    @Override
    /* esta funcion agrega la linea que corresponde al padre de este nodo
     esto va a servir al momento de mantener el invariante 1*/
    public void addPadre(int padreID)
    {
        this.idPadre= padreID;
    }

}
