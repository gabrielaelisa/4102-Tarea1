import java.io.*;

public class RTree implements Serializable{

    private int idRaiz;
    public String method;
    private int nextId= 0;
    public static final String DIR = "datos" + File.separator;
    public INodo current_node;

    // constructor de la raiz, recibe un dato y crea el nodo contenedor
    public RTree(IRectangulo rectangulo1){
        /* este es el nodo que tenemos actualmente cargado en memoria
         siempre habra un nodo cargado en memoria */
        current_node= NodoHoja(0,rectangulo1);
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

            } else {
                /* implementar heurísticas*/
            }
        }
              
            
        // es un nodo interno, debo comparar con cada rectangulo
        else
        {
            for (int x=0; x<current_node.cantidadRectangulos(); x++)
            {
                IRectangulo rec= current_node.rectangulos.get(x);
                //este caso es facil, solo debo descender
                if(rec.contains(newrec)){
                    //envío nodo a disco
                    current_node.guardar();
                    // traigo nuevo nodo de disco
                    // debo destruir el nodo?
                    current_node= leerNodo(rec.idNodo);
                    break;

                }
                // aqui se debe escoger el MBR que crezca menos, para mantener el invariante 1
                else if(rec.intersects(newrec)){

            }
            // se repite el paso anterior pero usando un nuevo nodo de memoria
            this.insertar(newrec);
    }

        
}


    }


}
