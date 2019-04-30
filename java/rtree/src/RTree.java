import com.sun.corba.se.impl.resolver.INSURLOperationImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.util.Pair;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

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

    //dado un nodo lleno, y un rectangulo rec, que se quiere insertar. 
    //Aplica dummy split para dividir este nodo en un par que contiene los nodos resultantes
    public Pair<INodo, INodo> dummySplit(INodo n, IRectangulo r){
        INodo nodo_izq = null;
        INodo nodo_der = null;

        if(n.esHoja()){
            nodo_izq = new NodoHoja(popNextId(), r);
            nodo_der = new NodoHoja(popNextId(), n.popRectangulo());
        }
        else{
            nodo_izq= new NodoInterno(popNextId(), r);
            nodo_der=new NodoInterno(popNextId(), n.popRectangulo());
        }
        int len = n.cantidadRectangulos();
        int i = 0;
        while(i < len/2){
            nodo_izq.appendRectangulo(n.popRectangulo());
            i++;
        }
        while(!n.isEmpty()){
            nodo_der.appendRectangulo(n.popRectangulo()); 
        }

        return new Pair<>(nodo_izq, nodo_der);
    }

    //m: capacidad minima que se debe ocupar de los nodos izq y der resultantes 
    public Pair<INodo, INodo> linearSplit(int m, INodo n, IRectangulo r){
        INodo nodo_izq = null;
        INodo nodo_der = null;
        
        //Eleccion de r1 y r2 que ira al nodo izq y der respectivamente
        IRectangulo r_sup = null;
        IRectangulo r_inf = null;
        IRectangulo r_izq = null;
        IRectangulo r_der = null;

        for(int i = 0; i < n.cantidadRectangulos(); i++){
            IRectangulo current = n.getRectangulo(i);

            //rectangulo con lado superior que esta más abajo 
            if(r_inf == null){
                r_inf = current;
            }
            r_inf = r_inf.getY()+ r_inf.alto() < current.getY()+ current.alto()? r_inf : current;

            //rectangulo con lado inferior que esta más arriba
            if(r_sup == null){
                r_sup = current;
            }
            r_sup = r_sup.getY() > current.getY()? r_sup : current;

            //rectangulo con lado izquierdo que esta mas a la derecha
            if(r_der == null){
                r_der = current;
            }
            r_der = r_der.getX() > r_.getX()? r_der : current;

            //rectangulo con lado derecho que esta mas a la izquierda
            if(r_izq == null){
                r_izq = current;
            }
            r_izq = r_izq.getX()+ r_izq.ancho() < current.getX()+ current.ancho()? r_izq : current;  
        }

        //Calculamos distancias maximas entre cada eje
        int dif_x = r_der.getX() - (r_izq.getX() + r_izq.ancho());
        int dif_y = r_sup.getY() - (r_inf.getY() + r_inf.alto());

        //Calculamos rangos
        int r_x = r_der.getX()+ r_der.ancho() - (r_izq.getX());
        int r_y = r_sup.getY() + r_sup.alto() - (r.inf.getX());
        
        //Comparamos cual es mejor: la distancia mayor normalizada e insertamos a nodo_izq y nodo_der lo que corresponda
        double n_x = dif_x/r_x;
        double n_y = dif_y/r_y;
        int i_izq = 0; //indices
        int i_der = 0; 
        if(n_x > n_y){
           i_izq = n.indexRectangulo(r_izq);
           i_der = n.indexRectangulo(r_der);
        }
        else{
            i_izq = n.indexRectangulo(r_sup);
            i_der = n.indexRectangulo(r_inf);
        }
        //se insertan
        if(n.esHoja()){
            nodo_izq = new NodoHoja(popNextId(), n.popRectangulo(i_izq));
            nodo_der = new NodoHoja(popNextId(), n.popRectangulo(d_der));
        }
        else{
            nodo_izq= new NodoInterno(popNextId(),n.popRectangulo(i_izq));
            nodo_der=new NodoInterno(popNextId(), n.popRectangulo(d_der));
        }

        //se inserta el resto

        int len = 0;
        while(!n.isEmpty()){
            len = n.cantidadRectangulos();
            int random_index = (int) (Math.random() * len) + 1; //indice rectangulo random a insertar

            IRectangulo mbr_izq = nodo_izq.getPadre();
            IRectangulo mbr_der = nodo_der.getPadre();

            double dif_izq = mbr_izq.ampliar(n.getRectangulo(random_index));
            double dif_der = mbr_der.ampliar(n.getRectangulo(random_index));
            
            //primero hay que verificar si quedan menos de m elementos para insertar y el tamanio de nodo_izq o nodo_der aun no supera m
            if(n.cantidadRectangulos <= m && nodo_izq.cantidadRectangulos() < m ){
                nodo_izq.appendRectangulo(n.popRectangulo(random_index));
            }
            else if(n.cantidadRectangulos <= m && nodo_der.cantidadRectangulos() < m ){
                nodo_der.appendRectangulo(n.popRectangulo(random_index));
            }
            else{ // caso en que queden mas de m elementos por insertar o nodo_izq y nodo_der tienen mas de m elementos cada uno
                //Comparamos crecimiento de areas
                if(dif_izq < dif_der){
                    nodo_izq.appendRectangulo(n.popRectangulo(random_index));
                }
                else{
                    nodo_der.appendRectangulo(n.popRectangulo(random_index));
                }
            }
        }


        return new Pair<>(nodo_izq, nodo_der);
    }
    public void DummySplit(IRectangulo rec) {
        System.out.println("splitting");
        INodo nodo_izq = null;
        INodo nodo_der = null;

        if (current_node.esHoja()) {
            nodo_izq = new NodoHoja(popNextId(), rec);
            nodo_der = new NodoHoja(popNextId(), current_node.popRectangulo());
        } else {
            nodo_izq = new NodoInterno(popNextId(), rec);
            nodo_der = new NodoInterno(popNextId(), current_node.popRectangulo());

        }

        //-------------------------- aqui debería ir una heurística-------------------------------------------

        int len = new Integer(current_node.cantidadRectangulos());
        for (int i = 0; i < (int) len / 2; i++) {
            nodo_izq.appendRectangulo(current_node.popRectangulo());
        }
        for (int i = len / 2; i < len; i++) {
            nodo_der.appendRectangulo(current_node.popRectangulo());
        }


        // ------------------------ terminan los ciclos ---------------------------------------------------------


        // caso 1:  el nodo al que se le hace split es una hoja
        if (current_node.esHoja()) {

            //caso 1.1 se trata de la raiz
            if (!current_node.tienePadre()) {
                // se debe crear nuevo nodo interno
                current_node.eliminar();
                current_node = null; // lo eliminamos de memoria
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
            else {

                System.out.println("tiene padre\n");
                IRectangulo padre = current_node.getPadre();
                current_node.eliminar(); // se debe destrulle el archivo de current node
                /* antes de traer el nodo padre a memoria principal debo dejar de tener a los hijos en RAM*/
                IRectangulo rect_izq = nodo_izq.getPadre();
                IRectangulo rect_der = nodo_der.getPadre();
                nodo_der = null;
                nodo_izq = null;
                current_node = this.u.leerNodo(padre.getIdContainer());
                current_node.eliminarRectangulo(padre);
                current_node.appendRectangulo(rect_izq);
                insertar_MBR(rect_der);


            }

        }


        // caso 2: el nodo es nodo interno
        else {
            // caso 2.1 se trata de la raiz
            if (!current_node.tienePadre()) {
                // se hace lo mismo que en 1.1 pero ademas debo actualizar el puntero al padre de todos los hijos
                // se debe crear nuevo nodo interno
                current_node.eliminar();
                current_node = null; // lo eliminamos de memoria
                nodo_izq.setPadre(this.idRaiz);
                nodo_der.setPadre(this.idRaiz);

                NodoInterno nueva_raiz = new NodoInterno(this.idRaiz, nodo_izq.getPadre()); // usamos el mismo id del nodo anterior
                nueva_raiz.appendRectangulo(nodo_der.getPadre());
                this.altura++;
                nodo_izq.guardar();
                nodo_der.guardar();
                nueva_raiz.guardar();

                // parte diferente
                int pos_der = nodo_der.getId();
                int pos_izq = nodo_izq.getId();
                ArrayList<Integer> hijos_der = nodo_der.indices_hijos();
                ArrayList<Integer> hijos_izq = nodo_izq.indices_hijos();
                // quitamos los nodos de memoria principal
                nodo_der = null;
                nodo_izq = null;

                /* debemos actualizar el puntero al padre de cada uno de los hijos. esto para indicarle
                 * el indice del nuevo archivo en que esta su padre*/
                //for int in hijo der:
                for (int i = 0; i < hijos_der.size(); i++) {
                    for (int i = 0; i < hijos_der.size(); i++) {
                        current_node = this.u.leerNodo(hijos_der.get(i));
                        current_node.setPadre(id_der);
                    }

                    for (int i = 0; i < hijos_izq.size(); i++) {
                        current_node = this.u.leerNodo(hijos_izq.get(i));
                        current_node.setPadre(id_izq);
                    }


                    IRectangulo padre = current_node.getPadre();
                    int id_der = nodo_der.getId();
                    nodo_izq = null;
                    current_node = this.u.leerNodo(padre.getIdContainer());

                    current_node.eliminarRectangulo(padre);
                    current_node.appendRectangulo(rect_izq);
                    insertar_MBR(rect_der);


                }
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

    private class InfoNodo{
        public int id;
        public boolean visitado;

        InfoNodo(int id, boolean visitado){
            this.id= id;
            this.visitado= visitado;
        }
    }

    public Pair<INodo, INodo> heuristicaDummy(INodo n, IRectangulo rec){
        // TODO Reemplazar por las heuristicas implementadas
        return new Pair<>(null, null);
    }

    public void insertarDato(Dato dato){
        //Inserta dato utilizando DFS:
        // TODO Implementar optimizacion para insercion cuando hay una sola hoja
        Stack<InfoNodo> pila= new Stack<>();
        boolean ampliar = false; // true si se deben ampliar los mbr al regresar hacia la raiz
        MBR mbrAmpliado= null; // El mbr que se debe buscar y reemplazar
        boolean overflow = false; // true si se debe manejar overflow al regresar hacia la raiz
        MBR mbrBorrar= null, nuevoMbr1=null, nuevoMbr2= null ; // MBRs para manejar el overflow al regresar hacia la raiz
        pila.push(new InfoNodo(idRaiz, false)); // Comenzamos con el nodo raiz
        while(pila.size()!=0){
            InfoNodo infoActual= pila.peek(); // Obtenemos primera componenete (id) del par en la pila
            if(infoActual.visitado){
                // Si llegamos aqui es porque hubo overflow o ampliacion y
                // estamos en un nodo interno.
                // TODO ver casos para la raiz (getPadre() retorna null).
                NodoInterno actual = (NodoInterno) NodoUtils.leerNodo(infoActual.id);
                if(ampliar){
                    MBR mbrNodo= new MBR((MBR) actual.getPadre()); //TODO Ver caso si es null
                    actual.replaceMRB(mbrAmpliado);
                    actual.getPadre().ampliar(mbrAmpliado);
                    MBR nuevoMBR= (MBR) actual.getPadre();
                    if(mbrNodo.equals(nuevoMBR))
                        return; // No hubo ampliacion al agregar el MBR ampliado
                    mbrAmpliado= nuevoMBR;
                    // Seguimos ampliando hacia la raiz
                }else if(overflow){
                    // Puede haber otro overflow o ampliacion
                    MBR mbrNodo= new MBR((MBR) actual.getPadre()); //TODO Ver caso si es null
                    actual.eliminarRectangulo(mbrBorrar);
                    actual.appendRectangulo(nuevoMbr1);
                    if(actual.isfull()){
                        // Nuevo overflow
                        //TODO Terminar caso nuevo overflow
                    }else{
                        actual.appendRectangulo(nuevoMbr2);
                        MBR nuevoMBR= (MBR) actual.getPadre();
                        if(!mbrNodo.equals(nuevoMBR)){
                            // Hay que ampliar hacia la raiz
                            overflow= false;
                            ampliar= true;
                            mbrAmpliado= nuevoMBR;
                        }
                    }
                }
                pila.pop();
                continue;
            }
            infoActual.visitado= true;
            INodo actual= NodoUtils.leerNodo(infoActual.id);
            if(actual.esHoja()){
                // Insertar y si no hay Overflow ni hay que ampliar retornamos.

                if (!actual.isfull()) {// la hoja tiene espacio
                    MBR mbrNodo= new MBR((MBR) actual.getPadre());
                    actual.appendRectangulo(dato);
                    MBR nuevoMBR= (MBR) actual.getPadre();
                    if(!mbrNodo.equals(nuevoMBR)){
                        // Se amplio el mbr del nodo, por lo que puede que
                        // se deba ampliar el mbr de arriba.
                        ampliar= true;
                        mbrAmpliado= nuevoMBR;
                        pila.pop(); // Quitamos el nodo actual de la pila
                        continue;
                    }
                    // No se amplio el mbr ni hubo overflow, retornamos.
                    return;
                }
                // El nodo esta lleno, hay overflow.
                overflow= true;
                mbrBorrar= new MBR((MBR) actual.getPadre());
                Pair<INodo, INodo> parNodos = heuristicaDummy(actual, dato);
                parNodos.getKey().guardar();
                parNodos.getValue().guardar();
                nuevoMbr1= (MBR) parNodos.getKey().getPadre();
                nuevoMbr2= (MBR) parNodos.getValue().getPadre();
                pila.pop(); // Quitamos el nodo actual de la pila
                continue;
            }
            // El nodo actual no es una hoja
            // TODO Revisar target_rectangulo
            MBR rectDescenso= (MBR) actual.target_rectangulo(dato, this);
            pila.push(new InfoNodo(rectDescenso.getId(), false));

        }// Fin while
    }

}



