import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.*;
import java.util.ArrayList;


public abstract class AbstractNodo implements INodo {
    protected int M;
    private int id;
    protected ArrayList<IRectangulo> rectangulos= new ArrayList<>();


    protected AbstractNodo(int id, IRectangulo mbr, int M){
        this.id= id;
        this.M= M;
        this.appendRectangulo(mbr);

    }

    @Override
    public int getId(){

        return id;
    }


    @Override
    public IRectangulo getPadre() {
        IRectangulo mbr = getRectangulo(0);
        IRectangulo padre = new MBR(id, mbr.getX(), mbr.getY(), mbr.ancho(), mbr.alto());
        for (int i = 1; i < cantidadRectangulos(); i++) {
            padre.ampliar(getRectangulo(i));
        }
        return padre;
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
    public int cantidadRectangulos(){
        return rectangulos.size();
    }

    @Override
    public void appendRectangulo(IRectangulo rect){
        rectangulos.add(rect);
    }



    @Override
    public void guardar(String dir) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(dir + "n" + id + ".node"));
            out.writeObject(this);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    // esta funcion elimina el nodo de disco ya que va a ser reemplazado
    public void eliminar(String dir) {
        Path path = Paths.get(dir + "n" + id + ".node");

        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", dir + "n" + id + ".node");
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", dir + "n" + id + ".node");
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }

    @Override
    public boolean isfull(){
        return(this.rectangulos.size()>= M);
    }

    @Override
    public boolean isEmpty(){
        return this.rectangulos.size() == 0;
    }



    public void eliminarRectangulo( IRectangulo rec){
        this.rectangulos.remove(rec);
    }
    // popea el rectangulo en posicion 0 y lo elimina de la lista
    public IRectangulo popRectangulo(){
        IRectangulo rec =getRectangulo(0);
        this.rectangulos.remove(0);
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
