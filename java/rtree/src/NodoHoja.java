public class NodoHoja extends AbstractNodo {


    public NodoHoja(int id, IRectangulo mbr, int M, int m){

        super(id, mbr, M, m);
    }

    @Override
    public boolean esHoja(){
        return true;
    }

}
