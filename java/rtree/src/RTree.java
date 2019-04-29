import com.sun.corba.se.impl.resolver.INSURLOperationImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.util.ArrayList;

public class RTree implements Serializable{

    private int idRaiz;
    private int altura=0;
    private int nextId= 0;
    public static final String DIR = "datos" + File.separator;
    public INodo current_node;
    public boolean en_memoria_principal= true;
    // indica cuando es necesario recorrer de hoja a raiz para mantener invariante 1
    protected NodoUtils u = new NodoUtils();
    public String split;
    public boolean reajustar=false;

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

    /* este metodo debe recorre iterativamente
     nodo desde la ultima hoja a la raiz, para recuperar invariante 1

     */

    public void actualizar(){

        //current_node= this.u.leerNodo(idRaiz);

        /*if(reajustar){

        }
        else{
            current_node= this.u.leerNodo(idRaiz);
        };*/
    }

    public void GreeneSplit(IRectangulo rec){
        return;
    }

    // este linear split es un dummy, no usa la heurística

    public void heurística(INodo split, INodo izq, INodo der){

    }

    public void DummySplit(IRectangulo rec){
        System.out.println("splitting");
        INodo nodo_izq= null;
        INodo nodo_der=null;

        if(current_node.esHoja()){
            nodo_izq= new NodoHoja(popNextId(),rec);
            nodo_der=new NodoHoja(popNextId(), current_node.popRectangulo());
        }
        else {
            nodo_izq= new NodoInterno(popNextId(),rec);
            nodo_der=new NodoInterno(popNextId(), current_node.popRectangulo());

        }

        //-------------------------- aqui debería ir una heurística-------------------------------------------

        int len= new Integer(current_node.cantidadRectangulos());
        for(int i=0; i< (int)len/2 ; i++){
            nodo_izq.appendRectangulo(current_node.popRectangulo());
        }
        for(int i= len/2; i< len ; i++){
            nodo_der.appendRectangulo(current_node.popRectangulo());
        }


        // ------------------------ terminan los ciclos ---------------------------------------------------------


        // caso 1:  el nodo al que se le hace split es una hoja
        if(current_node.esHoja()){

            //caso 1.1 se trata de la raiz
            if (!current_node.tienePadre()){
                // se debe crear nuevo nodo interno
                current_node.eliminar();
                current_node= null; // lo eliminamos de memoria
                nodo_izq.setPadre(this.idRaiz);
                nodo_der.setPadre(this.idRaiz);

                NodoInterno nueva_raiz = new NodoInterno(this.idRaiz, nodo_izq.getPadre()); // usamos el mismo id del nodo anterior
                nueva_raiz.appendRectangulo(nodo_der.getPadre());
                this.altura++;

                nodo_izq.guardar();
                nodo_der.guardar();
                nueva_raiz.guardar();
            }

            // caso 1.2  es un nodo con padre
            else{

                System.out.println("tiene padre\n");
                IRectangulo padre= current_node.getPadre();
                current_node.eliminar(); // se debe destrulle el archivo de current node
                /* antes de traer el nodo padre a memoria principal debo dejar de tener a los hijos en RAM*/
                IRectangulo rect_izq= nodo_izq.getPadre();
                IRectangulo rect_der= nodo_der.getPadre();
                nodo_der=null;
                nodo_izq= null;
                current_node= this.u.leerNodo(padre.getIdContainer());
                current_node.eliminarRectangulo(padre);
                current_node.appendRectangulo(rect_izq);
                insertar_MBR(rect_der);


            }

        }


        // caso 2: el nodo es nodo interno
        else{
            // caso 2.1 se trata de la raiz
            if(!current_node.tienePadre()){
                // se hace lo mismo que en 1.1 pero ademas debo actualizar el puntero al padre de todos los hijos
                // se debe crear nuevo nodo interno
                current_node.eliminar();
                current_node= null; // lo eliminamos de memoria
                nodo_izq.setPadre(this.idRaiz);
                nodo_der.setPadre(this.idRaiz);

                NodoInterno nueva_raiz = new NodoInterno(this.idRaiz, nodo_izq.getPadre()); // usamos el mismo id del nodo anterior
                nueva_raiz.appendRectangulo(nodo_der.getPadre());
                this.altura++;
                nodo_izq.guardar();
                nodo_der.guardar();
                nueva_raiz.guardar();

                // parte diferente
                int pos_der= nodo_der.getId();
                int pos_izq= nodo_izq.getId();
                ArrayList<Integer> hijos_der= nodo_der.indices_hijos();
                ArrayList<Integer> hijos_izq= nodo_izq.indices_hijos();
                // quitamos los nodos de memoria principal
                nodo_der=null;
                nodo_izq=null;
                /* debemos actualizar el puntero al padre de cada uno de los hijos. esto para indicarle
                * el indice del nuevo archivo en que esta su padre*/
                //for int in hijo der:




            }
            // caso 2.2 es un nodo con padre
            else{

            }
        }


    }



    public void insertar(IRectangulo new_rec){

        /* los primeros M datos se insertan en memoria principal
        sin necesidad de realizar búsquedas
         */

        if(en_memoria_principal) {
            if (!current_node.isfull()) {
                current_node.appendRectangulo(new_rec);
                if (current_node.isfull()) {
                    System.out.println("guardando primer nodo lleno");
                    current_node.guardar();
                    this.en_memoria_principal = false;
                }
            }
        }
        else{
            current_node= this.u.leerNodo(idRaiz);
            if(new_rec.esDato()){
                insertar_dato(new_rec);
            }

            // estamos insertando una mbr
            else{
                insertar_MBR(new_rec);
            }


        }



    }


    /* esta funcion recibe el dato que se desea insertar y verifica si el nodo actual cargado en memoria
    * tiene espacio para almacenarlo*/

    public void insertar_dato(IRectangulo dato){

        // se busca una hoja
        while(!current_node.esHoja()){
            IRectangulo target_rec= current_node.target_rectangulo(dato, this);
            // envío nodo a disco, traigo nuevo nodo de disco y nodo es recolectado por gc
            current_node= this.u.leerNodo(target_rec.getId());

        }
        // se encuentra una hoja

        if (!current_node.isfull()) {// la hoja tiene espacio
            current_node.appendRectangulo(dato);
            current_node.guardar();// mandamos la hoja a disco
            System.out.println("insertando dato en nodo " + Integer.toString(current_node.getId()) + "\n");
            System.out.println(Integer.toString(current_node.getIndiceUltimo())+ "\n");

        }

        // se debe realizar un split
        else {
            if(this.split.equals("Linear"))this.DummySplit(dato);
            else this.GreeneSplit(dato);

            }

    }


    public void insertar_MBR(IRectangulo mbr){

        if (!current_node.isfull()){
            current_node.appendRectangulo(mbr);
            current_node.guardar();// mandamos a disco
        }


        else {
            if(this.split.equals("Linear"))this.DummySplit(mbr);
            else this.GreeneSplit(mbr);

            }

        }



    }



