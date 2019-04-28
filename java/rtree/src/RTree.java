import com.sun.corba.se.impl.resolver.INSURLOperationImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.*;
import java.util.ArrayList;

public class RTree implements Serializable{

    private int idRaiz;
    private int altura=0;
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
        current_node= new NodoHoja(popNextId(),rectangulo1);
        this.idRaiz= current_node.getId();
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

        if(reajustar){

        }
        else{
            current_node= this.u.leerNodo(idRaiz);
        };
    }

    public void GreeneSplit(IRectangulo rec){
        return;
    }

    // este linear split es un dummy, no usa la heurística

    public void DummySplit(IRectangulo rec){

        INodo nodo_izq;
        INodo nodo_der;

        if(current_node.esHoja()){
            nodo_izq= new NodoHoja(popNextId(),rec);
            nodo_der=new NodoHoja(popNextId(), current_node.getRectangulo(0));

            // aqui debería ir una heurística

            //todo se debe implementar un metodo que quite rectangulos de current node y los agregue a nodo izq o der
            //todo usar double dispatch

            for(int i=1; i< (int)current_node.cantidadRectangulos()/2 ; i++){
                nodo_izq.appendRectangulo(current_node.getRectangulo(i)); // current_node.popRectangle , para no saturar memoria
            }
            for(int i= (int)current_node.cantidadRectangulos()/2; i< current_node.cantidadRectangulos() ; i++){
                nodo_der.appendRectangulo(current_node.getRectangulo(i));
            }

            // ------------------------ terminan los ciclos ---------------------------------------------------------

            IRectangulo rect_izq= nodo_izq.getMbr();
            IRectangulo rect_der=nodo_der.getMbr();
            nodo_izq.setPadre(rect_izq);
            nodo_der.setPadre(rect_der);
            //mandamos nodo_izq y derecho a disco
            nodo_izq.guardar();
            nodo_der.guardar();


            // se debe actualizar el padre y agrerar rectangulos rect_izq y rect_der
            if(current_node.tienePadre()){
                IRectangulo padre= current_node.getPadre();
                // se debe destruir el archivo de current node
                current_node.eliminar();
                current_node= this.u.leerNodo(padre.getIdContainer());




            }
            // se debe crear nuevo nodo interno
            else{

                current_node.eliminar();

                //-----------------------------------------------------------------------

                NodoInterno nueva_raiz = new NodoInterno(this.popNextId(), rect_izq);
                current_node= nueva_raiz;
                this.idRaiz= nueva_raiz.getId();
                this.altura++;
                nueva_raiz.appendRectangulo(rect_der);

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
                this.actualizar();

            } else {

                if(this.split.equals("Linear"))this.DummySplit(dato);
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



