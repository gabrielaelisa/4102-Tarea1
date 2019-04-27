public class NodoHoja extends AbstractNodo {


    public NodoHoja(int id, IRectangulo mbr){

        super(id, mbr);
    }

    @Override
    public boolean esHoja(){
        return true;
    }

}
