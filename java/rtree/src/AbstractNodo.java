import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.*;
import java.util.ArrayList;


public abstract class AbstractNodo implements INodo {
    protected int M= 10;
    //protected int M= 1000000; // Cantidad maxima de datos (Rectangulos) en un nodo
    protected int m= 1000; // Cantidad minima de datos
    private int id;
    // corresponde al padre de este nodo
    private IRectangulo padre = null;
    private boolean tiene_padre= false;
    // corresponde al rectangulo que representa el area de este nodo y su futuro padre
    private IRectangulo mbr;
    protected int indiceUltimo= -1; // Indice del ultimo Rectangulo
    protected ArrayList<IRectangulo> rectangulos= new ArrayList<>(M);


    protected AbstractNodo(int id, IRectangulo mbr){
        this.id= id;
        this.mbr= new MBR(id, mbr.getX(), mbr.getY(), mbr.ancho(), mbr.alto());
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
    public IRectangulo getPadre() {return padre;}

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
        rect.setContainer(this.id);
        this.mbr.ampliar(rect);

    }

    @Override
    public int cantidadRectangulos(){

        return indiceUltimo+1;
    }

    @Override
    public void setPadre(IRectangulo rec){
        this.padre= rec;
        this.tiene_padre=true;
    }
    @Override
    public boolean tienePadre(){
        return this.tiene_padre;
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
    // esta funcion elimina el nodo de disco ya que va a ser reemplazado
    public void eliminar() {
        Path path = Paths.get(RTree.DIR + "n" + id + ".node");

        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", RTree.DIR + "n" + id + ".node");
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", RTree.DIR + "n" + id + ".node");
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }

    @Override
    public boolean isfull(){
        return cantidadRectangulos()>= M;
    }


    @Override
    /* esta funcion retorna el rectangulo por el cual debo descender en el caso de que quiera
     insertar un dato*/
    public IRectangulo target_rectangulo(IRectangulo dato, RTree tree){

        IRectangulo target_rec= null;
        int min_grow= Integer.MAX_VALUE;
        int min_area= Integer.MAX_VALUE;

        for (int x=0; x<this.cantidadRectangulos(); x++) {

            IRectangulo rec = this.getRectangulo(x);
            //este caso es facil, solo debo descender
            if (rec.contains(dato)) {
                target_rec = rec;
                break;

            }
            // aqui se debe escoger el MBR que crezca menos, para mantener el invariante 1
            else if (rec.intersects(dato)) {
                if (rec.difArea(dato) < min_grow) {
                    target_rec = rec;
                    min_area= rec.ancho()*rec.alto();
                }
                // en caso de empate se baja por el MBR que tenga menor area
                if(rec.difArea(dato)== min_grow){
                    if(rec.ancho()*rec.alto()<min_area) target_rec=rec;
                }
                // se debe recorrer el arbol de abajo hacia arriba para recuperar la invariante 1
                tree.reajustar= true;
            }

        }
        return target_rec;


    }

    public IRectangulo popNextRectangulo(){
        IRectangulo rec =null;
        //todo saca un rectangulo del arraylist y lo borra de la lista
        return rec ;
    }

    public void eliminarRectangulo( IRectangulo rec){
        this.rectangulos.remove(rec);
        indiceUltimo--;
    }
    // popea el rectangulo en posicion 0 y lo elimina de la lista
    public IRectangulo popRectangulo(){
        IRectangulo rec =getRectangulo(0);
        eliminarRectangulo(rec);
        return rec;


    }


}
