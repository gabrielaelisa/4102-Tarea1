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
    public RTree(IRectangulo rectangulo1){
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

    public void insertar(IRectangulo newrec){
        // es una hoja, entonces intentamos insertarlo
        if(current_node.esHoja()) {
            if (!current_node.isfull()) {
                current_node.appendRectangulo(newrec);

            } else { // la hoja está llena
                /* implementar heurísticas*/
            }
        }
              
            
        // es un nodo interno, debo comparar con cada rectangulo
        else
        {
            //rectángulo que aumenta menos su MBR
            IRectangulo target_rec= null;
            int min_grow= Integer.MAX_VALUE;
            int min_area= Integer.MAX_VALUE;

            for (int x=0; x<current_node.cantidadRectangulos(); x++) {

                IRectangulo rec = current_node.getRectangulo(x);
                //este caso es facil, solo debo descender
                if (rec.contains(newrec)) {
                    target_rec = rec;
                    break;

                }
                // aqui se debe escoger el MBR que crezca menos, para mantener el invariante 1
                else if (rec.intersects(newrec)) {
                    if (rec.interseccion(newrec) < min_grow) {
                        target_rec = rec;
                        min_area= rec.ancho()*rec.alto();
                    }
                    // en caso de empate se baja por el MBR que tenga menor area
                    if(rec.interseccion(newrec)== min_grow){
                        if(rec.ancho()*rec.alto()<min_area) target_rec=rec;
                    }


                }

            }
            /*envío nodo a disco
             traigo nuevo nodo de disco
            nodo es recolectado por gc*/

            current_node.guardar();
            current_node= this.u.leerNodo(target_rec.getIdNodo());
            // se repite el paso anterior pero usando un nuevo nodo de memoria
            this.insertar(newrec);
        }

        
    }


    }



