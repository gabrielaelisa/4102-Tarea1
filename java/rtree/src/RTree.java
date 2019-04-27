import com.sun.corba.se.impl.resolver.INSURLOperationImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;

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
    public String split;

    // constructor de la raiz, recibe un dato y crea el nodo contenedor
    public RTree(Dato rectangulo1, String split){
        /* este es el nodo que tenemos actualmente cargado en memoria
         siempre habra un nodo cargado en memoria */
        this.split =split;
        current_node= new NodoHoja(0,rectangulo1);
        // agregamos el campo contenedor del nodo rectangulo
        rectangulo1.SetContainer(current_node.getId());
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

    public void actualizar(){
        return;
    }

    public void GreeneSplit(IRectangulo rec){
        return;
    }

    // este lienar split es un dummy, no usa la heurística

    public void LinearSplit(IRectangulo rec){

        INodo nodo_izq;
        INodo nodo_der;

        if(current_node.esHoja()){
            nodo_izq= new NodoHoja(popNextId(),rec);

            nodo_der=new NodoHoja(popNextId(), current_node.getRectangulo(0));

            for(int i=1; i< (int)current_node.cantidadRectangulos()/2 ; i++){
                nodo_izq.appendRectangulo(current_node.getRectangulo(i));
            }
            for(int i= (int)current_node.cantidadRectangulos()/2; i< current_node.cantidadRectangulos() ; i++){
                nodo_der.appendRectangulo(current_node.getRectangulo(i));
            }

        }

        else{

        }




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

    /* esta funcion recibe el dato que se desea insertar y verifica si el nodo actual cargado en memoria
    * tiene espacio para almacenarlo*/

    public void insertar_dato(IRectangulo dato){

        // es una hoja, entonces intentamos insertarlo
        if(current_node.esHoja()) {
            // la hoja tiene espacio
            if (!current_node.isfull()) {
                current_node.appendRectangulo(dato);
                dato.SetContainer(current_node.getId());
                this.actualizar();

            } else {

                if(this.split.equals("Linear"))this.LinearSplit(dato);
                else this.GreeneSplit(dato);

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
            this.insertar(dato); // se repite el paso anterior pero usando un nuevo nodo de memoria
        }



    }

    public void insertar_MBR(IRectangulo mbr){

    }


    }



