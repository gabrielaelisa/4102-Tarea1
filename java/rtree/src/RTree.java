import com.sun.corba.se.impl.resolver.INSURLOperationImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.util.Pair;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.util.Stack;

public class RTree implements Serializable{

    private int idRaiz;
    private int nextId= 0;
    public static final String DIR = "datos" + File.separator;
    public INodo current_node;
    public boolean en_memoria_principal= true;
    // indica cuando es necesario recorrer de hoja a raiz para mantener invariante 1
    protected NodoUtils u;
    public boolean reajustar=false;
    int M ;
    int m;
    // constructor de la raiz, recibe un dato y crea el nodo contenedor
    public RTree(Dato rectangulo1, String split, int M, int m){
        /* este es el nodo que tenemos actualmente cargado en memoria
         siempre habra un nodo cargado en memoria */
        this.M= M;
        this.m = m;
        current_node= new NodoHoja(popNextId(),rectangulo1, M, m);
        this.idRaiz= current_node.getId();
        this.u= new NodoUtils(M, m, split);
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

    private class InfoNodo{
        public int id;
        public boolean visitado;

        InfoNodo(int id, boolean visitado){
            this.id= id;
            this.visitado= visitado;
        }
    }

    

    public void insertarDato(Dato dato){
        // Si hay solo una hoja (que es a la vez la raiz) la mantenemos en memoria hasta que se llene.
        if(en_memoria_principal){
            if(current_node.isfull()){
                // Overflow, hay que generar una nueva raiz
                Pair<INodo, INodo> parNodos = u.split(this,current_node, dato);
                parNodos.getValue().guardar();
                parNodos.getKey().guardar();
                MBR nuevoMbr1= (MBR) parNodos.getKey().getPadre();
                MBR nuevoMbr2= (MBR) parNodos.getValue().getPadre();
                NodoInterno nuevaRaiz= new NodoInterno(this.popNextId(), nuevoMbr1, this.M, this.m);
                this.setIdRaiz(nuevaRaiz.getId());
                nuevaRaiz.appendRectangulo(nuevoMbr2);
                nuevaRaiz.guardar();
                current_node= null;
                //garbage collection
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
                            Pair<INodo, INodo> parNodos = u.split(this,actual, nuevoMbr2);
                            parNodos.getValue().guardar();
                            parNodos.getKey().guardar();
                            nuevoMbr1= (MBR) parNodos.getKey().getPadre();
                            nuevoMbr2= (MBR) parNodos.getValue().getPadre();
                            NodoInterno nuevaRaiz= new NodoInterno(this.popNextId(), nuevoMbr1, this.M, this.m);
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
                        Pair<INodo, INodo> parNodos = u.split(this,actual, nuevoMbr2);
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
                Pair<INodo, INodo> parNodos = u.split(this,actual, dato);
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



