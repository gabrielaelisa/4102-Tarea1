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
            nodo_izq= new NodoHoja(n.getId(), r);
            nodo_der= new NodoHoja(popNextId(), n.popRectangulo());
        }else{
            nodo_izq= new NodoInterno(n.getId(), r);
            nodo_der= new NodoInterno(popNextId(), n.popRectangulo());
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
        // Si hay solo una hoja (que es a la vez la raiz) la mantenemos en memoria hasta que se llene.
        if(en_memoria_principal){
            if(current_node.isfull()){
                // Overflow, hay que generar una nueva raiz
                Pair<INodo, INodo> parNodos = dummySplit(current_node, dato);
                parNodos.getValue().guardar();
                parNodos.getKey().guardar();
                MBR nuevoMbr1= (MBR) parNodos.getKey().getPadre();
                MBR nuevoMbr2= (MBR) parNodos.getValue().getPadre();
                NodoInterno nuevaRaiz= new NodoInterno(this.popNextId(), nuevoMbr1);
                this.setIdRaiz(nuevaRaiz.getId());
                nuevaRaiz.appendRectangulo(nuevoMbr2);
                nuevaRaiz.guardar();
                current_node= null;
                en_memoria_principal= false;
                return;
            }
            current_node.appendRectangulo(dato);
            return;
        }

        //No esta en memoria principal, insertamos dato utilizando DFS:
        Stack<InfoNodo> pila= new Stack<>();
        boolean ampliar = false; // true si se deben ampliar los mbr al regresar hacia la raiz
        MBR mbrAmpliado= null; // El mbr que se debe buscar y reemplazar al regresar hacia la raiz
        boolean overflow = false; // true si se debe manejar overflow al regresar hacia la raiz
        MBR mbrBorrar= null, nuevoMbr1=null, nuevoMbr2= null ; // MBRs para manejar el overflow al regresar hacia la raiz
        pila.push(new InfoNodo(idRaiz, false)); // Comenzamos con el nodo raiz
        while(pila.size()!=0){
            InfoNodo infoActual= pila.peek(); // Obtenemos primera componenete (id) del par en la pila
            if(infoActual.visitado){
                // Si llegamos aqui es porque hubo overflow o ampliacion y
                // estamos en un nodo interno.
                NodoInterno actual = (NodoInterno) NodoUtils.leerNodo(infoActual.id);
                if(ampliar){
                    if(actual.getId() == this.idRaiz){
                        // Estamos en la raiz, no hay que ampliar, solo reemplazar.
                        actual.replaceMRB(mbrAmpliado);
                        actual.guardar();
                        return;
                    }
                    // Estamos en un nodo interno que no es la raiz.
                    MBR mbrNodo= new MBR((MBR) actual.getPadre()); // Copiamos para comparar luego
                    actual.replaceMRB(mbrAmpliado);
                    actual.getPadre().ampliar(mbrAmpliado);
                    MBR nuevoMBR= (MBR) actual.getPadre();
                    actual.guardar();
                    if(mbrNodo.equals(nuevoMBR))
                        return; // No hubo ampliacion al agregar el MBR ampliado
                    mbrAmpliado= nuevoMBR;
                    // Seguimos ampliando hacia la raiz
                }else if(overflow){
                    // Puede haber otro overflow o ampliacion
                    if(actual.getId() == this.idRaiz){
                        //Estamos en la raiz, hay que crear un nuevo nodo raiz si es que esta llena.
                        actual.eliminarRectangulo(mbrBorrar);
                        actual.appendRectangulo(nuevoMbr1);
                        if(actual.isfull()){
                            // Creamos un nuevo nodo raiz
                            Pair<INodo, INodo> parNodos = dummySplit(actual, nuevoMbr2);
                            parNodos.getValue().guardar();
                            parNodos.getKey().guardar();
                            nuevoMbr1= (MBR) parNodos.getKey().getPadre();
                            nuevoMbr2= (MBR) parNodos.getValue().getPadre();
                            NodoInterno nuevaRaiz= new NodoInterno(this.popNextId(), nuevoMbr1);
                            this.setIdRaiz(nuevaRaiz.getId());
                            nuevaRaiz.appendRectangulo(nuevoMbr2);
                            nuevaRaiz.guardar();
                            return;
                        }
                        // No esta llena, insertamos y retornamos.
                        actual.eliminarRectangulo(mbrBorrar);
                        actual.appendRectangulo(nuevoMbr1);
                        actual.appendRectangulo(nuevoMbr2);
                        actual.guardar();
                        return;
                    }
                    // Estamos en un nodo interno que no es la raiz.
                    MBR mbrNodo= new MBR((MBR) actual.getPadre()); // Copiamos para comparar por ampliacion
                    actual.eliminarRectangulo(mbrBorrar);
                    actual.appendRectangulo(nuevoMbr1);
                    if(actual.isfull()){
                        // Nuevo overflow.
                        mbrBorrar= new MBR((MBR) actual.getPadre());
                        Pair<INodo, INodo> parNodos = dummySplit(actual, nuevoMbr2);
                        parNodos.getKey().guardar();
                        parNodos.getValue().guardar();
                        nuevoMbr1= (MBR) parNodos.getKey().getPadre();
                        nuevoMbr2= (MBR) parNodos.getValue().getPadre();
                        // Se continua manejando el overflow hacia la raiz.
                    }else{
                        // Ya no hay overflow pero puede que haya que ampliar.
                        actual.appendRectangulo(nuevoMbr2);
                        MBR nuevoMBR= (MBR) actual.getPadre();
                        if(!mbrNodo.equals(nuevoMBR)){
                            // Hay que ampliar hacia la raiz
                            overflow= false;
                            ampliar= true;
                            mbrAmpliado= nuevoMBR;
                        }
                        actual.guardar();
                    }
                }
                pila.pop(); // Se quita el nodo actual de la pila
                continue; // Continuamos manejando overflow o ampliacion hacia la raiz
            }
            infoActual.visitado= true;
            INodo actual= NodoUtils.leerNodo(infoActual.id);
            if(actual.esHoja()){
                // Insertar y si no hay Overflow ni hay que ampliar retornamos.

                if (!actual.isfull()) {// La hoja tiene espacio
                    MBR mbrNodo= new MBR((MBR) actual.getPadre()); // Copiamos el mbr padre para ver si hubo ampliacion
                    actual.appendRectangulo(dato);
                    MBR nuevoMBR= (MBR) actual.getPadre();
                    actual.guardar();
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
                Pair<INodo, INodo> parNodos = dummySplit(actual, dato);
                parNodos.getKey().guardar();
                parNodos.getValue().guardar();
                nuevoMbr1= (MBR) parNodos.getKey().getPadre();
                nuevoMbr2= (MBR) parNodos.getValue().getPadre();
                pila.pop(); // Quitamos el nodo actual de la pila
                continue;
            }
            // El nodo actual no es una hoja
            MBR rectDescenso= ((NodoInterno)actual).target_rectangulo(dato, this);
            pila.push(new InfoNodo(rectDescenso.getId(), false));

        }// Fin while
    }// Fin metodo

}



