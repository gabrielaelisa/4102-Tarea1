import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class NodoUtils {
    private String split;
    private int M;
    private int m;


    public NodoUtils( int M, int m, String split){
        this.M= M;
        this.m= m;
        this.split= split;
    }

    public static INodo leerNodo(int id) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(RTree.DIR + "n" + id + ".node"));
            return (INodo) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    // Mezcla dos listas
    // primera lista L[l, ... ,m]
    //segunda lista L[m+1, ... , r]
    public void merge(INodo n , int l, int m, int r, boolean dir_corte)
    {
        //Largos listas
        int n1 = m - l + 1;
        int n2 = r - m;

        /* listas temporales*/
        ArrayList<IRectangulo> L= new ArrayList<IRectangulo>();
        ArrayList<IRectangulo> R = new ArrayList<IRectangulo>();

        /*Copiamos datos*/
        for (int i=0; i<n1; ++i)
            L.add(n.getRectangulo(l + i));
        for (int j=0; j<n2; ++j)
            R.add(n.getRectangulo(m + 1+ j));


        /* Mezclamos listas temporales */

        //indices iniciales para cada sub lista
        int i = 0, j = 0;

        // Indice inicial de la lista mezclada
        int k = l;
        while (i < n1 && j < n2) {
            if(dir_corte){ //eje y, se ordena por lado inferior
                if(L.get(i).getY() <= R.get(j).getY()){
                    n.setRectangulo(k, L.get(i));
                    i++;
                }
                else{
                    n.setRectangulo(k, R.get(j));
                    j++;
                }
            }else{ // ejex , se ordena por lado izquierdo
                if(L.get(i).getX() <= R.get(j).getX()){
                    n.setRectangulo(k, L.get(i));
                    i++;
                }
                else{
                    n.setRectangulo(k, R.get(j));
                    j++;
                }
            }
            k++;
        }

        /* Copiamos elementos faltantes de L si hay */
        while (i < n1)
        {
            n.setRectangulo(k, L.get(i));
            i++;
            k++;
        }

        /*Copiamos elementos faltantes de R si hay */
        while (j < n2)
        {
            n.setRectangulo(k, R.get(j));
            j++;
            k++;
        }
    }

    //Ordena rectangulos del nodo n segun direccion de corte
    public void sort(INodo n, int l, int r, boolean dir_corte){
        if (l < r) {

            int m1 = (l+r)/2;
            sort(n, l, m1, dir_corte);
            sort(n , m1+1, r, dir_corte);

            // Merge the sorted halves
            merge(n, l, m1, r, dir_corte);
        }
    }

    //dado un nodo lleno, y un rectangulo rec, que se quiere insertar.
    //Aplica dummy split para dividir este nodo en un par que contiene los nodos resultantes
    public Pair<INodo, INodo> dummySplit(RTree tree, INodo n, IRectangulo r){
        INodo nodo_izq = null;
        INodo nodo_der = null;

        if(n.esHoja()){
            nodo_izq= new NodoHoja(n.getId(), r, this.M, this.m);
            nodo_der= new NodoHoja(tree.popNextId(), n.popRectangulo(), this.M, this.m);
        }else{
            nodo_izq= new NodoInterno(n.getId(), r, this.M, this.m);
            nodo_der= new NodoInterno(tree.popNextId(), n.popRectangulo(), this.M, this.m);
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
    public Pair<INodo, INodo> linearSplit(RTree tree, INodo n, IRectangulo r){
        INodo nodo_izq = null;
        INodo nodo_der = null;

        //Eleccion de r1 y r2 que ira al nodo izq y der respectivamente
        IRectangulo r_sup = null;
        IRectangulo r_inf = null;
        IRectangulo r_izq = null;
        IRectangulo r_der = null;

        n.appendRectangulo(r); //Agregamos rectangulo a n que queda con M+1 elementos

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
            r_der = r_der.getX() > current.getX()? r_der : current;

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
        int r_y = r_sup.getY() + r_sup.alto() - (r_inf.getX());

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
            nodo_izq = new NodoHoja(tree.popNextId(), n.popRectangulo(i_izq), this.M, this.m);
            nodo_der = new NodoHoja(tree.popNextId(), n.popRectangulo(i_der), this.M, this.m);
        }
        else{
            nodo_izq= new NodoInterno(tree.popNextId(),n.popRectangulo(i_izq), this.M, this.m);
            nodo_der=new NodoInterno(tree.popNextId(), n.popRectangulo(i_der), this.M, this.m);
        }

        //se inserta el resto

        int len = 0;
        while(!n.isEmpty()){
            len = n.cantidadRectangulos();
            int random_index = (int) (Math.random() * len) + 1; //indice rectangulo random a insertar

            IRectangulo mbr_izq = nodo_izq.getPadre();
            IRectangulo mbr_der = nodo_der.getPadre();

            double dif_izq = mbr_izq.difArea(n.getRectangulo(random_index));
            double dif_der = mbr_der.difArea(n.getRectangulo(random_index));

            //primero hay que verificar si quedan menos de m elementos para insertar y el tamanio de nodo_izq o nodo_der aun no supera m
            if(n.cantidadRectangulos() + nodo_izq.cantidadRectangulos() == this.m ){
                nodo_izq.appendRectangulo(n.popRectangulo(random_index));
            }
            else if(n.cantidadRectangulos() + nodo_der.cantidadRectangulos() == this.m ){
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

    //M: memoria principal
    public Pair<INodo, INodo> GreeneSplit(RTree tree, INodo n, IRectangulo rec){
        INodo nodo_izq = null;
        INodo nodo_der = null;

        //Eleccion de r1 y r2 que ira al nodo izq y der respectivamente
        IRectangulo r_sup = null;
        IRectangulo r_inf = null;
        IRectangulo r_izq = null;
        IRectangulo r_der = null;

        n.appendRectangulo(rec);

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
            r_der = r_der.getX() > current.getX()? r_der : current;

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
        int r_y = r_sup.getY() + r_sup.alto() - (r_inf.getX());

        //Comparamos cual es mejor: la distancia mayor normalizada e insertamos a nodo_izq y nodo_der lo que corresponda
        double n_x = dif_x/r_x;
        double n_y = dif_y/r_y;


        boolean dir_corte = false; //false x, true y

        if(n_x > n_y){
            dir_corte = true;
        }

        //se ordenan rectangulos segun direccion de corte
        sort(n, 0, n.cantidadRectangulos()-1, dir_corte);
        //se insertan
        if(n.esHoja()){
            nodo_izq = new NodoHoja(tree.popNextId(), n.popRectangulo(), this.M, this.m);
            int i = 1;
            while (i < M/2 -1){
                nodo_izq.appendRectangulo(n.popRectangulo());
                i++;
            }
            nodo_der = new NodoHoja(tree.popNextId(), n.popRectangulo(), this.M, this.m);
            while(!n.isEmpty()){
                nodo_der.appendRectangulo(n.popRectangulo());
            }
        }
        else{
            nodo_izq= new NodoInterno(tree.popNextId(),n.popRectangulo(), this.M, this.m);
            int i = 1;
            while (i < M/2 -1){
                nodo_izq.appendRectangulo(n.popRectangulo());
                i++;
            }
            nodo_der=new NodoInterno(tree.popNextId(), n.popRectangulo(), this.M, this.m);
            while(!n.isEmpty()){
                nodo_der.appendRectangulo(n.popRectangulo());
            }
        }


        return  new Pair<>(nodo_izq, nodo_der);
    }

    public Pair<INodo, INodo> split(RTree tree, INodo nodo, IRectangulo rec){
        if(split.equals("linear")){
            return this.linearSplit(tree, nodo, rec);
        }
        else if(split.equals("greene")){
            return this.GreeneSplit(tree, nodo, rec);
        }
        else{
            return this.dummySplit(tree,nodo, rec);
        }
    }

}
