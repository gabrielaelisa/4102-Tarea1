public class NodoInterno extends AbstractNodo {

    // un nodo interno siempre se crea con 2 rectangulos, ya que  se crea a partir de overflow
    public NodoInterno(int id, IRectangulo mbr){

        super(id, mbr);
    }

    @Override
    public boolean esHoja(){
        return false;
    }

}
