import java.io.*;
import INodo;
import NodoHoja;
import IRectangulo;

public class RTree implements Serializable{

    private int idRaiz;
    public string method;
    private int nextId= 0;
    public static final String DIR = "datos" + File.separator;
    public INode current_node;

    // constructor de la raiz, recibe un dato y crea el nodo contenedor
    public Rtree(IRectangulo rectangulo1){
        /* este es el nodo que tenemos actualmente cargado en memoria
         siempre habra un nodo cargado en memoria */
        current_node= NodoHoja(nextId,rectangulo);

    }

    public int getIdRaiz(){

        return idRaiz;
    }

    public void setIdRaiz(int idRaiz) {

        this.idRaiz = idRaiz;
    }

    // crea nuevo id disponible para un nuevo archivo
    public int popNextId()
    {

        return nextId++;
    }

    public void insertar(IRectangulo newrec){
        if(current_node.esHoja()){
            

        }


    }


}
