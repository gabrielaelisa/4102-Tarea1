import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.*;
import java.util.ArrayList;


public abstract class AbstractNodo implements INodo {
    protected int M;
    protected int m; // Cantidad minima de datos
    private int id;
    private int cantidad_hijos=0;
    private boolean tiene_padre= false;
    public IRectangulo padre; // corresponde al rectangulo que representa el area de este nodo y su futuro padre
    protected int indiceUltimo= -1; // Indice del ultimo Rectangulo
    protected ArrayList<IRectangulo> rectangulos= new ArrayList<>(M);


    protected AbstractNodo(int id, IRectangulo mbr, int M, int m){
        this.id= id;
        this.padre= new MBR(id, mbr.getX(), mbr.getY(), mbr.ancho(), mbr.alto());
        this.M= M;
        this.m = m;
        this.appendRectangulo(mbr);

    }

    @Override
    public int getId(){

        return id;
    }
    public int getIndiceUltimo(){
        return indiceUltimo;
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
    public void setPadre(int id){
        this.tiene_padre=true;
    }

    @Override
    public void appendRectangulo(IRectangulo rect){
        rectangulos.add(++this.indiceUltimo, rect);
        cantidad_hijos+=1;
        this.padre.ampliar(rect);
    }

    @Override
    public int cantidadRectangulos(){

        return cantidad_hijos;
    }


    @Override
    public boolean tienePadre(){
        return this.tiene_padre;
    }

    @Override
    // esta funcion retorna la lista de los indices de los archivos al cual apunta el Nodo
    public ArrayList<Integer> indices_hijos(){
        ArrayList<Integer> indices= new ArrayList<Integer>();
        for(int i= 0; i<this.indiceUltimo; i++){
            IRectangulo rec= getRectangulo(i);
            indices.add(rec.getId());
        }
        return indices;

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
    public boolean isEmpty(){
        return cantidadRectangulos() == 0;
    }




    public void eliminarRectangulo( IRectangulo rec){
        this.rectangulos.remove(rec);
        indiceUltimo--;
        cantidad_hijos-=1;
    }
    // popea el rectangulo en posicion 0 y lo elimina de la lista
    public IRectangulo popRectangulo(){
        IRectangulo rec =getRectangulo(0);
        this.rectangulos.remove(0);
        indiceUltimo--;
        cantidad_hijos-=1;
        return rec;
    }

    //retorna indice de un rectangulo en la lista de rectangulos del nodo 
    public int indexRectangulo(IRectangulo r){
        int i = this.rectangulos.indexOf(r);
        return i;
    }

    //popea el rectangulo en posicion i  y lo elimina de la lista
    public IRectangulo popRectangulo(int i){
        IRectangulo rec =getRectangulo(i);
        eliminarRectangulo(rec);
        return rec;
    }

}
