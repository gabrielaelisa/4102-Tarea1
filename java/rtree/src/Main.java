public class Main {

    public static void main(String[] args) {
        RTree tree= new RTree();
        int idNodo = tree.popNextId();
        MBR mbrHoja= new MBR(idNodo, 0,0,1,1);
        Dato d1= new Dato(0,0,1,1);
        Nodo nodo = new NodoHoja(idNodo, mbrHoja);
        nodo.appendRectangulo(d1);
        nodo.guardar();
        idNodo= tree.popNextId();
        MBR mbrHoja2= new MBR(idNodo, 3, 0, 1, 1);
        Dato d2= new Dato(3, 0, 1, 1);
        nodo= new NodoHoja(idNodo, mbrHoja2);
        nodo.appendRectangulo(d2);
        nodo.guardar();
        idNodo= tree.popNextId();
        nodo= new NodoInterno(idNodo, null);
        tree.setIdRaiz(idNodo);
        nodo.appendRectangulo(mbrHoja);
        nodo.appendRectangulo(mbrHoja2);
        System.out.println(nodo.cantidadRectangulos());
        nodo= NodoUtils.leerNodo(mbrHoja.getIdNodo());
        System.out.println(nodo.getRectangulo(0).getX());

    }
}
