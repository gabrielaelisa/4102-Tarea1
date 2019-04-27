import java.io.*;
import java.util.ArrayList;

public class RTree implements Serializable{

    private int idRaiz;
    private int nextId= 0;
    public static final String DIR = "datos" + File.separator;
    public INodo current_node;
    // indica cuando es necesario recorrer de hoja a raiz para mantener invariante 1
    public boolean reajustar= false;
    protected NodoUtils u = new NodoUtils();

    // constructor de la raiz, recibe un dato y crea el nodo contenedor
    public RTree(Dato rectangulo1){
        /* este es el nodo que tenemos actualmente cargado en memoria
         siempre habra un nodo cargado en memoria */
        current_node= new NodoHoja(0,rectangulo1);
        nextId++;
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

    // recorrer el nodo desde la ultima hoja a la raiz, para recuperar invariante 1

    public void actualiar(){
        return;
    }


    public void linearSplit(IRectangulo dato){


    }

    public void insertar(IRectangulo newrec){
        // estamos insertando un dato
        if(newrec.esDato()){
            insertar_dato(newrec);
        }

        // estamos insertando una mbr
        else{
            insertar_MBR(newrec);
        }


        
    }

    public void insertar_dato(IRectangulo dato){

        // es una hoja, entonces intentamos insertarlo
        if(current_node.esHoja()) {
            if (!current_node.isfull()) {
                current_node.appendRectangulo(dato);
                this.actualiar();

            } else {
                this.linearSplit(dato);
            }

        }


        // es un nodo interno, debo comparar con cada rectangulo
        else
        {
            //rectángulo que aumenta menos su MBR

            IRectangulo target_rec= current_node.target_rectangulo(dato, this);

            // envío nodo a disco, traigo nuevo nodo de disco y nodo es recolectado por gc

            current_node.guardar();
            current_node= this.u.leerNodo(target_rec.getIdNodo());
            // se repite el paso anterior pero usando un nuevo nodo de memoria
            this.insertar(dato);
        }



    }

    public void insertar_MBR(IRectangulo mbr){

    }


    }



