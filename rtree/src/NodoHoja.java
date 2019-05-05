public class NodoHoja extends AbstractNodo {


    public NodoHoja(int id, IRectangulo mbr, int M){

        super(id, mbr, M);
    }

    @Override
    public boolean esHoja(){
        return true;
    }

}
